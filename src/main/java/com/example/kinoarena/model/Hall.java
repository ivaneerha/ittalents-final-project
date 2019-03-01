package com.example.kinoarena.model;

public class Hall {
	private String[] hallTypes = {"IMAX", "2D", "3D"};
	private final static int IMAX_SEATS = 350;
	private final static int TWO_D_Seats = 100;
	private final static int THREE_D_SEATS = 200;
	
	private int id;
	private String type;
	private int seats;
	private Cinema cinema;
	
	public Hall(int id, String type) {
		setId(id);
		setSeats();
		setType(type);
	}

	private void setId(int id) {
		if(id > 0) {
			this.id = id;
		}
	}
	
	private void setType(String type) {
		if(type != null) {
			type = type.toLowerCase();
			switch (type) {
			case "imax":
				this.type = this.hallTypes[0];
				break;
			case "3d":
				this.type = this.hallTypes[2];
				break;
			default:
				this.type = this.hallTypes[1];
				break;
			}
		}
	}
	
	private void setSeats() {
			switch (this.type) {
			case "IMAX":
				this.seats = IMAX_SEATS;
				break;
			case "2D":
				this.seats = TWO_D_Seats;
				break;
			case "3D":
				this.seats = THREE_D_SEATS;
			break;
			default:
				System.out.println("Wrong HallType!");
				break;
			}
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public int getSeats() {
		return seats;
	}

	@Override
	public String toString() {
		return "Hall [type=" + type + ", seats=" + seats + "]";
	}

	public int getCinemaId() {
		return cinema.getId();
	}
}
