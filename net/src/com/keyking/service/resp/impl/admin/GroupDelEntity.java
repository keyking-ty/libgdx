package com.keyking.service.resp.impl.admin;

import com.keyking.service.dao.DataManager;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class GroupDelEntity extends RespEntity {

	public GroupDelEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		DataManager.getInstance().sendAll(buffer);
	}
	
	@Override
	public void _serialize_fail(DataBuffer buffer) {
		super._serialize_fail(buffer);
	}
}
 
 
