package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.User;


@Component
public class UserDao {
	


	@Autowired 
	private JdbcTemplate jdbcTemlplate; 
	
	
	//TODO
	public User login(LoginDto user) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemlplate.getDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * from users WHERE (username=? AND password =?)");
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
				result.getBoolean("isAdmin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user2;
	}
	
	//TODO
	public User getUser(long user_id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemlplate.getDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * from users WHERE user_id=?");
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
				result.getBoolean("isAdmin"),
				result.getString("favourite_movie"),
				result.getString("favourite_actor"));
		return user;
	}
	
	
}
	
	
