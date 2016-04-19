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
import com.keyking.service.util.SystemLog;

public class TelInfoDAO extends JdbcDaoSupport {
	
	private static String INSERT_SQL_STR = "insert into teltbl (name,telephone,userId)values(?,?,?)";
	
	private static String UPDATE_SQL_STR = "update teltbl set userId=?,downTime=? where telephone=?";
	
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
			SystemLog.error("insert tel error : " + e.getMessage());
			return false;
		}
	}
	
	public boolean save(TelInfoEntity tel) {
		try {
			getJdbcTemplate().update(UPDATE_SQL_STR,tel.getUserId(),tel.getDownTime(),tel.getTelephone());
			return true;
		} catch (Exception e) {
			SystemLog.error("save tel error : " + e.getMessage());
		}
		return false;
	}
	
	public List<TelInfoEntity> load(){
		List<TelInfoEntity> tels = null;
		try {
			tels = getJdbcTemplate().query(SELECT_SQL_STR1,telInfoRow);
		} catch (Exception e) {
			SystemLog.error("load tels error : " + e.getMessage());
		}
		return tels;
	}
	
	public List<TelInfoEntity> load(int dwonType, String start,String end,boolean flag){
		List<TelInfoEntity> tels = null;
		String downSTr = dwonType == 1 ? ">0" : "=0";
		String sql = "select * from teltbl where userId " + downSTr + " and downTime>='"  + start + "' and downTime <'" + end + "'";
		try {
			tels = getJdbcTemplate().query(sql,telInfoRow);
		} catch (Exception e) {
			//SystemLog.error("SQLÒì³£",e);
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
 
 
 
 
