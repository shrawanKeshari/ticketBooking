package com.api.tickerBooking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.ticketBooking.beans.SeatInfo;
import com.api.ticketBooking.beans.TicketBooking;
import com.api.ticketBooking.dao.SeatInfoDao;
import com.api.ticketBooking.dao.TicketBookingDao;
import com.api.ticketBooking.pojo.TicketBookingScreenRequest;
import com.api.ticketBooking.pojo.TicketBookingScreenResponse;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/")
public class TicketBookingController {
	
	@Autowired
	TicketBookingDao ticketBookingDao;
	
	@Autowired
	SeatInfoDao seatInfoDao;

	private static final String SUCCESS_STATUS = "SUCCESS";
	private static final String SUCCES_CODE = "SUCCESS";
	private static final String FAILURE_STATUS = "FAILURE";
	private static final String FAILURE_CODE = "FAILURE";

	@RequestMapping(value = "/screens", method = RequestMethod.POST)
	public @ResponseBody TicketBookingScreenResponse screenDetail(
			@RequestBody TicketBookingScreenRequest ticketBookingScreenRequest) {
		TicketBookingScreenResponse ticketBookingScreenResponse = new TicketBookingScreenResponse();

		String screen_name = ticketBookingScreenRequest.getRequest().getName();
		List<com.api.ticketBooking.pojo.TicketBookingScreenRequest.Request.SeatInfo> seatInfoList = ticketBookingScreenRequest
				.getRequest().getSeatInfo();

		System.out.println(screen_name);

		int len = seatInfoList.size();
		
		List<String> rowList = new ArrayList<String>();

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
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			System.out.println(currentRow + " " + no_of_seats + " " + aislesJson);
		}
		
		String seatInfoJson = new Gson().toJson(rowList);
		
		try {
			
			TicketBooking ticketBooking = new TicketBooking();
			ticketBooking.setScreen_name(screen_name);
			ticketBooking.setSeatInfo(seatInfoJson);
			
			ticketBookingDao.insertData(ticketBooking);
			
			ticketBookingScreenResponse.setStatus(SUCCESS_STATUS);
			ticketBookingScreenResponse.setStatusCode(SUCCES_CODE);
		}catch (Exception e) {
			ticketBookingScreenResponse.setStatus(FAILURE_STATUS);
			ticketBookingScreenResponse.setStatusCode(FAILURE_CODE);
		}

		return ticketBookingScreenResponse;
	}
}
