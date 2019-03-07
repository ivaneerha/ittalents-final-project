package com.example.kinoarena.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectionDto {
	
	
	private LocalDate startTime;
	private LocalDate endTime;
	private long movieId;
}
