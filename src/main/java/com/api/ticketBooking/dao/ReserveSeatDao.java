package com.api.ticketBooking.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.api.ticketBooking.beans.ReserveSeat;

public class ReserveSeatDao {

	JdbcTemplate template;

	private static final Logger LOGGER = Logger.getLogger(ReserveSeatDao.class);

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int insertData(ReserveSeat reserveSeat) {
		String sql = "insert into reserveSeat (`screen_name`,`available_seats`) values (?,?)";

		LOGGER.info("sql query is : " + sql + " , " + reserveSeat.getScreen_name() + " "
				+ reserveSeat.getAvailable_seats());

		return template.update(sql, reserveSeat.getScreen_name(), reserveSeat.getAvailable_seats());
	}

	public int updateData(ReserveSeat reserveSeat) {
		String sql = "update reserveSeat set available_seats=? where screen_name=?";

		LOGGER.info("sql query is : " + sql + " , " + reserveSeat.getAvailable_seats() + " "
				+ reserveSeat.getScreen_name());
		
		return template.update(sql, reserveSeat.getAvailable_seats(), reserveSeat.getScreen_name());
	}

	public ReserveSeat getAvailableSeats(String screen_name) {
		String sql = "select * from reserveSeat where screen_name=?";

		LOGGER.info("sql query is : " + sql + " , " + screen_name);

		return template.queryForObject(sql, new Object[] { screen_name },
				new BeanPropertyRowMapper<ReserveSeat>(ReserveSeat.class));
	}
}
