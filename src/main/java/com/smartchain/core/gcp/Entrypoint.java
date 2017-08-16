package com.smartchain.core.gcp;

import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import com.smartchain.core.gcp.service.ContainerEngineServiceJava;
import com.smartchain.core.gcp.service.DataProcService;
import com.smartchain.core.gcp.service.PipelineService;
import com.smartchain.core.gcp.service.StorageService;

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
        ContainerEngineServiceJava containerEngineService = new ContainerEngineServiceJava(configuration);
//        containerEngineService.createContainerCluster();

    }
}
