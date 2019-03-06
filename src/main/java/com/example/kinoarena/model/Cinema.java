package com.example.kinoarena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.kinoarena.exceptions.InvalidInputDataException;

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
@Table(name="cinemas")
public class Cinema {
	@Id
	private long cinemaId;
	@Column
	private String name;
	@Column
	private String address;
	@Column
	private String gsm;
}
