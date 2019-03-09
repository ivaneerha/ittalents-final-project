package com.example.kinoarena.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.kinoarena.dao.TicketDao;
import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.CinemaNotFoundException;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
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
	private CinemaRepository cinemaRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/ticket/add")
	public void addTicket(@RequestBody TicketDto ticketDto, HttpSession session, HttpServletRequest request)
			throws SQLException, KinoArenaException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		validateLoginAdmin(session);
		try {
			con.setAutoCommit(false);
			if (!cinemaRepository.existsById(ticketDto.getCinemaId())) {
				throw new CinemaNotFoundException("There is no such cinema!");
			}
			if (!projectionRepository.existsById(ticketDto.getProjectionId())) {
				throw new ProjectionNotFoundException("There is no such projection!");
			}
			User logged = (User) session.getAttribute("LoggedUser");
			// CHECK HERE IF SEAT IS TAKEN BEFORE 'buying' ticket
			Ticket ticket = new Ticket();
			if(cinemaRepository.existsById(ticketDto.getCinemaId())) {
			ticket.setCinemaId(ticketDto.getCinemaId());
			} else {
				throw new CinemaNotFoundException("There is no such cinema!");
			}
			ticket.setProjectionId(ticketDto.getProjectionId());
			ticket.setUserId(logged.getUserId());
			ticket.setStartTime(ticketDao.getStartTime(ticketDto.getProjectionId()));

			Ticket ticket2 = ticketRepository.save(ticket);
			Long ticketId = ticket2.getTicketId();
			System.out.println(ticketId);

			Long hallId = null;
			String query1 = "Select hall_id from halls where cinema_id = ? limit 1;";
			try (PreparedStatement ps = con.prepareStatement(query1)) {
				ps.setLong(1, ticketDto.getCinemaId());
				ResultSet result = ps.executeQuery();
				if (result.next()) {
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
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setLong(1, seatId);
				ps.setLong(2, ticketId);
				int result = ps.executeUpdate();
			}

			con.commit();
		} catch (ProjectionNotFoundException | CinemaNotFoundException e) {
			throw new KinoArenaException(e.getMessage());
		} catch (Exception e) {
			con.rollback();
			throw new InvalidInputDataException();
		} finally {
			con.setAutoCommit(true);
		}

	}

//	// TODO validations
//	// ako imame greshka pri mestata se syzdava bilet v bazata, no obratnoto ne e taka
//	@PostMapping("/ticket/add")
//	public void addTicket(@RequestBody TicketDto ticketDto, HttpSession session, HttpServletRequest request)
//			throws SQLException, KinoArenaException {
//		Connection con = jdbcTemplate.getDataSource().getConnection();
//		validateLoginAdmin(request);
//		try {
////			con.setAutoCommit(false);
//			if(!cinemaRepository.existsById(ticketDto.getCinemaId())) {
//				throw new CinemaNotFoundException("There is no such cinema!");
//			}
//			if(!projectionRepository.existsById(ticketDto.getProjectionId())) {
//				throw new ProjectionNotFoundException("There is no such projection!");
//			}
//			User logged = (User) session.getAttribute("LoggedUser");
//			// CHECK HERE IF SEAT IS TAKEN BEFORE 'buying' ticket
//			Ticket ticket = new Ticket();
//			ticket.setCinemaId(ticketDto.getCinemaId());
//			ticket.setProjectionId(ticketDto.getProjectionId());
//			ticket.setUserId(logged.getUserId());
//			ticket.setStartTime(ticketDao.getStartTime(ticketDto.getProjectionId()));
//			
//			Ticket ticket2 = ticketRepository.save(ticket); // nai - nakraq
////			Long ticketId = ticket2.getTicketId();
////			System.out.println(ticketId);
//			
//			String query1 = "SELECT ticket_id from tickets order by ticket_id desc limit 1;";
//			Long ticketId = (long) 1;
//			ResultSet rs = con.createStatement().executeQuery(query1);
//			if(rs.next()) {
//				ticketId = (rs.getLong("ticket_id") + 1);
//			} 
//			System.out.println("TICKET ID ----------------" + ticketId);
//			
//			Long hallId = null;
//			String query2 = "Select hall_id from halls where cinema_id = ? limit 1;";
//			try(PreparedStatement ps = con.prepareStatement(query2)){
//				ps.setLong(1, ticketDto.getCinemaId());
//				ResultSet result = ps.executeQuery();
//				if(result.next()) {
//					hallId = result.getLong("hall_id");
//				}
//			}
//			
//
//			
//			Seat seat = new Seat();
//			seat.setLine(ticketDto.getLine());
//			seat.setSeat(ticketDto.getSeat());
//			seat.setHallId(hallId);
//			Seat seat2 = seatRepository.save(seat);
//			Long seatId = seat2.getSeatId();
//			System.out.println(seatId);
//			
//			String query3 = "INSERT into ticket_seats values (?,?);";
//			try(PreparedStatement ps = con.prepareStatement(query3)){
//				ps.setLong(1, seatId);
//				ps.setLong(2, ticketId);
//				int result = ps.executeUpdate();
//			}
//			
////			ticketRepository.save(ticket);
//			con.commit();
//		} catch (ProjectionNotFoundException | CinemaNotFoundException e){
//			throw new KinoArenaException(e.getMessage());
//		} catch (Exception e) {
////			con.rollback();
//			throw new InvalidInputDataException();
//		}finally {
////			con.setAutoCommit(true);
//		}

}
