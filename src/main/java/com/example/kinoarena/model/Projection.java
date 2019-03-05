package com.example.kinoarena.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	@Id
	private int projectionId;
	@Column
	private LocalDateTime startTime;
	@Column
	private LocalDateTime endTime;
	@Column
	private int movieId;
	
	
}
