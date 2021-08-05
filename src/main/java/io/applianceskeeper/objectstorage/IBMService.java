package io.applianceskeeper.objectstorage;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.JsonCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsV2Request;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsV2Result;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import com.ibm.cloud.objectstorage.services.s3.transfer.MultipleFileUpload;
import com.ibm.cloud.objectstorage.services.s3.transfer.ObjectMetadataProvider;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManager;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManagerBuilder;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.model.Picture;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IBMService {

    private final String credentialsPath;
    private final String endpointUrl;
    private final String location;
    private AmazonS3 cosClient;
    private Bucket bucket;
    @Getter
    private String token;
    private TransferManager transferManager;


    IBMService(@Value("${ibm.credentials.path}") String credentialsPath,
               @Value("${ibm.endpoint.url}") String endpointUrl,
               @Value("${ibm.location.name}") String location) {
        this.credentialsPath = credentialsPath;
        this.endpointUrl = endpointUrl;
        this.location = location;
    }

    @PostConstruct
//    @Scheduled(initialDelay = 15000, fixedRate = 600000)
    public void initIBMService() {
        try {
            cosClient = createClient(endpointUrl, location);
            bucket = initBucket();
            transferManager = createTransportManager();
        } catch (BucketNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public AmazonS3 createClient(String endpointUrl, String location) throws IOException {
        var jsonCredentials = new JsonCredentials(this.getClass().getResourceAsStream(credentialsPath));
        BasicIBMOAuthCredentials credentials = new BasicIBMOAuthCredentials(jsonCredentials.getApiKey(),
                jsonCredentials.getServiceInstanceId());
        this.token = "Bearer " + credentials.getTokenManager().getToken();
        ClientConfiguration clientConfig = new ClientConfiguration()
                .withRequestTimeout(5000)
                .withTcpKeepAlive(true);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, location))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig)
                .build();

    }

    private Bucket initBucket() throws BucketNotFoundException {
        var buckets = listBuckets(cosClient);
        return buckets.stream().
                filter(localBucket -> localBucket.getName()
                        .contains("appliance"))
                .findFirst()
                .orElseThrow(BucketNotFoundException::new);
    }

    /**
     * Builds TransferManager and set the part size to 5 MB and the threshold size to 5 MB => 1024 * 1024 * 5L.
     *
     * @return TransferManager
     */
    private TransferManager createTransportManager() {
        long partSize = 1024 * 1024 * 5L;
        long thresholdSize = 1024 * 1024 * 5L;
        return TransferManagerBuilder.standard()
                .withS3Client(cosClient)
                .withMinimumUploadPartSize(partSize)
                .withMultipartCopyThreshold(thresholdSize)
                .build();
    }

    public List<Bucket> listBuckets(AmazonS3 cosClient) {
        return cosClient.listBuckets();
    }

    public Set<Picture> getPicturesByPrefix(Model model) {
        List<S3ObjectSummary> picturesByPrefix = getBucketContentsV2ByPrefix(model.getId().toString());
        return picturesByPrefix
                .stream()
                .map(s3ObjectSummary -> new Picture(model, s3ObjectSummary))
                .collect(Collectors.toSet());
    }

    public List<S3ObjectSummary> getBucketContentsV2ByPrefix(String prefix) {
        log.info("Retrieving bucket contents (V2) from: " + bucket.getName());
        boolean moreResults = true;
        String nextToken = "";
        List<S3ObjectSummary> summaryList = new ArrayList<>();

        while (moreResults) {
            ListObjectsV2Request request = new ListObjectsV2Request()
                    .withBucketName(bucket.getName())
                    .withMaxKeys(1000)
                    .withContinuationToken(nextToken)
                    .withPrefix(prefix);

            ListObjectsV2Result result = this.cosClient.listObjectsV2(request);
            summaryList.addAll(result.getObjectSummaries());
            printBucketContent(result);

            if (result.isTruncated()) {
                nextToken = result.getNextContinuationToken();
                log.info("...More results in next batch!\n");
            } else {
                nextToken = "";
                moreResults = false;
            }
        }
        log.info("...No more results!");
        return summaryList;
    }

    private void printBucketContent(ListObjectsV2Result result) {
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
            var url = cosClient.getUrl(bucket.getName(), objectSummary.getKey()).toString();
            System.out.printf("Item: %s (%s bytes)\n %s", objectSummary.getKey(), objectSummary.getSize(), url);
            System.out.println();
        }
    }

    public void uploadDirectoryWithPrefix(String prefix, File directory) {
        log.info("Starting large file upload with TransferManager");
//        var transferManager = createTransportManager();
        try {
            MultipleFileUpload upload = uploadDirectory(directory, prefix);
            upload.waitForCompletion();
            log.info("Large file upload complete!");
        } catch (SdkClientException e) {
            log.error("Upload error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("Upload interrupted " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            cleanAfterUpload(directory);
        }
    }

    private void cleanAfterUpload(File directory) {
//        transferManager.shutdownNow();
        if (directory != null && directory.exists()) {
            try {
                FileUtils.forceDelete(directory);
                log.info("Directory is deleted ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MultipleFileUpload uploadDirectory(File directory, String prefix) {
        ObjectMetadataProvider metadataProvider = (file, metadata) -> {
            metadata.addUserMetadata("prefix", "testowyprefix");
            metadata.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        };

        MultipleFileUpload upload = transferManager.uploadDirectory(
                bucket.getName(),
                prefix,
                directory,
                true,
                metadataProvider
        );
//        upload.addProgressListener(new ProgressListener() {
//            @Override
//            public void progressChanged(ProgressEvent progressEvent) {
//                log.info("Progress: " + progressEvent.getBytes() + " / " + progressEvent.getBytesTransferred());
//            }
//        });
        return upload;
    }


}
