package com.onlineshop.usermgmt.response;

import lombok.Data;

@Data
public class JwtResponse {
	  private String token;
	  private String type = "Bearer";
	  private Long id;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String roles;
	  private String status;
	  private String profilePic;
	  private String username;
}