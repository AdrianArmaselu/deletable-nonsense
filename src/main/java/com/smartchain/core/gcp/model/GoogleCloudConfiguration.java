package com.smartchain.core.gcp.model;


import com.smartchain.core.gcp.io.ResourceManager;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleCloudConfiguration {

    private String projectId;

    private HttpTransport transport;
    private JacksonFactory jacksonFactory;
    private GoogleCredential credential;
    private GoogleCredentials credentials;

    public GoogleCloudConfiguration() throws GeneralSecurityException, IOException {
        projectId = "alysia-172200";
        transport = GoogleNetHttpTransport.newTrustedTransport();
        jacksonFactory = JacksonFactory.getDefaultInstance();
        credential = ResourceManager.getServiceAccountApiKeyCredential();
        credentials = ResourceManager.getServiceAccountApiKeyCredentials();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setTransport(HttpTransport transport) {
        this.transport = transport;
    }

    public void setJacksonFactory(JacksonFactory jacksonFactory) {
        this.jacksonFactory = jacksonFactory;
    }

    public void setCredential(GoogleCredential credential) {
        this.credential = credential;
    }

    public void setCredentials(GoogleCredentials credentials) {
        this.credentials = credentials;
    }

    public String getProjectId() {
        return projectId;
    }

    public HttpTransport getTransport() {
        return transport;
    }

    public JacksonFactory getJacksonFactory() {
        return jacksonFactory;
    }

    public GoogleCredential getCredential() {
        return credential;
    }

    public GoogleCredentials getCredentials() {
        return credentials;
    }
}
