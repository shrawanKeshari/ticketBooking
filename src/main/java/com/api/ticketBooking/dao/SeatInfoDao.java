package com.api.ticketBooking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.api.ticketBooking.beans.SeatInfo;

public class SeatInfoDao {

	JdbcTemplate template;
	
	private static final Logger LOGGER = Logger.getLogger(SeatInfoDao.class);

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int insertData(SeatInfo seatInfo) {
		
		String sql = "insert into seatInfo values (null,?,?,?)";
		
		LOGGER.info("sql query is : " + sql);

		return template.update(sql, seatInfo.getRow(), seatInfo.getAisleSeats(), seatInfo.getNo_of_seats());
	}

	public int updateData(SeatInfo seatInfo) {
		String sql = "update seatInfo set aisleSeats=?,no_of_seats=? where row=?";

		LOGGER.info("sql query is : " + sql);
		
		return template.update(sql, seatInfo.getAisleSeats(), seatInfo.getNo_of_seats(), seatInfo.getRow());
	}

	public List<SeatInfo> getEntriesByRows(String row) {
		String sql = "select * from seatInfo where row in ("+ row +")";
		
		LOGGER.info("sql query is : " + sql);
		
		return template.query(sql, new RowMapper<SeatInfo>() {

			public SeatInfo mapRow(ResultSet rs, int row) throws SQLException {
				SeatInfo seatInfo = new SeatInfo();
				seatInfo.setId(rs.getInt(1));
				seatInfo.setRow(rs.getString(2));
				seatInfo.setAisleSeats(rs.getString(3));
				seatInfo.setNo_of_seats(rs.getInt(4));
				return seatInfo;
			}

		});
	}

}
