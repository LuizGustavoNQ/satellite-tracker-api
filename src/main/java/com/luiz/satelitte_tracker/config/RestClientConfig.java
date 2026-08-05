package com.luiz.satelitte_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    @Bean
    public RestClient celestrakClient() {

        return RestClient.builder()
                .baseUrl("https://celestrak.org")
                .build();

    }
}
