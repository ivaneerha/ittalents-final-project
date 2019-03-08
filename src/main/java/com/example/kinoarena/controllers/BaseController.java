package com.example.kinoarena.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.exceptions.HallNotFoundException;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.MovieNotFoundException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.exceptions.NotLoggedInException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
import com.example.kinoarena.model.User;
import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.model.ErrorMessage;

/**
 * RestController for handling all Exception handlers
 * 
 *
 */

@RestController
public abstract class BaseController {
	public static final String LOGGED = "LoggedUser";
	private static final int SESSION_TIMEOUT = 1000000;


	@ExceptionHandler({ NotLoggedInException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessage NotLoggedIn(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ NotAdminException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessage NotAdmin(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ ProjectionNotFoundException.class, MovieNotFoundException.class, HallNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage NotFound(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
		return msg;
	}
	
	@ExceptionHandler({ InvalidInputDataException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage invalidInput(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return msg;
	}
	
	@ExceptionHandler({NumberFormatException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage failToConvertError(Exception e) {
		ErrorMessage msg = new ErrorMessage("Wrong input!", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ KinoArenaException.class, SQLException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage KinoArenaErrors(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
	public ErrorMessage AllErrors(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.I_AM_A_TEAPOT.value(),
				LocalDateTime.now());
		return msg;
	}

	protected void validateLogin(HttpSession session) throws KinoArenaException {
		if (session.getAttribute(LOGGED) == null) {
			throw new NotLoggedInException();
		}
	}


	public void validateLoginAdmin(HttpServletRequest request) throws KinoArenaException {
		HttpSession session = request.getSession();
		if (session.getAttribute(LOGGED) == null) {
			throw new NotLoggedInException();
		} else {
			User logged = (User) session.getAttribute(LOGGED);
			if (logged.getIsAdmin() == 0) {
				throw new NotAdminException();
			}
		}
	}
	
	public static boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.isNew()) return false;
		if(session.getAttribute(LOGGED)==null) {
			return false;
		}
		return true;
	}
	
	
	public static void logUser(HttpServletRequest request, User user) throws SQLException, InvalidInputDataException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_TIMEOUT);
		session.setAttribute(LOGGED, user);
	}

}
