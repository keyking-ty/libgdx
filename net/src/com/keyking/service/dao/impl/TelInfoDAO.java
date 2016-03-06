package com.keyking.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.dao.row.TelInfoRow;

public class TelInfoDAO extends JdbcDaoSupport {
	
	private static String INSERT_SQL_STR = "insert into teltbl (name,telephone,userId,flag)values(?,?,?,?)";
	
	private static String UPDATE_SQL_STR = "update teltbl set name=?,userId=?,flag=? where telephone=?";
	
	private static String SELECT_SQL_STR1 = "select * from teltbl where userId=?";
	
	private static String SELECT_SQL_STR2 = "select * from teltbl where userId=0 limit ?";
	
	TelInfoRow telInfoRow = new TelInfoRow();
	
	public boolean insert(final TelInfoEntity tel) {
		try {
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)throws SQLException {
					PreparedStatement ps = conn.prepareStatement(INSERT_SQL_STR, Statement.NO_GENERATED_KEYS);
					int cursor = 1;
					ps.setString(cursor++,tel.getName());
					ps.setString(cursor++,tel.getTelephone());
					ps.setLong(cursor++,tel.getUserId());
					ps.setInt(cursor++,tel.getFlag());
					return ps;
				}
			});
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public void save(final TelInfoEntity tel) {
		try {
			getJdbcTemplate().update(UPDATE_SQL_STR, tel.getName(),tel.getUserId(),tel.getFlag(),tel.getTelephone());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public List<TelInfoEntity> loadLogin(UserEntity user){
		synchronized (user) {
			List<TelInfoEntity> tels = null;
			try {
				tels = getJdbcTemplate().query(SELECT_SQL_STR1,telInfoRow,user.getId());
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			return tels;
		}
	}
	
	public List<TelInfoEntity> loadDown(Object locker ,int num){
		synchronized (locker) {
			List<TelInfoEntity> tels = null;
			try {
				tels = getJdbcTemplate().query(SELECT_SQL_STR2,telInfoRow,num);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			return tels;
		}
	}
}
 
 
