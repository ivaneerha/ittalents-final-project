package com.example.kinoarena.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dao.CinemaDao;
import com.example.kinoarena.dao.HallDao;
import com.example.kinoarena.dao.MovieDao;
import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.model.Movie;

@Component
public class AdminManager {
	
	@Autowired
	private CinemaDao cinemaDao;
	
	@Autowired
	private HallDao hallDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private ProjectionDao projectionDao;
	
	@Autowired
	private UserDao userDao;
	
	
	public void addNewMovie(Movie movie,int genreType) throws SQLException {
		movieDao.addNewMovie(movie, genreType);
	}
	
	

}
