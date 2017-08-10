package com.smartchain.core.hyperledger;

import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;

public class ContainerConfigurations {

    private static final String HYPERLEDGER_FABRIC_CA_IMAGE = "hyperledger/fabric-ca:x86_64-1.0.0";


    static final ContainerConfig PEER_CA_CONFIGURATION = ContainerConfig.builder()
            .hostConfig(HostConfig.builder().appendBinds(Mounts.PEER_CA_MOUNT_BIND).build())
            .image(HYPERLEDGER_FABRIC_CA_IMAGE)
            .env("FABRIC_CA_HOME=/etc/hyperledger/fabric-ca-server")
            .env("FABRIC_CA_SERVER_CA_NAME=ca.org1.example.com")
            .exposedPorts("7054:7054")
            .cmd("sh", "-c", "fabric-ca-server start --ca.certfile /etc/hyperledger/fabric-ca-server-config/ca.org1.example.com-cert.pem --ca.keyfile /etc/hyperledger/fabric-ca-server-config/19ab65abbb04807dad12e4c0a9aaa6649e70868e3abd0217a322d89e47e1a6ae_sk -b admin:adminpw -d")
            .build();
}
