package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;

import lombok.Setter;

@Component
public class CinemaDao implements ICinemaDao{
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addCinema(Cinema cinema) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCinema(Cinema cinema) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Cinema> getAllCinemas() throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cinema getCinemaById(int id) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cinema getCinemaByName(String name) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

}
