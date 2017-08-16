package com.smartchain.core.gcp.io;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.dataproc.DataprocScopes;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * Created by adisor on 7/1/2017.
 */
public class ResourceManager {

    private static final String serviceAccountApiKeyFile = "SmartChainCore-476519a0dc33.json";

    // this is here because google is silly and uses tho methods to pass credentials
    public static GoogleCredentials getServiceAccountApiKeyCredentials() throws IOException {
        InputStream inputStream = getServiceAccountApiKeyStream();
        GoogleCredentials credential = GoogleCredentials.fromStream(inputStream)
                .createScoped(Collections.singleton(DataprocScopes.CLOUD_PLATFORM));
        closeResource(inputStream);
        return credential;
    }

    public static GoogleCredential getServiceAccountApiKeyCredential() throws IOException {
        InputStream inputStream = getServiceAccountApiKeyStream();
        GoogleCredential credential = GoogleCredential.fromStream(inputStream)
                .createScoped(Collections.singleton(DataprocScopes.CLOUD_PLATFORM));
        closeResource(inputStream);
        return credential;
    }

    public static InputStream getServiceAccountApiKeyStream() {
        return getInputStream(serviceAccountApiKeyFile);
    }

    public static InputStream getInputStream(String filePath) {
        return ResourceManager.class.getClassLoader().getResourceAsStream(filePath);
    }

    public static void closeResource(Closeable resource) {
        if (resource != null)
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
