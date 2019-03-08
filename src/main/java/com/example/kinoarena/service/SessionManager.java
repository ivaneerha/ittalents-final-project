package com.example.kinoarena.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.User;

@Component
public class SessionManager {
	
	@Autowired
	private  UserDao userDao;
	
	
	public static final String LOGGED = "LoggedUser";
	private static final int SESSION_TIMEOUT = 1000000;
	
//	public static boolean isLogged(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		if(session.isNew()) return false;
//		if(session.getAttribute(LOGGED)==null) {
//			return false;
//		}
//		return true;
//	}
//	
//	
//	public static void logUser(HttpServletRequest request, User user) throws SQLException, InvalidInputDataException {
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(SESSION_TIMEOUT);
//		session.setAttribute(LOGGED, user);
//	}
	
	
	public void validateLoginAdmin(HttpServletRequest request) throws KinoArenaException{
		HttpSession session = request.getSession();
		User logged = (User) session.getAttribute(LOGGED);
		if(logged.getIsAdmin()==0) {
			throw new NotAdminException();
		}
	}
	

}
