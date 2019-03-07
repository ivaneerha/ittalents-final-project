package com.example.kinoarena.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectionDto {
	
	
	private String startTime;
	private String endTime;
	private Long movieId;
}
