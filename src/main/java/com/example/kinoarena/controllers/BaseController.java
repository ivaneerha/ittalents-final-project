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
	private static final String CHECK_IF_IS_ADMIN = "SELECT is_admin FROM users WHERE username = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@ExceptionHandler({ ProjectionNotFoundException.class, MovieNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage NotFound(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
		return msg;
	}
	
	@ExceptionHandler({ InvalidInputDataException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage invalidInput(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ KinoArenaException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage KinoArenaErrors(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return msg;
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage AllErrors(Exception e) {
		ErrorMessage msg = new ErrorMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				LocalDateTime.now());
		return msg;
	}

	protected void validateLogin(HttpSession session) throws KinoArenaException {
		if (session.getAttribute(LOGGED) == null) {
			throw new NotLoggedInException();
		}
	}

//	protected void validateLoginAdmin(HttpSession session) throws KinoArenaException {
//		if (session.getAttribute(LOGGED) == null) {
//			throw new NotAdminException();
//		} else {
//			User logged = (User) (session.getAttribute(LOGGED));
//			if (logged.getIsAdmin()==0) {
//				throw new NotAdminException();
//			}
//		}
//	}
	public void validateLoginAdmin(HttpServletRequest request) throws KinoArenaException {
		HttpSession session = request.getSession();
		if (session.getAttribute(LOGGED) == null) {
			throw new NotLoggedInException();
		} else {
			User logged = (User) session.getAttribute(LOGGED);
			if (logged.getIsAdmin() == 1) {
				throw new NotAdminException();
			}
		}
	}

}
