package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
	private Long projectionId;
	private Long cinemaId;
	private Long seat;
	private Long line;
}
