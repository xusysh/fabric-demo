package com.example.springbootfabricdemo.config;

import lombok.Getter;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

@Configuration
@Getter
@ConfigurationProperties(prefix = "mytest.user")
public class FabricConfig {

    String configPath;

    String walletPath = "wallet";

    String orgName = "Org1";

    String channelName = "mychannel";

    String chaincodeName = "mycc";

    String caName = "ca.org1.ccb.com";

    String caUrl = "https://localhost:7054";

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

    public Wallet getWallet() throws IOException {
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get(walletPath));
        return wallet;
    }



}
