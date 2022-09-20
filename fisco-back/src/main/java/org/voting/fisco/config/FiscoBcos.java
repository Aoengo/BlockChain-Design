package org.voting.fisco.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class FiscoBcos {
    @Autowired
    private BcosConfig bcosConfig;

    private BcosSDK bcosSDK;

    @PostConstruct
    public void init() {
        ConfigProperty configProperty = loadProperty();
        try {
            ConfigOption configOption = new ConfigOption(configProperty, CryptoType.ECDSA_TYPE);
            bcosSDK = new BcosSDK(configOption);
        } catch (ConfigException e) {
            log.error("init error: {}", e);
        }
    }
    private ConfigProperty loadProperty() {
        ConfigProperty configProperty = new ConfigProperty();
        configProperty.setCryptoMaterial(bcosConfig.getCryptoMaterial());
        configProperty.setAccount(bcosConfig.getAccount());
        configProperty.setNetwork(new HashMap<String, Object>(){{
            put("peers", bcosConfig.getNetwork().get("peers"));
        }});
        configProperty.setAmop(bcosConfig.getAmop());
        configProperty.setThreadPool(bcosConfig.getThreadPool());
        return configProperty;
    }
}

