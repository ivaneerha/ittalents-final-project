package com.example.kinoarena.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long projectionId;
	@Column
	private LocalDate startTime;
	@Column
	private LocalDate endTime;
	@Column
	private long movieId;
	
	
}
