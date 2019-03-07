package com.example.kinoarena.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Projection;

import lombok.Setter;

@Component
public class TicketDao {

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
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
	
	
}
