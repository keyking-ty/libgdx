package com.keyking.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.row.GroupRow;

public class GroupDAO extends JdbcDaoSupport {
	
	private static String INSERT_SQL_STR = "insert into grouptbl (name,father,task)values(?,?,?)";

	private static String UPDATE_SQL_STR = "update grouptbl set name=?,father=?,task=? where id=?";

	private static String LOAD_SQL_STR = "select * from grouptbl where 1=1";

	GroupRow groupRow = new GroupRow();

	public List<GroupEntity> load() {
		List<GroupEntity> groups = null;
		try {
			groups = getJdbcTemplate().query(LOAD_SQL_STR, groupRow);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return groups;
	}

	public boolean insert(final GroupEntity group) {
		try {
			KeyHolder key = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(
							INSERT_SQL_STR, Statement.RETURN_GENERATED_KEYS);
					int cursor = 1;
					ps.setString(cursor++, group.getName());
					ps.setInt(cursor++, group.getFather() == null ? 0 : group
							.getFather().getId());
					ps.setInt(cursor++, group.getTask());
					return ps;
				}
			}, key);
			group.setId(key.getKey().intValue());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public void save(final GroupEntity group) {
		try {
			getJdbcTemplate().update(UPDATE_SQL_STR, group.getName(),
					group.getFather() == null ? 0 : group.getFather().getId(),
					group.getTask(), group.getId());
		} catch (DataAccessException e) {

		}
	}

	public boolean Del(final GroupEntity... groups) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from grouptbl where id=");
			for (int i = 0; i < groups.length; i++) {
				GroupEntity group = groups[i];
				sb.append(group.getId());
				sb.append((i == groups.length - 1 ? ";" : " or id="));
			}
			getJdbcTemplate().update(sb.toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
 
 
