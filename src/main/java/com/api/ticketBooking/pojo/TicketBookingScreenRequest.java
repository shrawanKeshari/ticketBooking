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

	public static class Request {

		private String name;
		private List<SeatInfo> seatInfo = null;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<SeatInfo> getSeatInfo() {
			return seatInfo;
		}

		public void setSeatInfo(List<SeatInfo> seatInfo) {
			this.seatInfo = seatInfo;
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

			public Integer getNumberOfSeats() {
				return numberOfSeats;
			}

			public void setNumberOfSeats(Integer numberOfSeats) {
				this.numberOfSeats = numberOfSeats;
			}

			public List<Integer> getAisleSeats() {
				return aisleSeats;
			}

			public void setAisleSeats(List<Integer> aisleSeats) {
				this.aisleSeats = aisleSeats;
			}

		}

	}

}
