package com.api.ticketBooking.pojo;

import java.util.List;

public class ReserveRequest {

	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public static class Request {

		private List<Seat> seats = null;

		public List<Seat> getSeats() {
			return seats;
		}

		public void setSeats(List<Seat> seats) {
			this.seats = seats;
		}

		public static class Seat {

			private String row;
			private List<Integer> seatList = null;

			public String getRow() {
				return row;
			}

			public void setRow(String row) {
				this.row = row;
			}

			public List<Integer> getSeatList() {
				return seatList;
			}

			public void setSeatList(List<Integer> seatList) {
				this.seatList = seatList;
			}

		}

	}

}
