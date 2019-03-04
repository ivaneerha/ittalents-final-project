package com.example.kinoarena.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.User;

@Component
public class SessionManager {
	
	@Autowired
	private  UserDao userDao;
	
	
	public static final String LOGGED = "logged";
	private static final int SESSION_TIMEOUT = 100000;
	
	public static boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.isNew()) return false;
		if(session.getAttribute(LOGGED)==null) {
			return false;
		}
		return true;
	}
	
	
	public static void logUser(HttpServletRequest request, LoginDto user) throws SQLException, InvalidInputDataException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_TIMEOUT);
		session.setAttribute(LOGGED, user);
	}
	
	
	
	

}
