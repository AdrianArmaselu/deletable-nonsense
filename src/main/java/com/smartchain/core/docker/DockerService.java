package com.smartchain.core.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

import java.util.List;


// TODO: CREATE A POOL OF DOCKER CLIENTS TO SPEEDUP PROCESSING
public class DockerService {


    private DockerClient dockerClient;

    public DockerService() throws DockerCertificateException {
        dockerClient = DefaultDockerClient.fromEnv().build();
    }

    void pullImages(List<String> images){
        images.forEach(s -> {
            try {
                dockerClient.pull(s);
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    void killAllRunningContainers() throws DockerException, InterruptedException {
        dockerClient.listContainers().forEach(container -> {
            try {
                dockerClient.stopContainer(container.id(), 1);
                dockerClient.removeContainer(container.id());
            } catch (DockerException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    void removeUntaggedImages() throws DockerException, InterruptedException {
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

    void removeImage(String image) throws DockerException, InterruptedException {
        dockerClient.removeImage(image);
    }

    void close() {
        dockerClient.close();
    }

    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {
        String image = "<none>:<none>";
        DockerService dockerService = new DockerService();
        dockerService.removeUntaggedImages();
        dockerService.killAllRunningContainers();
        System.out.println(dockerService.dockerClient.listImages());
        dockerService.close();
        System.exit(0);
    }
}
