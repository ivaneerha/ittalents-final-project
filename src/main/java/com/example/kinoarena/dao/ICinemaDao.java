package com.example.kinoarena.dao;

import java.sql.SQLException;


import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;

public interface ICinemaDao {

	public void addCinema(Cinema cinema) throws SQLException, InvalidInputDataException;

	public void deleteCinema(Cinema cinema) throws SQLException, InvalidInputDataException;

}
