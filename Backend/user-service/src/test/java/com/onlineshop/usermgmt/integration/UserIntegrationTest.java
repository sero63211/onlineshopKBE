package com.onlineshop.usermgmt.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.keycloak.representations.AccessTokenResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.usermgmt.dto.KeycloakUser;
import com.onlineshop.usermgmt.dto.LoginRequest;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.service.KeycloakService;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value=OrderAnnotation.class)
public class UserIntegrationTest {
	
	@MockBean
	private KeycloakService keycloakService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Container
	  static KeycloakContainer keycloak = new KeycloakContainer();
	
	@Test
	@Order(1)
	public void testUserSignup() throws Exception {
		Mockito.when(keycloakService.createUserWithKeycloak(any(KeycloakUser.class))).thenReturn(201);
		SignUpRequest signup = new SignUpRequest();
		signup.setEmail("test@gmail.com");
		signup.setName("test_user");
		signup.setPhoneNumber("9999988888");
		signup.setRole("seller");
		signup.setUsername("test_user");
		signup.setPassword("password");
		signup.setSurname("user");
		mockMvc.perform( post("/user/signup")
				.content(objectMapper.writeValueAsString(signup))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testUserSignin() throws Exception {
		Mockito.when(keycloakService.loginWithKeycloak(any(LoginRequest.class))).thenReturn(getAccessTokenResponse());
		mockMvc.perform( post("/user/signin")
				.content(objectMapper.writeValueAsString(getUser()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	private com.onlineshop.usermgmt.domain.User getUser() {
		com.onlineshop.usermgmt.domain.User user = new com.onlineshop.usermgmt.domain.User();
		user.setCreatedAt(LocalDateTime.now());
		user.setEmail("test@gmail.com");
		user.setId(1l);
		user.setName("test_user");
		user.setPhoneNumber("9999988888");
		user.setRole("seller");
		user.setUsername("test_user");
		user.setPassword("password");
		user.setSurname("user");
		return user;
	}
	
	public AccessTokenResponse getAccessTokenResponse() {
		AccessTokenResponse r = new AccessTokenResponse();
		r.setToken("edfgrtete");
		return r;
	}
}
