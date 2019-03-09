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

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.helper.RegexPatterns;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@NonNull @Column(unique=true, nullable = false)
	private String username;
	@NonNull
	@JsonIgnore
	private String password;
	@NonNull
	private String firstName;
	private Long locationId;
	@NonNull
	private String lastName;
	@NonNull @Column(unique=true, nullable = false)
	private String email;
	private String gsm;
	private Byte isAdmin;
	private String favouriteMovie;
	private String favouriteActor;
	
}
