package com.keyking.service.servlet.resp.impl.admin;

import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class UserAddEntity extends RespEntity {
	
	UserEntity user;
	
	public UserAddEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		user._serialize(buffer);
	}

	@Override
	public void _serialize_fail(DataBuffer buffer) {
		super._serialize_fail(buffer);
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
 
 
 
 
