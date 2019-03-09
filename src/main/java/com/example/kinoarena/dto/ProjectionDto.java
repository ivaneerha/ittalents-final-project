package com.example.kinoarena.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectionDto {
	private String startTime;
	private String endTime;
	private Long movieId;
}
