package com.onlineshop.usermgmt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.usermgmt.domain.User;
import com.onlineshop.usermgmt.domain.repository.UserRepository;
import com.onlineshop.usermgmt.dto.KeycloakUser;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.service.impl.UserServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepo;
	
	@Mock
	private KeycloakService keycloakService;
	
	@Test
	void testSignUpUser() {
		Mockito.when(keycloakService.createUserWithKeycloak(any(KeycloakUser.class))).thenReturn(201);
		Mockito.when(userRepo.save(any(User.class))).thenReturn(getUser());
		User dto = userService.signUpUser(getSignUpRequest());
		assertThat(dto.getId()).isEqualTo(1l);
	}
	
	@Test
	void testFindByName() {
		Mockito.when(userRepo.findByUsername(any(String.class))).thenReturn(getUser());
		User dto = userService.getUserByName("");
		assertThat(dto.getId()).isEqualTo(1l);
	}
	
	@Test
	void testGetUserById() {
		Mockito.when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(getUser()));
		User dto = userService.getUserByuserId(1l);
		assertThat(dto.getId()).isEqualTo(1l);
	}
	
	private SignUpRequest getSignUpRequest() {
		SignUpRequest signup = new SignUpRequest();
		signup.setEmail("test@gmail.com");
		signup.setName("test_user");
		signup.setPhoneNumber("9999988888");
		signup.setRole("seller");
		signup.setUsername("test_user");
		signup.setSurname("user");
		return signup;
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

}
