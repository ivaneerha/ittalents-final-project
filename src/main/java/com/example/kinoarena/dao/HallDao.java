package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;
import com.example.kinoarena.model.Hall;

import lombok.Setter;

@Component
public class HallDao implements IHallDao{
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addHall(Hall hall) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteHall(Hall hall) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Hall> getAllHalls() throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Hall> getAllHallsForACinema(Cinema cinema) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hall getHallById(int id) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

}
