package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Projection;
import com.example.kinoarena.model.Ticket;
import com.example.kinoarena.model.User;

import lombok.Setter;

@Component
public class TicketDao {

	private static final int TICKET_PRICE = 12;

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	private static final String LOGGED_USER = "LoggedUser";
	private static final String SELECT_HALL_ID = "Select hall_id from halls where cinema_id = ? limit 1;";
	private static final String HALL_ID = "hall_id";
	private static final String INSERT_QUERY = "INSERT into ticket_seats values (?,?);";
	
//	Connection con = jdbcTemplate.getDataSource().getConnection();
//	List<Projection> projections = new ArrayList<>();
//	try(PreparedStatement ps = con.prepareStatement("select * from projections where projection_id in (SELECT projection_id from cinemа_projections where cinema_id = ?);");){
//		ps.setLong(1, id);
//		try(ResultSet result = ps.executeQuery()){
//			while(result.next()) {
//				String startDateTime = result.getString("start_time");
//				String endDateTime = result.getString("start_time");
//				Projection projection = new Projection();
//				projection.setStartTime(startDateTime.toString());
//				projection.setEndTime(endDateTime.toString());
//				projection.setMovieId(result.getLong("movie_id"));
//				projection.setProjectionId(result.getLong("projection_id"));
//				projections.add(projection);
//			}
//		}
//	}
//	return projections;
	
	public String getStartTime(Long projectId) throws SQLException, KinoArenaException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		String startDateTime = "";
		try(PreparedStatement ps = con.prepareStatement("select start_time from projections where projection_id = ?")){
			ps.setLong(1, projectId);
			try(ResultSet result = ps.executeQuery()){
				if(result.next()) {
					 startDateTime = result.getString("start_time");
				}
			}
		}
		catch(Exception e) {
			throw new KinoArenaException("Could not find datetime in projection!");
		}
		return startDateTime;
	}
	
	
	public Ticket generateTicket(TicketDto ticketDto, HttpSession session) throws SQLException, KinoArenaException {
		Ticket ticket = new Ticket();
		User logged = (User) session.getAttribute(LOGGED_USER);
		ticket.setProjectionId(ticketDto.getProjectionId());
		ticket.setUserId(logged.getUserId());
		ticket.setCinemaId(ticketDto.getCinemaId());
		ticket.setStartTime(getStartTime(ticketDto.getProjectionId()));
		ticket.setPrice(TICKET_PRICE);
		
		return ticket;
	}
	
	public Long getHallIdForThisCinema(TicketDto ticketDto) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		Long hallId = null;
		try (PreparedStatement ps = con.prepareStatement(SELECT_HALL_ID)) {
			ps.setLong(1, ticketDto.getCinemaId());
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				hallId = result.getLong(HALL_ID);
			}
		}
		return hallId;
	}
	
	public void setIntoTicketSeats(Long seatId, Long ticketId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {
			ps.setLong(1, seatId);
			ps.setLong(2, ticketId);
			int result = ps.executeUpdate();
		}
	}
	
	
//	public void addTicket(TicketDto ticketDto, HttpSession session, HttpServletRequest request) {
//		
//		User logged = (User) session.getAttribute("LoggedUser");
//		// CHECK HERE IF SEAT IF TAKEN BEFORE 'buying' ticket
//		Ticket ticket = new Ticket();
//		ticket.setCinemaId(ticketDto.getCinemaId());
//		ticket.setProjectionId(ticketDto.getProjectionId());
//		ticket.setUserId(logged.getUserId());
//		ticket.setStartTime(ticketDao.getStartTime(ticketDto.getProjectionId()));
//		ticketRepository.save(ticket);
//	}
//	
	
}
