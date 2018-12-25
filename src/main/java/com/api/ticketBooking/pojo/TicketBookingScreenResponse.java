package com.api.ticketBooking.pojo;

import java.io.Serializable;
import java.util.List;

public class TicketBookingScreenResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7574267704293452702L;

	private String status;
	private String statusCode;
	private Response response;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public static class Response {

		private List<SeatResponse> seats = null;

		public List<SeatResponse> getSeats() {
			return seats;
		}

		public void setSeats(List<SeatResponse> seats) {
			this.seats = seats;
		}

		public static class SeatResponse {

			private String row;
			private List<Integer> seatsInfo = null;

			public String getRow() {
				return row;
			}

			public void setRow(String row) {
				this.row = row;
			}

			public List<Integer> getSeatsInfo() {
				return seatsInfo;
			}

			public void setSeatsInfo(List<Integer> seatsInfo) {
				this.seatsInfo = seatsInfo;
			}
		}

	}

}
