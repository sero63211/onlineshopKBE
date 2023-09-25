package com.onlineshop.usermgmt.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.usermgmt.dto.LoginRequest;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.service.KeycloakService;
import com.onlineshop.usermgmt.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private KeycloakService keycloakService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testUserSignup() throws Exception {
		SignUpRequest signup = new SignUpRequest();
		signup.setEmail("test@gmail.com");
		signup.setName("test_user");
		signup.setPhoneNumber("9999988888");
		signup.setRole("seller");
		signup.setUsername("test_user");
		signup.setSurname("user");
		Mockito.when(userService.signUpUser(any(SignUpRequest.class))).thenReturn(getUser());
		mockMvc.perform( post("/user/signup")
				.content(objectMapper.writeValueAsString(getUser()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testUserSignin() throws Exception {
		Mockito.when(keycloakService.loginWithKeycloak(any(LoginRequest.class))).thenReturn(getAccessTokenResponse());
		Mockito.when(userService.getUserByName(any(String.class))).thenReturn(getUser());
		mockMvc.perform( post("/user/signin")
				.content(objectMapper.writeValueAsString(getUser()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testGetUserById() throws Exception {
		Mockito.when(userService.getUserByuserId(any(Long.class))).thenReturn(getUser());
		mockMvc.perform( get("/user/1")
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
		user.setSurname("user");
		return user;
	}
	
	public AccessTokenResponse getAccessTokenResponse() {
		AccessTokenResponse r = new AccessTokenResponse();
		r.setToken("edfgrtete");
		return r;
	}

}
