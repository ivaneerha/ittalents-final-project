package com.example.kinoarena.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ErrorMessage {

	private String message;
	private int status;
	private LocalDateTime time;

}
