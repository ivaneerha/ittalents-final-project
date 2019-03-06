package com.example.kinoarena.controllers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	
	public User findByUsername(String username);
	
	public List<User> findAll();

	public User findByEmail(String email);
	
	}



