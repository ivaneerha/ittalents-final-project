package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;

public interface ICinemaDao {

	public void addCinema(Cinema cinema) throws SQLException, InvalidInputDataException;

	public void deleteCinema(Cinema cinema) throws SQLException, InvalidInputDataException;

	public Collection<Cinema> getAllCinemas() throws SQLException, InvalidInputDataException;

	public Cinema getCinemaById(int id) throws SQLException, InvalidInputDataException;
	
	public Cinema getCinemaByName(String name) throws SQLException, InvalidInputDataException;
	
}
