package com.onlineshop.usermgmt.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshop.usermgmt.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsername(String name);
	
	public User findByEmailIgnoreCase(String emailId);
	public boolean existsByEmailIgnoreCase(String emailId);
	public User findByIdAndRole(Long userId, String string);
	public List<User> findAllByRole(String role);
	public List<User> findAllByRoleNot(String lowerCase);
}
