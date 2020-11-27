package com.example.springbootfabricdemo.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FabricConfig {

    String configPath;

    String walletPath;

    String orgName;

    String channelName;

    String chaincodeName;

}
