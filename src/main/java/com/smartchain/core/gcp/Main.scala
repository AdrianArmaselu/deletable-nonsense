package com.smartchain.core.gcp

import com.smartchain.core.gcp.model.GoogleCloudConfiguration
import com.smartchain.core.gcp.service.{ContainerEngineService}

object Main {

  def main(args: Array[String]): Unit = {
    val configuration = new GoogleCloudConfiguration()
    val containerEngineService = new ContainerEngineService(configuration)
    containerEngineService.createContainerCluster("cluster1", "pool1")
  }

}
