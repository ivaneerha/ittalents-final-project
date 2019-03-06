package com.example.kinoarena.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.helper.RegexPatterns;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	@NonNull @Column(unique=true, nullable = false)
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private long location_id;
	@NotNull
	private String email;
	private String gsm;
	private byte isAdmin;
	private String favouriteMovie;
	private String favouriteActor;
	
}
