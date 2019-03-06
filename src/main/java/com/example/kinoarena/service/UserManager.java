package com.example.kinoarena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kinoarena.controllers.UserRepository;

@Service
public class UserManager {
	
	@Autowired
	UserRepository userRepository;

	public boolean isEmailTaken(String email) {
		return userRepository.findByEmail(email) != null;
		}

	public boolean isUsernameTaken(String username){
		return userRepository.findByUsername(username) !=null;
	}
	
	
	
}
