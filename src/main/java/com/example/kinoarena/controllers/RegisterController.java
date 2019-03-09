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
import com.example.kinoarena.service.UserManager;


@RestController
public class RegisterController extends BaseController {

	private static final String LOG_OUT_TO_REGISTER = "You have to logout to register!";
	private static final String EMAIL_TAKEN = "Email already taken!";
	private static final String USERNAME_TAKEN = "Username already taken!";
	
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
		if (BaseController.isLogged((request))) {
			throw new KinoArenaException(LOG_OUT_TO_REGISTER);
		}
		if (userManager.isEmailTaken(reg.getEmail())) {
			throw new KinoArenaException(EMAIL_TAKEN);
		}
		if(userManager.isUsernameTaken(reg.getUsername())) {
			throw new KinoArenaException(USERNAME_TAKEN);
		}
		User user = new User();

		user.setFirstName(reg.getFirstName());
		user.setLastName(reg.getLastName());
		user.setUsername(reg.getUsername());
		user.setEmail(reg.getEmail());
		user.setPassword(PasswordCrypt.cryptPassword(reg.getPassword()));
		userRepository.save(user);
		BaseController.logUser(request, user);
		
	}

}
