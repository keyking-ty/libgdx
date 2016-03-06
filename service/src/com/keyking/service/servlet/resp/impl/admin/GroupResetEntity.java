package com.keyking.service.servlet.resp.impl.admin;

import com.keyking.service.dao.DataManager;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class GroupResetEntity extends RespEntity {

	public GroupResetEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		DataManager.getInstance().sendAll(buffer);
	}
}
 
 
 
 
