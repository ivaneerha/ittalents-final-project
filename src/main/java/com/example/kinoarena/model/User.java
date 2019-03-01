package com.example.kinoarena.model;

import java.util.ArrayList;
import java.util.List;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.helper.RegexPatterns;

public class User {
	private static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number!";
	private static final String INVALID_ADDRESS_MESSAGE = "Invalid address!";
	private static final String PHONE_NUMBER_START_PREFIX = "08";
	private static final int PHONE_NUMBER_LENGTH = 10;

	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String gsm;
	private String address;
	private boolean isAdmin;
//	private Cinema cinema;
	private String password;
	private String favouriteMovie;
	private String favouriteActor;
	private List<String> favourites;

//	public User(String username,String password,String firstName,String lastName,String email, String address, String gsm) throws InvalidInputDataException {
//		setUsername(username);
//		setPassword(password);
//		setFirstName(firstName);
//		setLastName(lastName);
//		setEmail(email);
//		setGsm(gsm);
//		setAddress(address);
//		isAdmin = false;
//	}

	public User(int id, String username, String password, String firstName, String lastName, String address,
			String email, String gsm, boolean isAdmin) throws InvalidInputDataException {
//		this(username, password, firstName, lastName, email, address, gsm);
		setUsername(username);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setGsm(gsm);
		setAddress(address);
		isAdmin = false;
		this.isAdmin = isAdmin;
		this.favourites = new ArrayList<String>();
	}

	private void setId(int id) {
		if (id > 0) {
			this.id = id;
		}
	}

	private void setAddress(String address) throws InvalidInputDataException {
		if (address != null && address.trim().length() > 0) {
			this.address = address;
		} else {
			throw new InvalidInputDataException(INVALID_ADDRESS_MESSAGE);
		}
	}

	private void setGsm(String gsm) throws InvalidInputDataException {
		if (gsm != null && gsm.trim().length() == PHONE_NUMBER_LENGTH && gsm.startsWith(PHONE_NUMBER_START_PREFIX)) {
			this.address = gsm;
		} else {
			throw new InvalidInputDataException(INVALID_PHONE_NUMBER_MESSAGE);
		}
	}

	private void setEmail(String email) throws InvalidInputDataException {
		boolean isValid = RegexPatterns.validateEmail(email);
		if (!isValid) {
			throw new InvalidInputDataException("Invalid email pattern. Please try again.");
		}
		this.email = email;
	}

	private void setLastName(String lastName) {
		if (lastName != null) {
			this.lastName = lastName;
		}
	}

	private void setFirstName(String firstName) {
		if (firstName != null) {
			this.firstName = firstName;
		}
	}

	private void setPassword(String password) throws InvalidInputDataException {
		boolean isValid = RegexPatterns.validatePassword(password);
		if (!isValid) {
			throw new InvalidInputDataException("Invalid password pattern. Please try again.");
		}
		this.password = password;
	}

	private void setUsername(String username) throws InvalidInputDataException {
		if (this.username != null) {
			this.username = username;
		} else {
			throw new InvalidInputDataException("Invalid username pattern. Please try again.");
		}

	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getGsm() {
		return gsm;
	}

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
	}

	public String getFavouriteMovie() {
		if (this.favouriteMovie != null) {
			return this.favouriteMovie;
		} else {
			return "You don't have favourite movie added!";
		}
	}

	public void setFavouriteMovie(String favouriteMovie) throws InvalidInputDataException {
		if (favouriteMovie != null) {
			this.favouriteMovie = favouriteMovie;
		} else {
			throw new InvalidInputDataException("Invalid input!");
		}
	}

	public String getFavouriteActor() {
		if (favouriteMovie != null) {
			return this.favouriteActor;
		} else {
			return "You don't have favourite actor added!";
		}
	}

	public void setFavouriteActor(String favouriteActor) throws InvalidInputDataException {
		if (favouriteMovie != null) {
			this.favouriteActor = favouriteActor;
		} else {
			throw new InvalidInputDataException("Invalid input!");
		}
	}
	
	public void addFavourite(String fav) throws InvalidInputDataException {
		if(fav != null) {
			this.favourites.add(fav);
		} else {
			throw new InvalidInputDataException("Invalid input!");
		}
	}
	
	public List getFavourites() {
		return this.favourites;
	}
}
