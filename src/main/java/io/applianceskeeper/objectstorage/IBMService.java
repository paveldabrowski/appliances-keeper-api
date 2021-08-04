package io.applianceskeeper.objectstorage;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.ClasspathJsonFileCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import java.time.LocalDateTime;
import java.util.List;

public class IBMService {

        public static void main(String[] args)
        {
            String bucketName = "<BUCKET_NAME>";  // eg my-unique-bucket-name
            String newBucketName = "<NEW_BUCKET_NAME>"; // eg my-other-unique-bucket-name
            String apiKey = "<API_KEY>"; // eg "W00YiRnLW4k3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
            String serviceInstanceId = "<SERVICE_INSTANCE_ID"; // eg "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
            String endpointUrl = "https://s3.us-south.cloud-object-storage.appdomain.cloud"; // this could be any service endpoint

            String storageClass = "us-south-standard";
            String location = "us"; // not an endpoint, but used in a custom function below to obtain the correct URL

            System.out.println("Current time: " + LocalDateTime.now());
            AmazonS3 cosClient = createClient(apiKey, serviceInstanceId, endpointUrl, location);

            listObjects(cosClient, bucketName);
            createBucket(cosClient, newBucketName, storageClass);
            listBuckets(cosClient);
        }

        public static AmazonS3 createClient(String apiKey, String serviceInstanceId, String endpointUrl, String location)
        {
//            AWSCredentials credentials = new BasicIBMOAuthCredentials(apiKey, serviceInstanceId);
            var credentials = new ClasspathJsonFileCredentialsProvider("/keys/ibm.json");
            ClientConfiguration clientConfig = new ClientConfiguration()
                    .withRequestTimeout(5000)
                    .withTcpKeepAlive(true);

            return AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(credentials)
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, location))
                    .withPathStyleAccessEnabled(true)
                    .withClientConfiguration(clientConfig)
                    .build();
        }

        public static void listObjects(AmazonS3 cosClient, String bucketName)
        {
            System.out.println("Listing objects in bucket " + bucketName);
            ObjectListing objectListing = cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();
        }

        public static void createBucket(AmazonS3 cosClient, String bucketName, String storageClass)
        {
            cosClient.createBucket(bucketName, storageClass);
        }

        public static void listBuckets(AmazonS3 cosClient)
        {
            System.out.println("Listing buckets");
            final List<Bucket> bucketList = cosClient.listBuckets();
            for (final Bucket bucket : bucketList) {
                System.out.println(bucket.getName());
            }
            System.out.println();
        }

}
