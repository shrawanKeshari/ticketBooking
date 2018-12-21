package com.api.ticketBooking.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.api.ticketBooking.beans.TestDb;

public class TestDbDao {
	
	JdbcTemplate template;
	
	private static final Logger LOGGER = Logger.getLogger(TestDbDao.class);
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int insertData(TestDb testDb) {
		String sql = "insert into testDb (`screen_name`,`seatInfo`,`available_seats`) values (?,?,?)";
		
		return template.update(sql, testDb.getScreen_name(), testDb.getSeatInfo(), testDb.getAvailable_seats());
	}
	
	public int updateSeatInfo(TestDb testDb) {
		String sql = "update testDb set seatInfo=? where screen_name=?";
		
		return template.update(sql, testDb.getSeatInfo(), testDb.getScreen_name());
	}
	
	public int updateAvailableSeats(TestDb testDb) {
		String sql = "update testDb set available_seats=? where screen_name=?";
		
		return template.update(sql, testDb.getAvailable_seats(), testDb.getScreen_name());
	}
	
	public TestDb getAvailableSeats(String screen_name) {
		String sql = "select * from testDb where screen_name=?";

		LOGGER.info("sql query is : " + sql + " , " + screen_name);

		return template.queryForObject(sql, new Object[] { screen_name },
				new BeanPropertyRowMapper<TestDb>(TestDb.class));
	}
	
	public TestDb getEntriesByScreen(String screen_name) {
		String sql = "select * from testDb where screen_name=?";
		
		LOGGER.info("sql query is : " + sql);
		
		return template.queryForObject(sql, new Object[] { screen_name },
				new BeanPropertyRowMapper<TestDb>(TestDb.class));
	}
}
