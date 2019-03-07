package com.example.kinoarena.controllers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
	
	public List<Hall> findAllByCinemaId(Long id);

	

}
