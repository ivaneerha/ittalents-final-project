package com.example.kinoarena.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity
@Table(name="projections")
public class Projection {
	
	private int projectionId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int movieId;
	
	
}
