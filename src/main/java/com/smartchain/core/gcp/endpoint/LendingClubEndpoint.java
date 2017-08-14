package com.smartchain.core.gcp.endpoint;

import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * TODO: EXTRACT THROUGH ACTUAL APIS INSTEAD OF HARD LINK
 */
public class LendingClubEndpoint implements DataEndpoint {

    @Override
    public InputStream stream() {
        String link = "https://resources.lendingclub.com/LoanStats3a.csv.zip";
        try {
            return new URL(link).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getContentType() {
        return ContentType.DEFAULT_BINARY.toString();
    }
}
