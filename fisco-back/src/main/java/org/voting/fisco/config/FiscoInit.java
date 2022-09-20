package org.voting.fisco.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.voting.fisco.contract.VotingObject;

import javax.annotation.PostConstruct;

@RestController
public class FiscoInit {
    @Autowired
    private FiscoBcos fiscoBcos;

    @PostConstruct
    public void getBlockNumber() throws Exception {
        System.out.println("init the FISCO-BCOS block chain");
        initialize();
        VotingObject.deployVoting();
    }

    public void initialize(){
        VotingObject.bcosSDK = fiscoBcos.getBcosSDK();
        VotingObject.client = VotingObject.bcosSDK.getClient(1);
        VotingObject.cryptoKeyPair = VotingObject.client.getCryptoSuite().getCryptoKeyPair();
        VotingObject.client.getCryptoSuite().setCryptoKeyPair(VotingObject.cryptoKeyPair);
    }
}

