package com.smartchain.core.gcp.service;

import com.google.api.client.json.CustomizeJsonParser;
import com.google.api.services.container.Container;
import com.google.api.services.container.model.*;
import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import com.smartchain.core.gcp.model.ImageTypes;
import com.smartchain.core.gcp.model.MachineTypes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ContainerEngineServiceJava extends GoogleCloudService {

    private Container containerService;

    public ContainerEngineServiceJava(GoogleCloudConfiguration configuration) {
        super(configuration);
        containerService = new Container.Builder(configuration.getTransport(), configuration.getJacksonFactory(), configuration.getCredential())
                .setApplicationName("ChainCodeClusters")
                .build();
    }

    public void createContainerService(String clusterId) throws IOException, GeneralSecurityException {
        CreateNodePoolRequest requestBody = new CreateNodePoolRequest();
        Container.Projects.Zones.Clusters.NodePools.Create request =
                containerService
                        .projects()
                        .zones()
                        .clusters()
                        .nodePools()
                        .create(configuration.getProjectId(), configuration.getZone(), clusterId, requestBody);
        Operation response = request.execute();
        System.out.println(response);
    }

    public void deleteContainerCluster() {
        Cluster cluster = new Cluster();
        CreateClusterRequest requestBody = new CreateClusterRequest();
    }

    // TODO: IS THERE A WAY TO CHECK AND SEE IF GOOGLE IS FINISHED SETTING UP ENVIRONMENT BEFORE YOU ARE ABLE TO CREATE CLUSTER
    public void createContainerCluster(String clusterName, String poolName) throws IOException, GeneralSecurityException {
        List<NodePool> nodePools = new LinkedList<>();
        List<String> oauthScopes = Arrays.asList(
                "https://www.googleapis.com/auth/compute",
                "https://www.googleapis.com/auth/devstorage.read_only",
                "https://www.googleapis.com/auth/logging.write",
                "https://www.googleapis.com/auth/monitoring.write",
                "https://www.googleapis.com/auth/servicecontrol",
                "https://www.googleapis.com/auth/service.management.readonly",
                "https://www.googleapis.com/auth/trace.append"
        );
        NodeConfig nodeConfig = new NodeConfig()
                .setMachineType(MachineTypes.n1_standard_1)
                .setImageType(ImageTypes.cos)
                .setDiskSizeGb(100)
                .setPreemptible(false)
                .setOauthScopes(oauthScopes);

        NodePool nodePool = new NodePool()
                .setName(poolName)
                .setInitialNodeCount(3)
                .setConfig(nodeConfig)
                .setAutoscaling(new NodePoolAutoscaling().setEnabled(false))
                .setManagement(new NodeManagement());

        nodePools.add(nodePool);
        Cluster cluster = new Cluster()
                .setName(clusterName)
                .setZone(configuration.getZone())
                .setNetwork("default")
                .setLoggingService("logging.googleapis.com")
                .setNodePools(nodePools);

        CreateClusterRequest requestBody = new CreateClusterRequest().setCluster(cluster);
        Container.Projects.Zones.Clusters.Create createClusterRequest =
                containerService
                        .projects()
                        .zones()
                        .clusters()
                        .create(configuration.getProjectId(), configuration.getZone(), requestBody);
        Operation response = createClusterRequest.execute();
        System.out.println(response);

        Cluster c = configuration.getJacksonFactory().createJsonParser("asd").parseAndClose(Cluster.class);
    }
/*
        "autoscaling": {
          "enabled": false
        },
        "management": {
          "autoUpgrade": false,
          "autoRepair": false,
          "upgradeOptions": {}
        }
      }
    ],
    "initialClusterVersion": "1.6.7",
    "masterAuth": {
      "username": "admin",
      "clientCertificateConfig": {
        "issueClientCertificate": true
      }
    },
    "subnetwork": "default",
    "legacyAbac": {
      "enabled": true
    },
    "masterAuthorizedNetworksConfig": {
      "enabled": false,
      "cidrBlocks": []
    }
  }
}
 */
}