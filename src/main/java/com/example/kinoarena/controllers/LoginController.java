package com.example.kinoarena.controllers;

import java.security.NoSuchAlgorithmException;
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
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.User;
import com.example.kinoarena.passwordcrypt.PasswordCrypt;

@RestController
public class LoginController extends BaseController {

	private static final String WRONG_CREDENTIALS = "Wrong password or username!";
	private static final String ALREADY_LOGGED_IN = "You are already logged!";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDao userDao;

	@PostMapping("/login")
	public void login(@RequestBody LoginDto log, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, KinoArenaException, NoSuchAlgorithmException {
		if (!BaseController.isLogged(request)) {
			new UserValidation().validateLogin(log);
			System.out.println(log);
			User user = userRepository.findByUsername(log.getUsername());
			if (user.getPassword().equals(PasswordCrypt.cryptPassword(log.getPassword()))) {
				userDao.login(log);
				BaseController.logUser(request, user);
			} else {
				throw new KinoArenaException(WRONG_CREDENTIALS);
			}
		} else {
			throw new KinoArenaException(ALREADY_LOGGED_IN);
		}
	}
}
