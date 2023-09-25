package com.onlineshop.usermgmt.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshop.usermgmt.convertor.UserMapper;
import com.onlineshop.usermgmt.domain.User;
import com.onlineshop.usermgmt.domain.repository.UserRepository;
import com.onlineshop.usermgmt.dto.KeycloakUser;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.exception.UserMgmtException;
import com.onlineshop.usermgmt.service.KeycloakService;
import com.onlineshop.usermgmt.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private KeycloakService keycloakService;

    @Override
    public User signUpUser(SignUpRequest signUpRequest) {

        LOGGER.info("UserServiceImpl- signUpUser triggered");

        KeycloakUser keyCloak = new KeycloakUser();
        keyCloak.setFirstName(signUpRequest.getName());
        keyCloak.setLastName(signUpRequest.getSurname());
        keyCloak.setEmail(signUpRequest.getEmail());
        keyCloak.setPassword(signUpRequest.getPassword());
        keyCloak.setRole(signUpRequest.getRole());
        keyCloak.setUsername(signUpRequest.getUsername());
        int status = 0;
        try {
        	status = keycloakService.createUserWithKeycloak(keyCloak);
		} catch (Exception e) {
			throw new UserMgmtException(e.getMessage());
		}

        if(status == 201){
            LOGGER.info("UserServiceImpl | signUpUser | status : " + status);
            User signUpUser = UserMapper.signUpRequestToUser(signUpRequest);
            signUpUser.setCreatedAt(LocalDateTime.now());
            return userRepository.save(signUpUser);
        } else {
        	throw new UserMgmtException("Exception occured during signup user");
        }
    }
    
    @Override
    public User getUserByName(String username) {
    	User user = userRepository.findByUsername(username);
    	return user;
    }
    
    @Override
    public User getUserByuserId(long userId) {
    	Optional<User> user = userRepository.findById(userId);
    	if(user.isPresent()) {
    		return user.get();
    	}
    	return null;
    }
}
