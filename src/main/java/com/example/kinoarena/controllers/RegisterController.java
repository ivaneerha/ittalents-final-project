package com.example.kinoarena.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.RegisterDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.User;
import com.example.kinoarena.passwordcrypt.PasswordCrypt;
import com.example.kinoarena.service.SessionManager;
import com.example.kinoarena.service.UserManager;


@RestController
public class RegisterController extends BaseController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserManager userManager;
	
	@Autowired
	UserDao userDao;

	@PostMapping("/register")
	public void signUp(@RequestBody RegisterDto reg, HttpServletRequest request)
			throws KinoArenaException, SQLException, NoSuchAlgorithmException {
		new UserValidation().validateRegistration(reg);
		if (SessionManager.isLogged(request)) {
			throw new KinoArenaException("You have to logout to register!");
		}
		if (userManager.isEmailTaken(reg.getEmail())) {
			throw new KinoArenaException("Email already taken!");
		}
		User user = new User();

		user.setFirstName(reg.getFirstName());
		user.setLastName(reg.getLastName());
		user.setUsername(reg.getUsername());
		user.setEmail(reg.getEmail());
		user.setPassword(PasswordCrypt.cryptPassword(reg.getPassword()));
		userRepository.save(user);
		SessionManager.logUser(request, user);
		
	}

}
