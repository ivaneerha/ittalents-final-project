package com.example.kinoarena.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
 

public class Seat {
	
	private int seat_id;
	private int row;
	private int column;
	private boolean isTaken;
	
	
}
