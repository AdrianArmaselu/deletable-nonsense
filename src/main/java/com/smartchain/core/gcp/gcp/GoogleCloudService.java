package com.smartchain.core.gcp.gcp;

import com.smartchain.core.gcp.model.GoogleCloudConfiguration;

/**
 * Created by adisor on 7/2/2017.
 */
public abstract class GoogleCloudService {

    protected GoogleCloudConfiguration configuration;

    public GoogleCloudService(GoogleCloudConfiguration configuration) {
        this.configuration = configuration;
    }
}
