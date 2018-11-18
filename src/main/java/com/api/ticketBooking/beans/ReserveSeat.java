package com.api.ticketBooking.beans;

public class ReserveSeat {

	private int id;
	private String screen_name;
	private String available_seats;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getAvailable_seats() {
		return available_seats;
	}

	public void setAvailable_seats(String available_seats) {
		this.available_seats = available_seats;
	}

}
