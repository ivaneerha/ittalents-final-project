package com.example.kinoarena.controllers;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Projection;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection,Long>{
	
	Projection findByProjectionId(long id);
	Projection findByMovieId(long movieId);
//	Projection findByMovieIdAndStartTime(long MovieId, LocalDate startTime);
//	boolean exists(Projection projection);
	Projection findByMovieIdAndStartTime(Long movieId, String startTime);
	

}
