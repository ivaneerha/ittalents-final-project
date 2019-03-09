package com.example.kinoarena.controllers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kinoarena.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {


	List<Movie> findByGenreId(Long genreId);

	Movie findByMovieId(Long id);

}
