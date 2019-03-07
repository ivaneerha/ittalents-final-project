package com.example.kinoarena.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.Setter;

@Component
public class TicketDao {

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	
	
}
