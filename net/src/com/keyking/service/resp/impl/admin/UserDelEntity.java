package com.keyking.service.resp.impl.admin;

import com.keyking.service.dao.DataManager;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class UserDelEntity extends RespEntity {

	public UserDelEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		DataManager.getInstance().sendAll(buffer);
	}
}
 
 
