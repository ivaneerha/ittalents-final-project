package com.example.kinoarena.model;

public class Movie {
	private int id;
	private int length;
	private String title;
	private int genreId;
	

	
	public Movie(int id, int length, String title, int genreId) {
		
		setId(id);
		setLength(length);
		setTitle(title);
		setGenreId(genreId);
	}

	private void setId(int id) {
		if(id > 0) {
			this.id = id;
		}
	}
	
	//TODO InnerClass for Genre ?
	
	private void setGenreId(int id) {
		if(id > 0) {
			this.genreId = id;
		}
	}
	
	private void setTitle(String title) {
		if(title != null) {
			this.title = title;
		}
	}
	
	private void setLength(int length) {
		if(length > 0) {
			this.length = length;
		}
	}

	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

	public String getTitle() {
		return title;
	}

	public int getGenreId() {
		return genreId;
	}
}
