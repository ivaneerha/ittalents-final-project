package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Seat;

@Repository
public interface SeatReposirory extends JpaRepository<Seat,Long> {

}
