package com.example.kinoarena.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dao.TicketDao;
import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Seat;
import com.example.kinoarena.model.Ticket;
import com.example.kinoarena.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class TicketContoller extends BaseController {

	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private SeatReposirory seatRepository;

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	// TODO validations
	@PostMapping("/ticket/add")
	public void addTicket(@RequestBody TicketDto ticketDto, HttpSession session, HttpServletRequest request)
			throws SQLException, KinoArenaException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		validateLoginAdmin(request);
		try {
			con.setAutoCommit(false);
			User logged = (User) session.getAttribute("LoggedUser");
			// CHECK HERE IF SEAT IS TAKEN BEFORE 'buying' ticket
			Ticket ticket = new Ticket();
			ticket.setCinemaId(ticketDto.getCinemaId());
			ticket.setProjectionId(ticketDto.getProjectionId());
			ticket.setUserId(logged.getUserId());
			ticket.setStartTime(ticketDao.getStartTime(ticketDto.getProjectionId()));
			
			Ticket ticket2 = ticketRepository.save(ticket);
			Long ticketId = ticket2.getTicketId();
			System.out.println(ticketId);
			
			
			Long hallId = null;
			String query1 = "Select hall_id from halls where cinema_id = ? limit 1;";
			try(PreparedStatement ps = con.prepareStatement(query1)){
				ps.setLong(1, ticketDto.getCinemaId());
				ResultSet result = ps.executeQuery();
				if(result.next()) {
					hallId = result.getLong("hall_id");
				}
			}
			

			
			Seat seat = new Seat();
			seat.setLine(ticketDto.getLine());
			seat.setSeat(ticketDto.getSeat());
			seat.setHallId(hallId);
			Seat seat2 = seatRepository.save(seat);
			Long seatId = seat2.getSeatId();
			System.out.println(seatId);
			
			String query = "INSERT into ticket_seats values (?,?);";
			try(PreparedStatement ps = con.prepareStatement(query)){
				ps.setLong(1, seatId);
				ps.setLong(2, ticketId);
				int result = ps.executeUpdate();
			}
			
			
			con.commit();
		} catch (Exception e) {
			con.rollback();
			throw new InvalidInputDataException();
		}	finally {
			con.setAutoCommit(true);
		}

	}

}
