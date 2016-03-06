package com.keyking.service.dao.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.keyking.service.dao.entity.TelInfoEntity;

public class TelInfoRow implements RowMapper<TelInfoEntity> {

	@Override
	public TelInfoEntity mapRow(ResultSet rs, int arg1) throws SQLException {
		TelInfoEntity tel = new TelInfoEntity();
		tel.setName(rs.getString("name"));
		tel.setTelephone(rs.getString("telephone"));
		tel.setUserId(rs.getLong("userId"));
		return tel;
	}
}
 
 
 
 
