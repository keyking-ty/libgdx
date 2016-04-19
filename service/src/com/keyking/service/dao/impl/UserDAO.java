package com.keyking.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.dao.row.UserRow;
import com.keyking.service.util.SystemLog;

public class UserDAO extends JdbcDaoSupport {
	
	private static String INSERT_SQL_STR = "insert into usertbl (username,password,email,telephone,firstName,lastName,address,age,post,father,task)values(?,?,?,?,?,?,?,?,?,?,?)";

	private static String UPDATE_SQL_STR = "update usertbl set username=?,password=?,email=?,telephone=?,firstName=?,lastName=?,address=?,age=?,post=?,father=?,task=? where id=?";

	private static String LOGIN_SQL_STR = "select * from usertbl where username=? and password=?";

	private static String LOAD_SQL_STR = "select * from usertbl where 1=1";

	private UserRow userRow = new UserRow();

	private static String CJECK_SQL_STR = "select * from usertbl where username=?";

	public List<UserEntity> load() {
		List<UserEntity> users = null;
		try {
			users = getJdbcTemplate().query(LOAD_SQL_STR,userRow);
		} catch (Exception e) {
			SystemLog.error("load users error : " + e.getMessage());
		}
		return users;
	}

	public UserEntity login(String username, String password) {
		UserEntity user = null;
		try {
			user = getJdbcTemplate().queryForObject(LOGIN_SQL_STR,userRow,username,password);
		} catch (Exception e) {
			SystemLog.error("not find user");
		}
		return user;
	}

	public long insert(final UserEntity user) {
		try {
			KeyHolder key = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(INSERT_SQL_STR, Statement.RETURN_GENERATED_KEYS);
					int cursor = 1;
					ps.setString(cursor++, user.getUsername());
					ps.setString(cursor++, user.getPassword());
					ps.setString(cursor++, user.getEmail());
					ps.setString(cursor++, user.getTelephone());
					ps.setString(cursor++, user.getFirstName());
					ps.setString(cursor++, user.getLastName());
					ps.setString(cursor++, user.getAddress());
					ps.setInt(cursor++, user.getAge());
					ps.setString(cursor++, user.getPost());
					ps.setInt(cursor++, user.getFather()== null ? 0 : user.getFather().getId());
					ps.setInt(cursor++, user.getTask());
					return ps;
				}
			}, key);
			return user.setId(key.getKey().longValue());
		} catch (Exception e) {
			SystemLog.error("insert user error : " + e.getMessage());
		}
		return 0;
	}

	public boolean save(final UserEntity user) {
		try {
			getJdbcTemplate().update(UPDATE_SQL_STR, user.getUsername(),
					user.getPassword(), user.getEmail(), user.getTelephone(),
					user.getFirstName(), user.getLastName(), user.getAddress(),
					user.getAge(), user.getPost(), user.getFather()== null ? 0 : user.getFather().getId(),
					user.getTask(), user.getId());
			return true;
		} catch (Exception e) {
			SystemLog.error("save user error : " + e.getMessage());
			return false;
		}
	}

	public boolean check(String username) {
		UserEntity user = null;
		try {
			user = getJdbcTemplate().queryForObject(CJECK_SQL_STR, userRow,username);
		} catch (Exception e) {
			//SystemLog.error("SQL“Ï≥£",e);
			return false;
		}
		return user != null;
	}

	public boolean Del(UserEntity... entitys) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from usertbl where id=");
			for (int i = 0; i < entitys.length; i++) {
				UserEntity group = entitys[i];
				sb.append(group.getId());
				sb.append((i == entitys.length - 1 ? ";" : " or id="));
			}
			getJdbcTemplate().update(sb.toString());
		} catch (Exception e) {
			SystemLog.error("del users error : " + e.getMessage());
			return false;
		}
		return true;
	}
}
 
 
 
 
