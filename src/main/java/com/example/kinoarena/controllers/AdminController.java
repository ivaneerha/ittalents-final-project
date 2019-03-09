package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.example.kinoarena.dao.UserDao;

import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.User;


@RestController
public class AdminController extends BaseController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDao userDao;


	@GetMapping("/users")
	public List<User> getAll(HttpServletRequest request) throws KinoArenaException {
		validateLoginAdmin(request);
		return userRepository.findAll();
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable long id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, KinoArenaException {
		validateLoginAdmin(request);
		if (userRepository.existsById(id)) {
			userDao.deleteUserByID(id);
		} else {
			throw new KinoArenaException("There is no user with this id!");
		}
	}

	@GetMapping("/users/{id}")
	public User getAll(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException{
		validateLoginAdmin(request);
		if(userRepository.existsById(id)) {
		return userRepository.findById(id).get();
	} else {
		throw new KinoArenaException("There is no user with this id!");
	}
	
	}
}
