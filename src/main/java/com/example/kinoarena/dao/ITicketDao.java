package com.example.kinoarena.dao;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import com.example.kinoarena.dto.TicketDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Ticket;

public interface ITicketDao {

	String getStartTime(Long projectId) throws SQLException, KinoArenaException;

	Ticket generateTicket(TicketDto ticketDto, HttpSession session) throws SQLException, KinoArenaException;

	Long getHallIdForThisCinema(TicketDto ticketDto) throws SQLException;

	void setIntoTicketSeats(Long seatId, Long ticketId) throws SQLException;

}
