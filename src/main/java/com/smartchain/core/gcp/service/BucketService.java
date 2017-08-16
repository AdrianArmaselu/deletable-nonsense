package com.smartchain.core.gcp.service;

import com.smartchain.core.gcp.endpoint.DataEndpoint;
import com.smartchain.core.gcp.io.ResourceManager;
import com.google.cloud.storage.Bucket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class BucketService {
    private Bucket bucket;

    public BucketService(Bucket bucket) {
        this.bucket = bucket;
    }

    public void uploadFileFromLocalFileSystem(String sourceFilePath) throws UnsupportedEncodingException {
        InputStream content = ResourceManager.getInputStream(sourceFilePath);
        bucket.create(new File(sourceFilePath).getName(), content, "text/plain");
        ResourceManager.closeResource(content);
    }

    public void uploadFileFromHttpUrl(URL url, String contentType) throws IOException {
        InputStream content = url.openStream();
        bucket.create(url.getFile(), content, contentType);
        ResourceManager.closeResource(content);
    }

    public void uploadFileFromDataEndpoint(String targetFileName, DataEndpoint dataEndpoint) {
        InputStream content = dataEndpoint.stream();
        bucket.create(targetFileName, content, dataEndpoint.getContentType());
        ResourceManager.closeResource(content);
    }
}
