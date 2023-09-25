package com.onlineshop.usermgmt.controller;

import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.usermgmt.domain.User;
import com.onlineshop.usermgmt.dto.LoginRequest;
import com.onlineshop.usermgmt.dto.SignUpRequest;
import com.onlineshop.usermgmt.exception.UserMgmtException;
import com.onlineshop.usermgmt.response.JwtResponse;
import com.onlineshop.usermgmt.service.KeycloakService;
import com.onlineshop.usermgmt.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private KeycloakService keycloakService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUpUser(@RequestBody SignUpRequest signUpRequest){
        LOGGER.info("UserController - for signUpUser : role : {}", signUpRequest.getRole());
        LOGGER.info("UserController - for signUpUser : email : {}", signUpRequest.getEmail());
        LOGGER.info("UserController - for signUpUser : name : {}", signUpRequest.getName());
        return ResponseEntity.ok(userService.signUpUser(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){

        LOGGER.info("UserController - for login : username : {}", request.getUsername());

        AccessTokenResponse accessTokenResponse =keycloakService.loginWithKeycloak(request);
        if (accessTokenResponse == null){
            LOGGER.info("UserController - for login, got Http Status Bad Request");
           throw new UserMgmtException("User not found");
        }

        LOGGER.info("UserController - for login, got Http Status Ok");
        User user = userService.getUserByName(request.getUsername());
        JwtResponse resp = new JwtResponse();
        resp.setEmail(user.getEmail());
        resp.setFirstName(user.getName());
        resp.setLastName(user.getSurname());
        resp.setRoles(user.getRole());
        resp.setUsername(user.getUsername());
        resp.setToken(accessTokenResponse.getToken());
        resp.setId(user.getId());
        return ResponseEntity.ok(resp);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<?> getUserByid(@PathVariable("id") long id) {
    	LOGGER.info("getUserById  UserId : {}", id);
		return new ResponseEntity<User>(userService.getUserByuserId(id), HttpStatus.OK);
	}


//    @GetMapping("/info")
//    public ResponseEntity<String> infoUser(){
//
//        LOGGER.info("UserController | infoUser is started");
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        LOGGER.info("UserController | infoUser | auth toString : " + auth.toString());
//        LOGGER.info("UserController | infoUser | auth getPrincipal : " + auth.getPrincipal());
//
//        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();
//        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
//        AccessToken accessToken = session.getToken();
//
//        String username = accessToken.getPreferredUsername();
//        String email = accessToken.getEmail();
//        String lastname = accessToken.getFamilyName();
//        String firstname = accessToken.getGivenName();
//        String realmName = accessToken.getIssuer();
//        AccessToken.Access access = accessToken.getRealmAccess();
//        Set<String> roles = access.getRoles();
//
//        String role = roles.stream()
//                .filter(s -> s.equals("ROLE_USER") || s.equals("ROLE_ADMIN"))
//                .findAny()
//                .orElse("noElement");
//
//        LOGGER.info("UserController | infoUser | username : " + username);
//        LOGGER.info("UserController | infoUser | email : " + email);
//        LOGGER.info("UserController | infoUser | lastname : " + lastname);
//        LOGGER.info("UserController | infoUser | firstname : " + firstname);
//        LOGGER.info("UserController | infoUser | realmName : " + realmName);
//        LOGGER.info("UserController | infoUser | firstRole : " + role);
//
//        return ResponseEntity.ok(role);
//    }

}
