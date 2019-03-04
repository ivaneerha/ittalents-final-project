package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;
import com.example.kinoarena.model.Hall;

public interface IHallDao {
	
	public void addHall(Hall hall) throws SQLException, InvalidInputDataException;

	public void deleteHall(Hall hall) throws SQLException, InvalidInputDataException;

	public Collection<Hall> getAllHalls() throws SQLException, InvalidInputDataException;

	public Collection<Hall> getAllHallsForACinema(Cinema cinema) throws SQLException, InvalidInputDataException;

	public Hall getHallById(int id) throws SQLException, InvalidInputDataException;
	

}
