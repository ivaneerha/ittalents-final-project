package com.example.kinoarena.model;

import com.example.kinoarena.exceptions.InvalidInputDataException;

public class Cinema {
	private static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number!";
	private static final String INVALID_ADDRESS_MESSAGE = "Invalid address!";
	private static final String PHONE_NUMBER_START_PREFIX = "08";
	private static final int PHONE_NUMBER_LENGTH = 10;
	
	private int id;
	private String name;
	private String gsm;
	private String address;
	
	public Cinema(int id, String name, String gsm) throws InvalidInputDataException {
		setId(id);
		setAddress(address);
		setGsm(gsm);
		setName(name);
	}
	
	private void setId(int id) {
		if(id > 0) {
			this.id = id;
		}
	}
	
	private void setGsm(String gsm) throws InvalidInputDataException {
		if(gsm != null && gsm.trim().length() == PHONE_NUMBER_LENGTH && gsm.startsWith(PHONE_NUMBER_START_PREFIX)) {
			this.address = gsm;
		} else {
			throw new InvalidInputDataException(INVALID_PHONE_NUMBER_MESSAGE);
		}
	}
	
	private void setAddress(String address) throws InvalidInputDataException {
		if(address != null && address.trim().length() > 0) {
			this.address = address;
		} else {
			throw new InvalidInputDataException(INVALID_ADDRESS_MESSAGE);
		}
	}
	
	private void setName(String name) throws InvalidInputDataException {
		if(this.name != null) {
			this.name = name;
		} else {
			throw new InvalidInputDataException("Invalid username pattern. Please try again.");
		}
		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGsm() {
		return gsm;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Cinema [name=" + name + ", gsm=" + gsm + ", address=" + address + "]";
	}
}
