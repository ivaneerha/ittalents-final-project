package com.example.kinoarena.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectionDto {
	private String startTime;
	private String endTime;
	private long movieId;
}
