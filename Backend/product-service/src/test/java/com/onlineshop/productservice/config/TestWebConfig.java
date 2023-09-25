package com.onlineshop.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Profile("test")
public class TestWebConfig extends WebMvcConfigurationSupport {
//	@Bean
//    RestTemplate restTemplate() {
//        return TraceableRestTemplate.create();
//    }
}