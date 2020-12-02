package com.example.springbootfabricdemo.config;

import lombok.Getter;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

@Configuration
@Getter
public class FabricConfig {

    String configPath;

    String walletPath;

    String orgName;

    String channelName;

    String chaincodeName;

    String caName = "ca.org1.ccb.com";

    String caUrl = "https://localhost:7054";

    HFCAClient hfcaClient = null;

    @Bean
    public HFCAClient HFCAClient() throws Exception {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        props.put("pemFile", this.configPath + this.caName + "-cert.pem");
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(caUrl, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

}
