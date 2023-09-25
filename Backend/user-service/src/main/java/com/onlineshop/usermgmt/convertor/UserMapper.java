package com.onlineshop.usermgmt.convertor;

import org.springframework.stereotype.Component;

import com.onlineshop.usermgmt.domain.User;
import com.onlineshop.usermgmt.dto.SignUpRequest;

@Component
public class UserMapper {

    public static User signUpRequestToUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRole(signUpRequest.getRole());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());

        return user;
    }
}
