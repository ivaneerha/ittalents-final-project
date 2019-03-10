package com.example.kinoarena.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.TicketDao;
import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.CinemaNotFoundException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
import com.example.kinoarena.model.Seat;
import com.example.kinoarena.model.Ticket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class TicketContoller extends BaseController {

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

	@PostMapping("/ticket/add")
	public void addTicket(@RequestBody TicketDto ticketDto, HttpSession session, HttpServletRequest request)
			throws SQLException, KinoArenaException {
		validateLoginAdmin(session);
			Ticket ticket = new Ticket();
			
			/**
			 * checking if the projection exists
			 */
			if (!projectionRepository.existsById(ticketDto.getProjectionId())) {
				throw new ProjectionNotFoundException(PROJECTION_NOT_FOUND);
			}
			
			/**
			 * checking if the cinema exists
			 */
			if(!cinemaRepository.existsById(ticketDto.getCinemaId())) {
				throw new CinemaNotFoundException(CINEMA_NOT_FOUND);
			}
			
			/**
			 * validate seats
			 */
			if(!((ticketDto.getLine() > 0 && ticketDto.getLine() <= MAX_NUM_FOR_SEAT) && (ticketDto.getSeat() > 0 && ticketDto.getSeat() <= MAX_NUM_FOR_SEAT))) {
				throw new KinoArenaException(SEAT_EXCEPTION_MESSAGE);
			}
			
			/**
			 * getting hall_id
			 */
			Long hallId = ticketDao.getHallIdForThisCinema(ticketDto);
			
			/**
			 * checking if the seats is taken for this hall
			 */
			if(ticketDao.isTheSeatTaken(hallId, ticketDto.getLine(), ticketDto.getSeat())) {
				throw new KinoArenaException("The seat is taken!");
			}
			
			/**
			 * setting projection_id, user_id, cinema_id and start_time to ticket
			 */
			ticket = ticketDao.generateTicket(ticketDto, session);

			ticketRepository.save(ticket);
			Long ticketId = ticket.getTicketId();

			/**
			 * setting line, seat and hall_id to seats
			 */
			Seat seat = new Seat();
			seat.setLine(ticketDto.getLine());
			seat.setSeat(ticketDto.getSeat());
			seat.setHallId(hallId);
			
			seatRepository.save(seat);
			Long seatId = seat.getSeatId();

			/**
			 * setting seat_id and ticket_id to ticket_seats
			 */
			ticketDao.setIntoTicketSeats(seatId, ticketId);
	}
}
