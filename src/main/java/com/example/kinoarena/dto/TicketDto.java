package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
	private Long cinemaId;
	private Long projectionId;
	private int[] seats;
}
