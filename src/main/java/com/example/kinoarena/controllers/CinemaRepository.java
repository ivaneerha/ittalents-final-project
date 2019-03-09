package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema,Long>{

	
}
