package com.example.kinoarena.controllers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {


	List<Movie> findByGenreId(Long genreId);

	Movie findByMovieId(Long id);

}
