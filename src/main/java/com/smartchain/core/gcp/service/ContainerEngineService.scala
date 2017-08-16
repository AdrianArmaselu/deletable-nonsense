package com.smartchain.core.gcp.service

import java.io.IOException
import java.security.GeneralSecurityException
import java.util

import com.google.api.services.container.Container
import com.google.api.services.container.model._
import com.smartchain.core.gcp.model.{GoogleCloudConfiguration, ImageTypes, MachineTypes}

// TODO: IS THERE A WAY TO CHECK AND SEE IF GOOGLE IS FINISHED SETTING UP ENVIRONMENT BEFORE YOU ARE ABLE TO CREATE CLUSTER

class ContainerEngineService(var configuration: GoogleCloudConfiguration) {

  var containerService: Container = new Container.Builder(
    configuration.getTransport,
    configuration.getJacksonFactory,
    configuration.getCredential
  ).setApplicationName("ChainCodeClusters").build

  def deleteContainerCluster(): Unit = {
//    TODO
  }

  @throws[IOException]
  @throws[GeneralSecurityException]
  def createContainerCluster(clusterName: String, poolName: String): Unit = {
    val cluster = new Cluster()
      .setName(clusterName)
      .setZone(configuration.getZone)
      .setNetwork("default")
      .setLoggingService("logging.googleapis.com")
      .setNodePools(
        util.Arrays.asList(
          new NodePool()
            .setName(poolName)
            .setInitialNodeCount(3)
            .setConfig(
              new NodeConfig()
                .setMachineType(MachineTypes.n1_standard_1)
                .setImageType(ImageTypes.cos).setDiskSizeGb(100)
                .setOauthScopes(
                  util.Arrays.asList(
                    "https://www.googleapis.com/auth/compute",
                    "https://www.googleapis.com/auth/devstorage.read_only",
                    "https://www.googleapis.com/auth/logging.write",
                    "https://www.googleapis.com/auth/monitoring.write",
                    "https://www.googleapis.com/auth/servicecontrol",
                    "https://www.googleapis.com/auth/service.management.readonly",
                    "https://www.googleapis.com/auth/trace.append"
                  )
                )
            )
        )
      )
      .setInitialClusterVersion("1.6.7")
      .setMasterAuth(
        new MasterAuth()
          .setUsername("admin")
          .setClientCertificateConfig(new ClientCertificateConfig().setIssueClientCertificate(true))
      )
      .setSubnetwork("default")
      .setLegacyAbac(new LegacyAbac().setEnabled(true))

    val response = containerService.projects.zones.clusters.create(
      configuration.getProjectId,
      configuration.getZone,
      new CreateClusterRequest().setCluster(cluster)
    ).execute()
    print(response)
  }

  def deployContainer(): Unit = {
//    containerService.projects.zones.clusters.
  }
}
