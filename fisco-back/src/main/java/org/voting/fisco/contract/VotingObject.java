package org.voting.fisco.contract;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class VotingObject {
    public static Client client;
    public static BcosSDK bcosSDK;
    public static CryptoKeyPair cryptoKeyPair;
    public static Voting voting;

//    public static void recordAssetAddr(String address) throws IOException {
//        Properties prop = new Properties();
//        prop.setProperty("address", address);
//        final Resource contractResource = new ClassPathResource("contract.properties");
//        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
//        prop.store(fileOutputStream, "contract address");
//    }
//
//    public static String loadAssetAddr() throws Exception {
//        Properties prop = new Properties();
//        final Resource contractResource = new ClassPathResource("contract.properties");
//        prop.load(contractResource.getInputStream());
//
//        String contractAddress = prop.getProperty("address");
//        if (contractAddress == null || contractAddress.trim().equals("")) {
//            throw new Exception(" load Asset contract address failed, please deploy it first. ");
//        }
//        return contractAddress;
//    }
    public static void deployVoting() throws Exception{
        voting = Voting.deploy(client, cryptoKeyPair);
        System.out.println(
                "deploy Asset success, contract address is " + voting.getContractAddress());
//        recordAssetAddr(voting.getContractAddress());
    }

}
