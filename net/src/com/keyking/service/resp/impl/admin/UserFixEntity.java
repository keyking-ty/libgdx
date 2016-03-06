package com.keyking.service.resp.impl.admin;

import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class UserFixEntity extends RespEntity {
	
	UserEntity user;
	
	public UserFixEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		user._serialize(buffer);
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
 
 
