package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kinoarena.model.Location;

public interface LocationRepository extends JpaRepository<Location,Long>{

}
