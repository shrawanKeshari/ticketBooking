package com.api.ticketBooking.beans;

public class SeatInfo {

	private int id;
	private String row;
	private String aisleSeats;
	private int no_of_seats;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getAisleSeats() {
		return aisleSeats;
	}

	public void setAisleSeats(String aisleSeats) {
		this.aisleSeats = aisleSeats;
	}

	public int getNo_of_seats() {
		return no_of_seats;
	}

	public void setNo_of_seats(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

}
