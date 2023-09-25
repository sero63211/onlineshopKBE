package com.onlineshop.usermgmt.service;

import org.keycloak.representations.AccessTokenResponse;

import com.onlineshop.usermgmt.dto.KeycloakUser;
import com.onlineshop.usermgmt.dto.LoginRequest;

public interface KeycloakService {
    public AccessTokenResponse loginWithKeycloak(LoginRequest request);
    public int createUserWithKeycloak(KeycloakUser keycloakUser);
}
