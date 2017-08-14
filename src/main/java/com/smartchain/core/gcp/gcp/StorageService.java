package com.smartchain.core.gcp.gcp;


import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import com.google.cloud.storage.*;

/**
 * This class facilitates loading and downloading of files to and from google cloud storage
 *
 * Storage Classes: http://g.co/cloud/storage/docs/storage-classes
 * Locations: http://g.co/cloud/storage/docs/bucket-locations#location-mr
 *
 */
public class StorageService extends GoogleCloudService {

    private String location = "us-central1";
    private Storage storage;

    public StorageService(GoogleCloudConfiguration configuration) {
        super(configuration);
        storage = StorageOptions.newBuilder()
                .setProjectId(configuration.getProjectId())
                .setCredentials(configuration.getCredentials())
                .build()
                .getService();
    }

    public void createBucket(String bucketName) {
        BucketInfo bucketInfo = BucketInfo.newBuilder(bucketName)
                .setStorageClass(StorageClass.COLDLINE)
                .setLocation(location)
                .build();
        storage.create(bucketInfo);
    }

    public void deleteBucket(String bucketName){
        storage.delete(bucketName);
    }

    public Bucket getBucket(String bucketName){
        return storage.get(bucketName);
    }
}
