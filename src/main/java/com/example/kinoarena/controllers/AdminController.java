package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.MovieDao;
import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.helper.RandomNumber;
import com.example.kinoarena.model.User;
import com.example.kinoarena.service.SessionManager;

@RestController
public class AdminController {
	

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@GetMapping("/users")
	public List<User> getAll(){
		return userDao.getAllUsers();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		userDao.deleteUserByID(id);
	}
	
	//TODO
	@PostMapping("/addmovie")
		public void addMovie(User admin, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException, NotAdminException {
		if(userDao.isAdmin(admin) && SessionManager.isLogged(request)) {

		}
	}
	
	
	

}