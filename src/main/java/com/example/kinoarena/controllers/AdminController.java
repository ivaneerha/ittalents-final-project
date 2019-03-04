package com.example.kinoarena.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.model.User;

@RestController
public class AdminController {
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/users")
	public List<User> getAll(){
		return userDao.getAllUsers();
	}
	
	
	
	

}
