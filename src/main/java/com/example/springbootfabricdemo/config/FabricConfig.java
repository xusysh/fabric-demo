package com.example.springbootfabricdemo.config;

import lombok.Getter;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@Getter
@ConfigurationProperties(prefix = "demo.fabric")
public class FabricConfig {

    String configPath = "fabric/config/";

    String walletPath = "fabric/wallet/";

    String userId = "user111";
    String orgName = "Org3";

    String channelName = "mychannel";

    String chaincodeName = "mycc";

    String caName = "ca.org3.ccb.com";

    String caUrl = "https://106.15.193.9:7054";

    @Bean
    public HFCAClient HFCAClient() throws Exception {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        Path pemFilePath = Paths.get(this.configPath + this.caName + "-cert.pem");
        props.put("pemFile", pemFilePath.toString());
//        props.put("pemFile", Paths.get(this.configPath + this.caName + "-cert.pem"));
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(caUrl, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    public Wallet getWallet() throws Exception {
        Path walletPath = Paths.get(this.walletPath);
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        return wallet;
    }


}