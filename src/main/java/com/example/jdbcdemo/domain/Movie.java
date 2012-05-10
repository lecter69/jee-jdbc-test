package com.example.jdbcdemo.domain;

public class Movie {

	private long id;
	private String name;
	private int year;
	private String genre;
	private int time;
	
	public Movie() {
	}
	
	public Movie(String name, int year, String genre, int time) {
		super();
		this.name = name;
		this.year = year;
		this.genre = genre;
		this.time = time;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year = year;		
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getTime(){
		return time;
	}
	
	public void setTime(int time){
		this.time = time;		
	}
	
}
