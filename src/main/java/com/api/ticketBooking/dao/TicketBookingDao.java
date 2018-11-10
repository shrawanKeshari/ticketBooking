package com.api.ticketBooking.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.api.ticketBooking.beans.TicketBooking;

public class TicketBookingDao {

	JdbcTemplate template;
	

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int insertData(TicketBooking ticketBooking) {
		String sql = "insert into ticketBooking (screen_name,seatInfo) values (?,?)";

		return template.update(sql, ticketBooking.getScreen_name(), ticketBooking.getSeatInfo());
	}

	public int updateData(TicketBooking ticketBooking) {
		String sql = "update ticketBooking set seatInfo=? where screen_name=?";
		
		return template.update(sql, ticketBooking.getSeatInfo(), ticketBooking.getScreen_name());
	}

	public TicketBooking getEntriesByScreen(String screen_name) {
		String sql = "select * from ticketBooking where screen_name=?";
		return template.queryForObject(sql, new Object[] { screen_name },
				new BeanPropertyRowMapper<TicketBooking>(TicketBooking.class));
	}
}
