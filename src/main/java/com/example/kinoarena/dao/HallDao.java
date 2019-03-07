package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.AddHallDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Cinema;
import com.example.kinoarena.model.Hall;

import lombok.Setter;

@Component
public class HallDao{

	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;



}
