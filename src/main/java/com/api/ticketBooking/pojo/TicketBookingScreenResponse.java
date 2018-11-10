package com.api.ticketBooking.pojo;

import java.io.Serializable;

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

	}

}
