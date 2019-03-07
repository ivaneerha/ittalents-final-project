package com.example.kinoarena.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dao.TicketDao;
import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Ticket;
import com.example.kinoarena.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class TicketContoller {

	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@PostMapping("/ticket/add")
	public void addTicket(@RequestBody TicketDto ticketDto, HttpSession session) throws SQLException, KinoArenaException {
		
		//TODO check if logged in
		User logged = (User) session.getAttribute("LoggedUser");
		//CHECK HERE IF SEAT IF TAKEN BEFORE 'buying' ticket
		Ticket ticket = new Ticket();
		ticket.setCinemaId(ticketDto.getCinemaId());
		ticket.setProjectionId(ticketDto.getProjectionId());
		ticket.setUserId(logged.getUserId());
		ticket.setStartTime(ticketDao.getStartTime(ticketDto.getProjectionId()));
		ticketRepository.save(ticket);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
