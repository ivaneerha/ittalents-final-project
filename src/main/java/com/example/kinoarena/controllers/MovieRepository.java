package com.example.kinoarena.controllers;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kinoarena.model.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long>{
	
	
	Movie findByName(String name);
	
	ArrayList<Movie> findAllByName(String name);
	
	Movie findByNameAndPrice(String name, double price);
	
	Movie findByGenre(String genre);

}
