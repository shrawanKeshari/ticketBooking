package com.api.ticketBooking.pojo;

import java.util.List;

public class TicketBookingScreenRequest {

	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public TicketBookingScreenRequest withRequest(Request request) {
		this.request = request;
		return this;
	}

	public static class Request {

		private String name;
		private SeatInfo seatInfo = null;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Request withName(String name) {
			this.name = name;
			return this;
		}

		public SeatInfo getSeatInfo() {
			return seatInfo;
		}

		public void setSeatInfo(SeatInfo seatInfo) {
			this.seatInfo = seatInfo;
		}

		public Request withSeatInfo(SeatInfo seatInfo) {
			this.seatInfo = seatInfo;
			return this;
		}

		public static class SeatInfo {

			private String row;
			private Integer numberOfSeats;
			private List<Integer> aisleSeats = null;

			public String getRow() {
				return row;
			}

			public void setRow(String row) {
				this.row = row;
			}

			public SeatInfo withRow(String row) {
				this.row = row;
				return this;
			}

			public Integer getNumberOfSeats() {
				return numberOfSeats;
			}

			public void setNumberOfSeats(Integer numberOfSeats) {
				this.numberOfSeats = numberOfSeats;
			}

			public SeatInfo withNumberOfSeats(Integer numberOfSeats) {
				this.numberOfSeats = numberOfSeats;
				return this;
			}

			public List<Integer> getAisleSeats() {
				return aisleSeats;
			}

			public void setAisleSeats(List<Integer> aisleSeats) {
				this.aisleSeats = aisleSeats;
			}

			public SeatInfo withAisleSeats(List<Integer> aisleSeats) {
				this.aisleSeats = aisleSeats;
				return this;
			}

		}

	}

}
