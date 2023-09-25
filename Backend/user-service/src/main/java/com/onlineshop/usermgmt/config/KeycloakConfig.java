package com.onlineshop.usermgmt.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
	
	@Value("${keycloak.auth-server-url}")
	private String serverUrl;
    
	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${app.keycloak.client.id}")
	private String clientId;
	
	@Value("${app.keycloak.client.secret}")
	private String clientSecret;
	
	@Value("${app.keycloak.client.username}")
	private String userName;
	
	@Value("${app.keycloak.client.password}")
	private String password;

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak(){
          return Keycloak.getInstance(serverUrl,
                realm,
                userName,
                password,
                clientId,
                clientSecret);
    }
}
