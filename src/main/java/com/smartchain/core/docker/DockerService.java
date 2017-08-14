package com.smartchain.core.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/*
    This class is basically a layer on top of dockerClient
 */
public class DockerService {


    private DockerClient dockerClient;

    public DockerService() throws DockerCertificateException {
        dockerClient = DefaultDockerClient.fromEnv().connectionPoolSize(16).build();
    }

    public DockerClient getDockerClient() {
        return dockerClient;
    }

    public String getContainerId(String containerName) throws DockerException, InterruptedException {
        // container names start with '/' character, so we use substring
        return dockerClient
                .listContainers(DockerClient.ListContainersParam.allContainers())
                .stream()
                .filter(container -> container.names().stream().anyMatch(s -> s.substring(1).equals(containerName)))
                .findAny().map(Container::id).orElse(null);
    }

    public boolean isContainerCreated(String containerName) throws DockerException, InterruptedException {
        return dockerClient
                .listContainers(DockerClient.ListContainersParam.allContainers())
                .stream()
                .anyMatch(container -> container.names().stream().anyMatch(s -> s.substring(1).equals(containerName)));
    }
//
//    private boolean anyContainerNameMatch(Container container){
//        container -> container.names().stream().anyMatch(s -> s.substring(1).equals(containerName)
//    }

    public void pullImages(List<String> images) {
        images.forEach(s -> {
            try {
                dockerClient.pull(s);
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void removeAllImages() throws DockerException, InterruptedException {
        dockerClient.listImages().forEach(image -> {
            try {
                dockerClient.removeImage(image.id());
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void stopAllRunningContainers() throws DockerException, InterruptedException {
        dockerClient.listContainers().forEach(container -> {
            try {
                dockerClient.stopContainer(container.id(), 1);
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void removeAllRunningContainers() throws DockerException, InterruptedException {
        dockerClient.listContainers(DockerClient.ListContainersParam.allContainers()).forEach(container -> {
            try {
                dockerClient.stopContainer(container.id(), 1);
                dockerClient.removeContainer(container.id());
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void removeUntaggedImages() throws DockerException, InterruptedException {
        dockerClient.listImages().stream()
                .filter(image -> image.repoTags().contains("<none>:<none>")
                        && image.repoDigests().contains("<none>@<none>"))
                .forEach(image -> {
                    try {
                        dockerClient.removeImage(image.id());
                    } catch (DockerException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    /*
        Run the Container. Creates one first if it does not exist already.
     */
    public void runContainer(ContainerConfig containerConfig, String containerName) throws DockerException, InterruptedException {
        String containerId;
        if (isContainerCreated(containerName)) {
            containerId = getContainerId(containerName);
        } else {
            ContainerCreation containerCreation = getDockerClient().createContainer(containerConfig, containerName);
            containerId = containerCreation.id();
        }
        getDockerClient().startContainer(containerId);
    }

    public void close() {
        dockerClient.close();
    }

    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {
        String image = "<none>:<none>";
        DockerService dockerService = new DockerService();
        dockerService.removeUntaggedImages();
        dockerService.removeAllRunningContainers();
        System.out.println(dockerService.dockerClient.listImages());
        dockerService.close();
        System.exit(0);
    }
}
