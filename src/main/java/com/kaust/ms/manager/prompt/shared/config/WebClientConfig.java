package com.kaust.ms.manager.prompt.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Create a WebClient bean.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

}
