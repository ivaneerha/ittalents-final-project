package com.example.kinoarena.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.service.SessionManager;

@RestController
public class LoginController extends BaseController{
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, KinoArenaException {
		userDao.login(user);
		SessionManager.logUser(request, user);
	
	}

}
