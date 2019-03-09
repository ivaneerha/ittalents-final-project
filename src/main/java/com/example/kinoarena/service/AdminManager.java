//package com.example.kinoarena.service;
//
//import java.sql.SQLException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.example.kinoarena.dao.CinemaDao;
//import com.example.kinoarena.dao.HallDao;
//import com.example.kinoarena.dao.MovieDao;
//import com.example.kinoarena.dao.ProjectionDao;
//import com.example.kinoarena.dao.UserDao;
//import com.example.kinoarena.exceptions.InvalidInputDataException;
//import com.example.kinoarena.exceptions.NotAdminException;
//import com.example.kinoarena.model.Cinema;
//import com.example.kinoarena.model.Hall;
//import com.example.kinoarena.model.Movie;
//import com.example.kinoarena.model.Projection;
//
//@Service
//@Component
//public class AdminManager {
//	
//	@Autowired
//	private CinemaDao cinemaDao;
//	
//	@Autowired
//	private HallDao hallDao;
//	
//	@Autowired
//	private MovieDao movieDao;
//	
//	@Autowired
//	private ProjectionDao projectionDao;
//	
//	@Autowired
//	private UserDao userDao;
//	
//	
////	public void addNewProjection(Projection b) throws SQLException, NotAdminException, InvalidInputDataException {
////			projectionDao.addProjection(b);
////	}
//
//	
//	public void addNewCinema(Cinema c) throws SQLException, NotAdminException, InvalidInputDataException {
//			cinemaDao.addCinema(c);
//	}
//
//
//	public void removeProjection(int projectionId) throws SQLException, NotAdminException, InvalidInputDataException {	
//			projectionDao.deleteProjection(projectionId);
//	}
//	
//	public void removeCinema(Cinema c) throws SQLException, NotAdminException, InvalidInputDataException {	
//			cinemaDao.deleteCinema(c);
//	}
//
//	
//
//}
