package com.sky.config;

import com.sky.properties.AliOSSProperties;
import com.sky.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@EnableConfigurationProperties(AliOSSProperties.class)
@Configuration
public class AliyunOSSAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliyunOSSOperator aliyunOSSOperator(AliOSSProperties aliOSSProperties) {
        log.info("Initializing AliyunOSSOperator with properties: {}", aliOSSProperties);
        return new AliyunOSSOperator(aliOSSProperties);
    }
}
