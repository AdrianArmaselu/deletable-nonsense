package com.smartchain.core.hyperledger;

import com.spotify.docker.client.messages.HostConfig;

import java.io.File;

public class Mounts {

    private static final String HOST_PEER_CA_FOLDER = "src/main/resources/crypto-config/peerOrganizations/org1.example.com/ca/";
    private static final String GUEST_PEER_CA_FOLDER = "/etc/hyperledger/fabric-ca-server-config";

    static final HostConfig.Bind PEER_CA_MOUNT_BIND = HostConfig.Bind
            .from(new File(HOST_PEER_CA_FOLDER).getAbsolutePath())
            .to(GUEST_PEER_CA_FOLDER)
            .build();
}
