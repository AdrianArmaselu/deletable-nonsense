package com.smartchain.core.gcp.service


import io.fabric8.kubernetes.api.KubernetesHelper
import io.fabric8.kubernetes.client.{DefaultKubernetesClient, KubernetesClient}

object KubernetesService {

  def main(args: Array[String]): Unit = {
    System.out.println("\n\nfabric8 Kubernetes-api example")
    val kube = new DefaultKubernetesClient("http://35.184.254.21:")
    System.out.println("=========================================================================")
    try {
      listPods(kube)
      listReplicationControllers(kube)
//      listServices(kube)
      listServiceAccounts(kube)
      listEndpoints(kube)
    } catch {
      case e: Exception =>
        System.out.println("FAILED: " + e)
        e.printStackTrace()
    } finally kube.close()
    System.out.println("=========================================================================")
  }

  protected def listPods(kube: KubernetesClient): Unit = {
    System.out.println("\n\nLooking up pods")
    System.out.println("=========================================================================")
    val pods = kube.pods.list
    //System.out.println("Got pods: " + pods);
    val items = pods.getItems
    import scala.collection.JavaConversions._
    for (item <- items) {
      System.out.println("Pod " + KubernetesHelper.getName(item) + " with ip: " + item.getStatus.getPodIP + " created: " + item.getMetadata.getCreationTimestamp)
      val spec = item.getSpec
      if (spec != null) {
        val containers = spec.getContainers
        if (containers != null) {
          import scala.collection.JavaConversions._
          for (container <- containers) {
            System.out.println("Container " + container.getImage + " " + container.getCommand + " ports: " + container.getPorts)
          }
        }
      }
      val currentContainers = KubernetesHelper.getCurrentContainers(item)
      System.out.println("Has " + currentContainers.size + " container(s)")
      val entries = currentContainers.entrySet
      import scala.collection.JavaConversions._
      for (entry <- entries) {
        val id = entry.getKey
        val info = entry.getValue
        System.out.println("Current container: " + id + " info: " + info)
      }
    }
    System.out.println()
  }



  protected def listReplicationControllers(kube: KubernetesClient): Unit = {
    System.out.println("\n\nLooking up replicationControllers")
    System.out.println("=========================================================================")
    val replicationControllers = kube.replicationControllers.list
    val items = replicationControllers.getItems
    import scala.collection.JavaConversions._
    for (item <- items) {
      val replicationControllerSpec = item.getSpec
      if (replicationControllerSpec != null) System.out.println("ReplicationController " + KubernetesHelper.getName(item) + " labels: " + item.getMetadata.getLabels + " replicas: " + replicationControllerSpec.getReplicas + " replicatorSelector: " + replicationControllerSpec.getSelector + " podTemplate: " + replicationControllerSpec.getTemplate)
      else System.out.println("ReplicationController " + KubernetesHelper.getName(item) + " labels: " + item.getMetadata.getLabels + " no replicationControllerSpec")
    }
    System.out.println()
  }

  protected def listServiceAccounts(kube: KubernetesClient): Unit = {
    System.out.println("\n\nLooking up service accounts")
    System.out.println("=========================================================================")
    val serviceAccounts = kube.serviceAccounts.list
    val serviceAccountItems = serviceAccounts.getItems
    import scala.collection.JavaConversions._
    for (serviceAccount <- serviceAccountItems) {
      System.out.println("Service Account " + KubernetesHelper.getName(serviceAccount) + " labels: " + serviceAccount.getMetadata.getLabels)
    }
    System.out.println()
  }

  protected def listEndpoints(kube: KubernetesClient): Unit = {
    System.out.println("\n\nLooking up endpoints")
    System.out.println("=========================================================================")
    val endpoints = kube.endpoints.list
    val endpointItems = endpoints.getItems
    import scala.collection.JavaConversions._
    for (endpoint <- endpointItems) {
      System.out.println("Endpoint " + KubernetesHelper.getName(endpoint) + " labels: " + endpoint.getMetadata.getLabels)
    }
    System.out.println()
  }
}
