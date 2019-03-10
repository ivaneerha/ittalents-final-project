package com.example.kinoarena.controllers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

	Optional<Genre> findById(Long id);
}
