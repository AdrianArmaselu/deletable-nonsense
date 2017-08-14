package com.smartchain.core.gcp;

import com.smartchain.core.gcp.endpoint.LendingClubEndpoint;
import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import com.smartchain.core.gcp.gcp.DataProcService;
import com.smartchain.core.gcp.gcp.PipelineService;
import com.smartchain.core.gcp.gcp.StorageService;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by adisor on 7/1/2017.
 */
public class Entrypoint {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String bucketName = "dataflow-alysia-172200";
        GoogleCloudConfiguration configuration  = new GoogleCloudConfiguration();
        StorageService storageService = new StorageService(configuration);
        DataProcService dataProcService = new DataProcService(configuration);
        PipelineService pipelineService = new PipelineService(configuration);

        storageService.upload(bucketName, "lendingClub.zip", new LendingClubEndpoint());
    }
}
