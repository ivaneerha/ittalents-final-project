package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.User;

import lombok.Setter;


@Component
public class UserDao implements IUserDao{
	
	private static final String CHECK_IF_IS_ADMIN = "SELECT is_admin FROM users WHERE username = ?";
	private static final String LOGIN = "SELECT * from users WHERE (username=? AND password =?)";
	private static final String GET_USER_BY_ID = "SELECT * from users WHERE user_id=?";
	private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE user_id = ?";
	private static final String GET_ALL_USERS = "SELECT * FROM users;";
	private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
	private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	private static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

	

	//CHECH IF SETTER IS NEEDED
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate; 
	
	
	public List<User> getAllUsers(){
		//for every row from resultSet return user
		return jdbcTemplate.query(GET_ALL_USERS, (resultSet,i) ->forUser(resultSet));
	}
	
	private User forUser(ResultSet result) throws SQLException {
		User user2 = new User(
				result.getLong("user_id"),
				result.getString("username"),
				result.getString("password"),
				result.getString("first_name"),
				result.getString("last_name"),
				result.getLong("location_id"),
				result.getString("email"),
				result.getString("gsm"),
				result.getByte("isAdmin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user2;
	}
	
	public User login(LoginDto user) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement(LOGIN);
		ps.setString(1,user.getUsername());
		ps.setString(2, user.getPassword());
		ResultSet result = ps.executeQuery();
		result.next();
		User user2 = new User(
				result.getLong("user_id"),
				result.getString("username"),
				result.getString("password"),
				result.getString("first_name"),
				result.getString("last_name"),
				result.getLong("location_id"),
				result.getString("email"),
				result.getString("gsm"),
				result.getByte("is_admin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user2;
	}
	
	
	public User getUserByUsername(String username) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement(GET_USER_BY_USERNAME);
		ps.setString(1,username);
		ResultSet result = ps.executeQuery();
		result.next();
		User user = new User(
				result.getLong("user_id"),
				result.getString("username"),
				result.getString("password"),
				result.getString("first_name"),
				result.getString("last_name"),
				result.getLong("location_id"),
				result.getString("email"),
				result.getString("gsm"),
				result.getByte("isAdmin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user;
	}
	
	public User getUserById(long user_id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID);
		ps.setLong(1,user_id);
		ResultSet result = ps.executeQuery();
		result.next();
		User user = new User(
				result.getLong("user_id"),
				result.getString("username"),
				result.getString("password"),
				result.getString("first_name"),
				result.getString("last_name"),
				result.getLong("location_id"),
				result.getString("email"),
				result.getString("gsm"),
				result.getByte("isAdmin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user;
	}
	
	
	public String usernameExists(String username) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try(PreparedStatement usernameExists = con.prepareStatement(GET_USER_BY_USERNAME);){
			usernameExists.setString(1, username);
			try(ResultSet result = usernameExists.executeQuery()){
				if(result.next()) {
					return username;
				}
			}
		}
		return null;
	}
	
	public boolean isAdmin(User user) throws SQLException, NotAdminException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement checkAdmin = con.prepareStatement(CHECK_IF_IS_ADMIN);) {
			String username = this.usernameExists(user.getUsername());
			checkAdmin.setString(1, username);
			try (ResultSet result = checkAdmin.executeQuery()) {
				if (result.next()) {
					if (result.getInt("isAdmin") == 1) {
						return true;
					}
				}
			}
			throw new NotAdminException();
		}
	}
	
	public void deleteUserByID(long id) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement deleteUserByID = con.prepareStatement(DELETE_USER_BY_ID);) {
			deleteUserByID.setLong(1, id);
			deleteUserByID.executeUpdate();
		}
	}

	public String emailExists(String email) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement emailExists = con.prepareStatement(GET_USER_BY_EMAIL);) {
			emailExists.setString(1, email);
			try (ResultSet result = emailExists.executeQuery()) {
				if (result.next()) {
					return email;
				}
			}
		}
		return null;
	}

	
}
	
	
