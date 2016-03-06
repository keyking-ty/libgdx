package com.keyking.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.row.TelInfoRow;

public class TelInfoDAO extends JdbcDaoSupport {
	
	private static String INSERT_SQL_STR = "insert into teltbl (name,telephone,userId)values(?,?,?)";
	
	private static String UPDATE_SQL_STR = "update teltbl set name=?,userId=?,downTime=? where telephone=?";
	
	private static String SELECT_SQL_STR1 = "select * from teltbl where userId=0";
	
	TelInfoRow telInfoRow = new TelInfoRow();
	
	public boolean insert(final TelInfoEntity tel) {
		try {
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)throws SQLException {
					PreparedStatement ps = conn.prepareStatement(INSERT_SQL_STR, Statement.NO_GENERATED_KEYS);
					int cursor = 1;
					ps.setString(cursor++,tel.getName().trim());
					ps.setString(cursor++,tel.getTelephone().trim());
					ps.setLong(cursor++,tel.getUserId());
					return ps;
				}
			});
			return true;
		} catch (Exception e) {
			//SystemLog.error("SQL异常",e);
			return false;
		}
	}
	
	public boolean save(TelInfoEntity tel) {
		try {
			getJdbcTemplate().update(UPDATE_SQL_STR, tel.getName(),tel.getUserId(),tel.getDownTime(),tel.getTelephone());
			return true;
		} catch (Exception e) {
			//SystemLog.error("SQL异常",e);
		}
		return false;
	}
	
	public List<TelInfoEntity> load(){
		List<TelInfoEntity> tels = null;
		try {
			tels = getJdbcTemplate().query(SELECT_SQL_STR1,telInfoRow);
		} catch (Exception e) {
			//SystemLog.error("SQL异常",e);
		}
		return tels;
	}
	
	public List<TelInfoEntity> load(String start,String end,boolean flag){
		List<TelInfoEntity> tels = null;
		String sql = "select * from teltbl where userId != 0 and downTime>=convert(varchar(10),'"  + start + "',120) and downTime <=convert(varchar(10),'" + end + "',120)";
		try {
			tels = getJdbcTemplate().query(sql,telInfoRow);
		} catch (Exception e) {
			//SystemLog.error("SQL异常",e);
		}
		if (flag){
			for (TelInfoEntity tel : tels){
				sql = "delete from teltbl where telephone='"  + tel.getTelephone() + "'";
				getJdbcTemplate().update(sql);
			}
		}
		return tels;
	}
}
 
 
 
 
