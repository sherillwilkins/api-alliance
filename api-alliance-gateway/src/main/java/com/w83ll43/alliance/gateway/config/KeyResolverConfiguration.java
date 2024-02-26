package com.w83ll43.alliance.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfiguration {
    @Bean
    public KeyResolver keyResolver()
    {
        // 路径限流
        // return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
        // IP 限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
