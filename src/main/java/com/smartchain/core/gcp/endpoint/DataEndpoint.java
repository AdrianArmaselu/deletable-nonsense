package com.smartchain.core.gcp.endpoint;

import java.io.InputStream;

/**
 * Created by adisor on 7/2/2017.
 */
public interface DataEndpoint {
    InputStream stream();

    String getContentType();
}
