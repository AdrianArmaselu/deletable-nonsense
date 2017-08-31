package com.smartchain.core.ethereum;

/* Copyright (C) Smartchain - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Adrian Armaselu <adrian@smartchain.com>, August 2017
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.ECKey;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class EthereumService {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();



//        Transaction requestTransaction = new Transaction("")
        Web3j web3 = Web3j.build(new HttpService("http://54.85.102.92:8545"));
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard("password", ecKeyPair);




//            byte[] object = objectMapper.writeValueAsBytes(requestTransaction);

        } catch (NoSuchAlgorithmException | NoSuchProviderException | CipherException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }


//        web3.ethAccounts().send()
//        web3.ethSendTransaction();
//        System.out.println(web3.);
    }

    private void generateKeys(){
        ECKey key = new ECKey();

        byte[] addr = key.getAddress();
        byte[] priv = key.getPrivKeyBytes();

        String addrBase16 = Hex.toHexString(addr);
        String privBase16 = Hex.toHexString(priv);

        System.out.println("Address     : " + addrBase16);
        System.out.println("Private Key : " + privBase16);
    }
}
