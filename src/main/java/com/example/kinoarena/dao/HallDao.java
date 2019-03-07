package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.AddHallDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;
import com.example.kinoarena.model.Hall;

import lombok.Setter;

@Component
public class HallDao{

	//TODO NOT WORKING
	private static final String ADD_NEW_HALL = "INSERT INTO halls('type','cinema_id') VALUES(?,?)";

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;

	//TODO NEED TO JOIN TABLES TO ADDHALL
	public void addHall(AddHallDto hall) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(ADD_NEW_HALL);) {
			ps.setString(1, hall.getType());
			ps.setLong(2, hall.getCinemaId());
			ps.executeUpdate();
		}
	}


}
