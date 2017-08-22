package com.smartchain.core.hyperledger;

import me.grapebaba.hyperledger.fabric.ErrorResolver;
import me.grapebaba.hyperledger.fabric.Fabric;
import me.grapebaba.hyperledger.fabric.Hyperledger;
import me.grapebaba.hyperledger.fabric.models.PeerEndpoint;
import rx.functions.Action1;

import java.io.IOException;

public class HyperledgerNetworkRestService {

    public static void main(String[] args) throws IOException {
        Fabric FABRIC = Hyperledger.fabric("http://localhost:7050/");

        FABRIC.getNetworkPeers().subscribe(peersMessage -> {
            for (PeerEndpoint peerEndpoint : peersMessage.getPeers()) {
                System.out.printf("Peer message:%s\n", peerEndpoint);
            }

        });

        FABRIC.getBlock(0)
                .subscribe(block -> System.out.printf("Get Block info:%s\n", block), throwable -> {
                    Error error = ErrorResolver.resolve(throwable, Error.class);
//                        System.out.printf("Error message:%s\n", error.getError());
                });
    }
}
