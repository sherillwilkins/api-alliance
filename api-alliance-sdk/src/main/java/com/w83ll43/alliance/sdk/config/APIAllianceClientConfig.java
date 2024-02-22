package com.w83ll43.alliance.sdk.config;

import com.w83ll43.alliance.sdk.client.APIAllianceClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
// 扫描组件
@ComponentScan
// 读取配置文件中的设置并注入到类中
@ConfigurationProperties("api.alliance.client")
public class APIAllianceClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public APIAllianceClient apiAllianceClient() {
        return new APIAllianceClient(accessKey, secretKey);
    }
}
