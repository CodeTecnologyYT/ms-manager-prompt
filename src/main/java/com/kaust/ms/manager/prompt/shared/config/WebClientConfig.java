package com.kaust.ms.manager.prompt.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /** SIZE_STRING. */
    final static int SIZE_STRING = 16 * 1024 * 1024;

    /**
     * Create a WebClient bean.
     */
    @Bean
    public WebClient webClient() {
        final var strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(SIZE_STRING))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

}
