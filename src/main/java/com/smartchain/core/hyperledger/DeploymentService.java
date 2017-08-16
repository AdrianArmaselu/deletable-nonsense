package com.smartchain.core.hyperledger;

import com.smartchain.core.docker.DockerService;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;

import java.util.Arrays;
import java.util.List;

public class DeploymentService {


    private static final String HYPERLEDGER_FABRIC_COUCHDB_IMAGE = "hyperledger/fabric-couchdb:x86_64-1.0.0";
    private static final String HYPERLEDGER_FABRIC_ORDERER_IMAGE = "hyperledger/fabric-orderer:x86_64-1.0.0";
    private static final String HYPERLEDGER_FABRIC_PEER_IMAGE = "hyperledger/fabric-orderer:x86_64-1.0.0";
    private static final String HYPERLEDGER_COMPOSER_PLAYGROUND_IMAGE = "hyperledger/fabric-orderer:x86_64-1.0.0";

    private static final List<String> HYPERLEDGER_IMAGES = Arrays.asList(
            HYPERLEDGER_FABRIC_COUCHDB_IMAGE,
            HYPERLEDGER_FABRIC_ORDERER_IMAGE,
            HYPERLEDGER_FABRIC_PEER_IMAGE,
            HYPERLEDGER_COMPOSER_PLAYGROUND_IMAGE
    );

    private DockerService dockerService;

    public DeploymentService(){
        try {
            dockerService = new DockerService();
        } catch (DockerCertificateException e) {
            e.printStackTrace();
        }
    }

    private void createDockerServices(){
        String containerId;
        String containerName = "ca.org1.example.com";
        try {
            if(dockerService.isContainerCreated(containerName)) {
                containerId = dockerService.getContainerId(containerName);
            }else{
                ContainerCreation containerCreation = dockerService.getDockerClient().createContainer(ContainerConfigurations.PEER_CA_CONFIGURATION, containerName);
                containerId = containerCreation.id();
            }
            dockerService.getDockerClient().startContainer(containerId);
        } catch (DockerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runCaContainer() throws DockerException, InterruptedException {
        dockerService.runContainer(ContainerConfigurations.PEER_CA_CONFIGURATION, "ca.org1.example.com");
    }


    public void redeployHyperledgerFabricNetwork() throws DockerException, InterruptedException {
//        try {
//            dockerService.removeAllImages();
//        } catch (DockerException | InterruptedException e) {
//            e.printStackTrace();
//        }

        // cleanup
        dockerService.removeAllRunningContainers();

        // deploy certificates

        // pull hyperledger images
        dockerService.pullImages(HYPERLEDGER_IMAGES);

        // start hyperledger from composer
        createDockerServices();

        // create channel

        // join peer to the channel

    }

    public static void main(String[] args) {
        DeploymentService deploymentService = new DeploymentService();
//        deploymentService.redeployHyperledgerFabricNetwork();
    }
}

