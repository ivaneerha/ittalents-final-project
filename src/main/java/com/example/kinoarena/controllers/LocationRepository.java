package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kinoarena.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
