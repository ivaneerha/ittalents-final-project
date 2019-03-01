package com.example.kinoarena.model;

import java.time.LocalDateTime;

public class Projection {
	private int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int movieId;
	
	public Projection(int id, int movieId, LocalDateTime startTime, LocalDateTime endTime) {
		setId(movieId);
		setMovieId(movieId);
	}
	
	private void setId(int id) {
		if(id > 0) {
			this.id = id;
		}
	}
	
	private void setMovieId(int id) {
		if(id > 0) {
			this.movieId = id;
		}
	}
	
	private void setStartTime() {
		
	}
	
	private void setEndTime() {
		
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public int getMovieId() {
		return movieId;
	}

	@Override
	public String toString() {
		return "Projection [startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
