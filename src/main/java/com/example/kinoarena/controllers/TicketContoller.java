package com.example.kinoarena.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dao.TicketDao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class TicketContoller {

	@Autowired
	private TicketDao ticketDao;
	
	@PostMapping("/ticket/add")
	public void addTicket(@RequestBody TicketDto, HttpSession session) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
