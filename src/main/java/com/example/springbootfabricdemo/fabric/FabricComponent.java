package com.example.springbootfabricdemo.fabric;

import com.example.springbootfabricdemo.config.FabricConfig;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.gateway.Gateway.Builder;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivateKey;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import org.apache.commons.beanutils.BeanUtils;

import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

@Component
public class FabricComponent {

    @Autowired
    FabricConfig fabricConfig;

    @Autowired
    HFCAClient caClient;

    public void enrollAdmin(String userId, String passwd) throws Exception {
        Wallet wallet = fabricConfig.getWallet();
        // Check to see if we've already enrolled the admin user.
        boolean adminExists = wallet.exists(userId);
        if (adminExists) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }
        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll(userId, passwd, enrollmentRequestTLS);
        Identity user = Identity.createIdentity(fabricConfig.getOrgName() + "MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put(userId, user);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }

    public void registerUser(String userId, String passwd) throws Exception {
        Wallet wallet = fabricConfig.getWallet();
        User admin = this.getAdminFabricUser(wallet);
        // Register the user, enroll the user, and import the new identity into the wallet.
        RegistrationRequest registrationRequest = new RegistrationRequest("user4");
        // registrationRequest.setAffiliation("org1.department1");
        registrationRequest.setEnrollmentID("user4");
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll("user4", enrollmentSecret);
        Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put("user4", user);
        System.out.println("Successfully enrolled user \"user1\" and imported it into the wallet");
    }

    public void invokeQuery(String userId,String apiName, String... args) throws Exception {
        Builder builder = this.getGatewayBuilder(userId);
        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(fabricConfig.getChannelName());
            Contract contract = network.getContract(fabricConfig.getChaincodeName());

            byte[] result;
            result = contract.evaluateTransaction(apiName, args);
            System.out.println(new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Builder getGatewayBuilder(String userId) throws Exception {
        // Load a file system based wallet for managing identities.
        Wallet wallet = fabricConfig.getWallet();
        // load a CCP
        Path networkConfigPath = Paths.get(fabricConfig.getConfigPath());
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userId).networkConfig(networkConfigPath).discovery(true);
        return builder;
    }

    public void invokeTx(String userId,String apiName, String... args) {

    }

    public User getAdminFabricUser(Wallet wallet) throws IOException {
        Identity adminIdentity = wallet.get("admin");
        User admin = new User() {

            @Override
            public String getName() {
                return "admin";
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return "org1.department1";
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

                    @Override
                    public PrivateKey getKey() {
                        return adminIdentity.getPrivateKey();
                    }

                    @Override
                    public String getCert() {
                        return adminIdentity.getCertificate();
                    }
                };
            }

            @Override
            public String getMspId() {
                return "Org1MSP";
            }

        };
        return admin;
    }

}
