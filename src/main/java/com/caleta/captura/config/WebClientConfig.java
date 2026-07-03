package com.caleta.captura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient especieWebClient() {
        return WebClient.builder()
            .baseUrl("https://especie-services.onrender.com")
            .build();
    }

}
