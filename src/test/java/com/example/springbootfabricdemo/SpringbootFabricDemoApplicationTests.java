package com.example.springbootfabricdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.springbootfabricdemo.config.FabricConfig;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.*;

import java.nio.file.Paths;
import java.security.PrivateKey;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
@SpringBootTest
class SpringbootFabricDemoApplicationTests {

    @Autowired
    FabricComponent fabricComponent;

    @Autowired
    FabricConfig fabricConfig;

    String userId = "user112";
    String orgId = "Org1";


    @Test
    void fastjson() throws Exception {
        AccountInfo accountInfo = parseObject("{\n" +
                "    \"id\": \"id1\",\n" +
                "    \"username\": \"uname1\",\n" +
                "    \"password\": \"pwd1\",\n" +
                "    \"balance\": \"12\",\n" +
                "    \"orgId\": \"org\"\n" +
                "}",AccountInfo.class);
        int a = 1;
    }

    <T> T parseObject(String json, Class<T> clazz) throws Exception {
        T obj = (T) JSON.parseObject(json,clazz);
        return obj;
    }


    @Test
    void contextLoads() throws Exception {

        fabricComponent.enrollAdmin("admin", "adminpw");
        fabricComponent.registerUser(userId);
        // 查询余额
//        String resp = fabricComponent.invokeQuery(userId, "wallet", "zhuhao2.js");
//        System.out.println(resp);
//        // 查询交易记录
//        resp = fabricComponent.invokeQuery(userId, "record", "zhuhao2.js");
//        System.out.println(resp);
//        List<TxInfo> emm = JSON.parseArray(resp, TxInfo.class);
//        // 转账
//        resp = fabricComponent.invokeTx(userId, "donate", "zhuhao2.js", "guojingyu.js", "1");
//        System.out.println(resp);
//        this.registerUser("user107","Org1");
    }

    @Test
    void txInfo() throws Exception {
        // 查询交易记录
        String resp = fabricComponent.invokeQuery(userId, "record", "zhuhao2.js");
        System.out.println(resp);
        Map<String,JSONArray> emm = JSON.parseObject(resp,Map.class);
        JSONArray emm2 = emm.get("records");
        List<TxInfo> txInfo = emm2.toJavaList(TxInfo.class);
        return;
    }

    @Test
    void registerUser(String userId,String orgId) throws Exception {
        HFCAClient caClient = this.getHFCAClient();

        // Create a wallet for managing identities
        Wallet wallet =  fabricConfig.getWallet();

        // Check to see if we've already enrolled the user.
        boolean userExists = wallet.exists(userId);
        if (userExists) {
            System.out.println("An identity for the user "+userId+" already exists in the wallet");
            return;
        }

        userExists = wallet.exists("admin");
        if (!userExists) {
            System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
            return;
        }

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

        // Register the user, enroll the user, and import the new identity into the wallet.
        RegistrationRequest registrationRequest = new RegistrationRequest(userId);
        // registrationRequest.setAffiliation("org1.department1");
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
        Identity user = Identity.createIdentity(orgId+"MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put(userId, user);
        System.out.println("Successfully enrolled user "+userId+" and imported it into the wallet");
    }

    private HFCAClient getHFCAClient() throws Exception {
        // Create a CA client for interacting with the CA.
        Properties props = new Properties();
        Path pemFilePath = Paths.get(this.getClass().getClassLoader().getResource(fabricConfig.getConfigPath() + fabricConfig.getCaName() + "-cert.pem").toURI());
        props.put("pemFile", pemFilePath.toString());
//        props.put("pemFile", "fabric/config/ca.org1.ccb.com-cert.pem");
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(fabricConfig.getCaUrl(), props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

}
