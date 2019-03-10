package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	Movie findByMovieId(Long id);

}
