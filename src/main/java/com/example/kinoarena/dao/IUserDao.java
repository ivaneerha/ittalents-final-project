package com.example.kinoarena.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.support.SQLErrorCodes;

import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.User;

public interface IUserDao {
	
	public List<User> getAllUsers();	
	
	public User login(LoginDto user) throws SQLException, InvalidInputDataException, NoSuchAlgorithmException;
	
	public User getUserByUsername(String username) throws SQLException, InvalidInputDataException;
	
	public User getUserById(long user_id) throws SQLException, InvalidInputDataException;
	
	public String usernameExists(String username) throws SQLException;
		
	public boolean isAdmin(User user) throws SQLException, NotAdminException;
		
	public void deleteUserByID(long id) throws SQLException;
		
	public String emailExists(String email) throws SQLException;

}
