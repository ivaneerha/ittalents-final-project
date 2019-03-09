package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter

public class ProjectionDateTimeDto {
	private Long projectionId;
	private String startTime;
	private String endTime;
}
