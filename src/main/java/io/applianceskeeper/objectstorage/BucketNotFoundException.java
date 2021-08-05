package io.applianceskeeper.objectstorage;

public class BucketNotFoundException extends Exception {

    BucketNotFoundException() {
        super("Bucket not found");
    }
}
