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

//	private static final String HALL_ID = "hall_id";

//	private static final String INSERT_QUERY = "INSERT into ticket_seats values (?,?);";

//	private static final String SELECT_HALL_ID = "Select hall_id from halls where cinema_id = ? limit 1;";

//	private static final String LOGGED_USER = "LoggedUser";

	private static final String PROJECTION_NOT_FOUND = "There is no such projection!";

	private static final String CINEMA_NOT_FOUND = "There is no such cinema!";

	private static final int MAX_NUM_FOR_SEAT = 10;

	private static final String SEAT_EXCEPTION_MESSAGE = "There is no seat with this number!";

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
			Ticket ticket = new Ticket();
			
			//checking projection
			if (!projectionRepository.existsById(ticketDto.getProjectionId())) {
				throw new ProjectionNotFoundException(PROJECTION_NOT_FOUND);
			}
			
			//checking cinema
			if(!cinemaRepository.existsById(ticketDto.getCinemaId())) {
				throw new CinemaNotFoundException(CINEMA_NOT_FOUND);
			}
			
			//setting projection_id, user_id, cinema_id and start_time to ticket
			ticket = ticketDao.generateTicket(ticketDto, session);

			ticketRepository.save(ticket);
			Long ticketId = ticket.getTicketId();

			//getting hall_id
			Long hallId = ticketDao.getHallIdForThisCinema(ticketDto);
			
			//checking seats
			if(!((ticketDto.getLine() > 0 && ticketDto.getLine() <= MAX_NUM_FOR_SEAT) && (ticketDto.getSeat() > 0 && ticketDto.getSeat() <= MAX_NUM_FOR_SEAT))) {
				throw new KinoArenaException(SEAT_EXCEPTION_MESSAGE);
			}
			
			//setting line, seat and hall_id to seats
			Seat seat = new Seat();
			seat.setLine(ticketDto.getLine());
			seat.setSeat(ticketDto.getSeat());
			seat.setHallId(hallId);
			
			seatRepository.save(seat);
			Long seatId = seat.getSeatId();

			//setting seat_id and ticket_id to ticket_seats
			ticketDao.setIntoTicketSeats(seatId, ticketId);

			con.commit();
		} catch (KinoArenaException e) {
			throw new KinoArenaException(e.getMessage());
		} catch (Exception e) {
			con.rollback();
			throw new InvalidInputDataException();
		} finally {
			con.setAutoCommit(true);
		}
	}
}
