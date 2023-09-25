package com.onlineshop.usermgmt.service;

import com.onlineshop.usermgmt.domain.User;
import com.onlineshop.usermgmt.dto.SignUpRequest;

public interface UserService {
    public User signUpUser(SignUpRequest signUpRequest);
    public User getUserByName(String username);
	User getUserByuserId(long userId);
}
