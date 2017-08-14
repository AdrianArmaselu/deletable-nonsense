package com.smartchain.core.gcp.gcp;

import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import com.google.api.services.dataproc.Dataproc;
import com.google.api.services.dataproc.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Creates a cluster with a master and two worker nodes
 * Google currently only limits at least 2 workers. It is possible to have 1 development node but there is
 * a bug in google's api server when reading software configuration properties for worker nodes.
 * This link does not work: "https://cloud.google.com/dataproc/reference/rest/?hl=en_US"
 */
public class DataProcService extends GoogleCloudService{

    private String n1StandardMachine1Uri = "https://www.googleapis.com/compute/v1/projects/alysia-172200/zones/us-central1-a/machineTypes/n1-standard-1";
    private String zoneUri = "https://www.googleapis.com/compute/v1/projects/alysia-172200/zones/us-central1-a";
    private String networkUri = "https://www.googleapis.com/compute/v1/projects/alysia-172200/global/networks/default";

    private Dataproc dataproc;

    public DataProcService(GoogleCloudConfiguration configuration) throws IOException, GeneralSecurityException {
        super(configuration);
        this.configuration = configuration;
        dataproc = new Dataproc.Builder(configuration.getTransport(), configuration.getJacksonFactory(), configuration.getCredential()).build();
    }

    public void createCluster(String clusterName) throws GeneralSecurityException, IOException {
        dataproc.projects().regions().clusters().create(configuration.getProjectId(), "global", buildClusterDefinition(clusterName)).execute();
    }

    public void deleteCluster(String clusterName) throws IOException {
        dataproc.projects().regions().clusters().delete(configuration.getProjectId(), "global", clusterName).execute();
    }

    private Cluster buildClusterDefinition(String clusterName) {
        return new Cluster().setConfig(getClusterConfig())
                .setClusterName(clusterName)
                .setProjectId(configuration.getProjectId());
    }

    private ClusterConfig getClusterConfig() {
        return new ClusterConfig()
                .setGceClusterConfig(getGceClusterConfig())
                .setMasterConfig(getMasterConfig())
                .setWorkerConfig(getWorkerConfig());
    }

    private GceClusterConfig getGceClusterConfig() {
        return new GceClusterConfig()
                .setZoneUri(zoneUri)
                .setNetworkUri(networkUri);
    }

    private InstanceGroupConfig getMasterConfig() {
        return new InstanceGroupConfig()
                .setNumInstances(1)
                .setMachineTypeUri(n1StandardMachine1Uri)
                .setDiskConfig(getDiskConfig());
    }

    private InstanceGroupConfig getWorkerConfig() {
        return new InstanceGroupConfig()
                .setNumInstances(2)
                .setMachineTypeUri(n1StandardMachine1Uri)
                .setDiskConfig(getDiskConfig());
    }

    private DiskConfig getDiskConfig() {
        return new DiskConfig()
                .setBootDiskSizeGb(100)
                .setNumLocalSsds(0);
    }

}
