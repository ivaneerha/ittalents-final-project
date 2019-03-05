package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kinoarena.model.Projection;

public interface ProjectionRepository extends JpaRepository<Projection,Long>{
	
	Projection findByProjectionId(long id);

}
