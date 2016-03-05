package com.keyking.service.dao.row;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.keyking.service.dao.entity.UserEntity;

public class UserRow implements RowMapper<UserEntity>{

	@Override
	public UserEntity mapRow(ResultSet rs, int i) throws SQLException {
		UserEntity user = new UserEntity();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setTelephone(rs.getString("telephone"));
		user.setFirstName(rs.getString("firstName"));
		user.setLastName(rs.getString("lastName"));
		user.setAddress(rs.getString("address"));
		user.setAge(rs.getInt("age"));
		user.setPost(rs.getString("post"));
		user.setFid(rs.getInt("father"));
		user.setTask(rs.getInt("task"));
		return user;
	}
}
 
 
 
