package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Projection;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection,Long>{
	
	Projection findByProjectionId(long id);

}
