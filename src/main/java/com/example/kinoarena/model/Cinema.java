package com.example.kinoarena.model;

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

public class Cinema {

	private int id;
	private String name;
	private String gsm;
	private String address;

}
