package com.example.kinoarena.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Projection {
	
	private int projectionId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int movieId;
	
	
}
