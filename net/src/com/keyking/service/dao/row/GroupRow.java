package com.keyking.service.dao.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.keyking.service.dao.entity.GroupEntity;

public class GroupRow implements RowMapper<GroupEntity> {

	@Override
	public GroupEntity mapRow(ResultSet rs, int i) throws SQLException {
		GroupEntity group = new GroupEntity();
		group.setId(rs.getInt("id"));
		group.setName(rs.getString("name"));
		group.setFid(rs.getInt("father"));
		group.setTask(rs.getInt("task"));
		return group;
	}
}
 
 
