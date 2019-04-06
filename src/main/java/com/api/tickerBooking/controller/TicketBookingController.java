package com.api.tickerBooking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.ticketBooking.beans.SeatInfo;
import com.api.ticketBooking.beans.TicketBooking;
import com.api.ticketBooking.dao.SeatInfoDao;
import com.api.ticketBooking.dao.TicketBookingDao;
import com.api.ticketBooking.pojo.ReserveRequest;
import com.api.ticketBooking.pojo.ReserveRequest.Request.Seat;
import com.api.ticketBooking.pojo.TicketBookingScreenRequest;
import com.api.ticketBooking.pojo.TicketBookingScreenResponse;
import com.api.ticketBooking.pojo.TicketBookingScreenResponse.Response;
import com.api.ticketBooking.pojo.TicketBookingScreenResponse.Response.AvailableSeatResponse;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/screens")
public class TicketBookingController {

	@Autowired
	TicketBookingDao ticketBookingDao;

	@Autowired
	SeatInfoDao seatInfoDao;

	private static final String SUCCESS_STATUS = "SUCCESS";
	private static final String SUCCES_CODE = "SUCCESS";
	private static final String FAILURE_STATUS = "FAILURE";
	private static final String FAILURE_CODE = "FAILURE";

	private static final Logger LOGGER = Logger.getLogger(TicketBookingController.class);

	@RequestMapping(value = "/screens", method = RequestMethod.POST)
	public @ResponseBody TicketBookingScreenResponse screenDetail(
			@RequestBody TicketBookingScreenRequest ticketBookingScreenRequest) {

		LOGGER.info("Into the screeDetails controller method");

		TicketBookingScreenResponse ticketBookingScreenResponse = new TicketBookingScreenResponse();

		String screen_name = ticketBookingScreenRequest.getRequest().getName();
		List<com.api.ticketBooking.pojo.TicketBookingScreenRequest.Request.SeatInfo> seatInfoList = ticketBookingScreenRequest
				.getRequest().getSeatInfo();

		LOGGER.info("screen name : " + screen_name);

		int len = seatInfoList.size();

		List<String> rowList = new ArrayList<String>();

		boolean secondCommit = true;

		JSONObject availableSeatJsonObject = new JSONObject();

		for (int i = 0; i < len; i++) {
			com.api.ticketBooking.pojo.TicketBookingScreenRequest.Request.SeatInfo seatInfo = seatInfoList.get(i);
			String currentRow = seatInfo.getRow();
			int no_of_seats = seatInfo.getNumberOfSeats();
			List<Integer> aislesSeats = seatInfo.getAisleSeats();
			rowList.add(currentRow);
			String aislesJson = new Gson().toJson(aislesSeats);

			try {

				SeatInfo seatInfo2 = new SeatInfo();
				seatInfo2.setRow(currentRow);
				seatInfo2.setAisleSeats(aislesJson);
				seatInfo2.setNo_of_seats(no_of_seats);

				seatInfoDao.insertData(seatInfo2);

				constructJsonObjectForSeats(availableSeatJsonObject, currentRow, no_of_seats);

			} catch (Exception e) {
				secondCommit = false;
				e.printStackTrace();
				LOGGER.error("error while inserting data into seatInfo table, exception is " + e.getMessage());
			}

			LOGGER.info("current row : " + currentRow + ", no of seats : " + no_of_seats + ", aisles seats json : "
					+ aislesJson);
		}

		LOGGER.info("secondCommit : " + secondCommit);

		if (secondCommit) {
			String seatInfoJson = new Gson().toJson(rowList);

			try {

				TicketBooking ticketBooking = new TicketBooking();
				ticketBooking.setScreen_name(screen_name);
				ticketBooking.setSeatInfo(seatInfoJson);
				ticketBooking.setAvailable_seats(availableSeatJsonObject.toString());

				ticketBookingDao.insertData(ticketBooking);

				ticketBookingScreenResponse.setStatus(SUCCESS_STATUS);
				ticketBookingScreenResponse.setStatusCode(SUCCES_CODE);
			} catch (Exception e) {
				ticketBookingScreenResponse.setStatus(FAILURE_STATUS);
				ticketBookingScreenResponse.setStatusCode(FAILURE_CODE);

				LOGGER.error("error while inserting data into testDb table, exception is " + e.getMessage());

			}
		} else {
			ticketBookingScreenResponse.setStatus(FAILURE_STATUS);
			ticketBookingScreenResponse.setStatusCode(FAILURE_CODE);

			LOGGER.error("error occured while inserting data into seatInfo table");
		}

		return ticketBookingScreenResponse;
	}

	private void constructJsonObjectForSeats(JSONObject availableSeatJsonObject, String currentRow, int no_of_seats) {
		boolean[] availSeatArray = new boolean[no_of_seats];
		Arrays.fill(availSeatArray, false);
		JSONArray jsonArray = new JSONArray(availSeatArray);
		availableSeatJsonObject.put(currentRow, jsonArray);
	}

	@RequestMapping(value = "/{screen_name}/reserve", method = RequestMethod.POST)
	public @ResponseBody TicketBookingScreenResponse reserve(@PathVariable String screen_name,
			@RequestBody ReserveRequest reserveRequest) {
		TicketBookingScreenResponse ticketBookingScreenResponse = new TicketBookingScreenResponse();

		List<Seat> seats = reserveRequest.getRequest().getSeats();

		int seatsLength = seats.size();

		try {

			TicketBooking ticketBooking = ticketBookingDao.getAvailableSeats(screen_name);

			JSONObject jsonObject = new JSONObject(ticketBooking.getAvailable_seats());

			LOGGER.info("Json Object before reserving the seat is :" + jsonObject.toString());

			for (int i = 0; i < seatsLength; i++) {
				Seat seat = seats.get(i);
				String currentRow = seat.getRow();
				List<Integer> seatsList = seat.getSeatList();
				int seatLength = seatsList.size();

				JSONArray jsonArray = jsonObject.getJSONArray(currentRow);

				for (int j = 0; j < seatLength; j++) {
					int currIndex = seatsList.get(j);
					jsonArray.put(currIndex, true);
				}

				LOGGER.info("Json Array is :" + jsonArray.toString());
			}

			LOGGER.info("Json Object after reserving the seat is :" + jsonObject.toString());

			ticketBooking.setScreen_name(screen_name);
			ticketBooking.setAvailable_seats(jsonObject.toString());

			LOGGER.info("screen name for reserve request: " + ticketBooking.getScreen_name());
			LOGGER.info("avialable sest for reserve request: " + ticketBooking.getAvailable_seats());

			ticketBookingDao.updateAvailableSeats(ticketBooking);

			ticketBookingScreenResponse.setStatus(SUCCESS_STATUS);
			ticketBookingScreenResponse.setStatusCode(SUCCES_CODE);
		} catch (Exception e) {
			ticketBookingScreenResponse.setStatus(FAILURE_STATUS);
			ticketBookingScreenResponse.setStatusCode(FAILURE_CODE);
			LOGGER.error(e.getMessage());
		}

		return ticketBookingScreenResponse;
	}

	@RequestMapping(value = "{screen_name}/seats", method = RequestMethod.GET)
	public @ResponseBody TicketBookingScreenResponse seatAvailability(@PathVariable String screen_name,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "numSeats", required = false) String numSeats,
			@RequestParam(value = "choice", required = false) String choice) {
		TicketBookingScreenResponse ticketBookingScreenResponse = null;

		TicketBooking ticketBooking = ticketBookingDao.getEntriesByScreen(screen_name);

		JSONArray seatInfoArray = new JSONArray(ticketBooking.getSeatInfo());
		int seatInfoLen = seatInfoArray.length();

		JSONObject jsonObject = new JSONObject(ticketBooking.getAvailable_seats());

		if (status != null) {
			ticketBookingScreenResponse = seatStatus(seatInfoArray, seatInfoLen, jsonObject);
		} else if (numSeats != null && choice != null) {
			ticketBookingScreenResponse = seatStatusAtGivenPosition(numSeats, choice);
		}

		return ticketBookingScreenResponse;
	}

	public TicketBookingScreenResponse seatStatus(JSONArray seatInfoArray, int seatInfoLen,
			JSONObject jsonObject) {
		TicketBookingScreenResponse ticketBookingScreenResponse = new TicketBookingScreenResponse();
		Response response = new Response();
		List<AvailableSeatResponse> availableSeatList = new ArrayList<AvailableSeatResponse>();

		try {
			for (int i = 0; i < seatInfoLen; i++) {
				JSONArray availSeatArray = jsonObject.getJSONArray(seatInfoArray.getString(i));
				int availSeatLen = availSeatArray.length();
				List<Integer> unresevedSeat = new ArrayList<Integer>();
				for (int j = 0; j < availSeatLen; j++) {
					if (!availSeatArray.getBoolean(j)) {
						unresevedSeat.add(j);
					}
				}
				AvailableSeatResponse availableSeatResponse = new AvailableSeatResponse();
				availableSeatResponse.setRow(seatInfoArray.getString(i));
				availableSeatResponse.setSeatsInfo(unresevedSeat);

				availableSeatList.add(availableSeatResponse);
			}

			response.setAvailableSeats(availableSeatList);

			ticketBookingScreenResponse.setStatus(SUCCESS_STATUS);
			ticketBookingScreenResponse.setStatusCode(SUCCES_CODE);
		} catch (Exception e) {
			ticketBookingScreenResponse.setStatus(FAILURE_STATUS);
			ticketBookingScreenResponse.setStatusCode(FAILURE_CODE);
			LOGGER.error(e.getMessage());
		}

		ticketBookingScreenResponse.setResponse(response);

		return ticketBookingScreenResponse;
	}

	public TicketBookingScreenResponse seatStatusAtGivenPosition(String numSeats, String choice) {
		TicketBookingScreenResponse ticketBookingScreenResponse = new TicketBookingScreenResponse();

		return ticketBookingScreenResponse;
	}
}
