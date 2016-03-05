package com.keyking.service.servlet.resp.impl.admin;

import com.keyking.service.dao.DataManager;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class GroupDelEntity extends RespEntity {
	
	public GroupDelEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putLong(getUid());
		DataManager.getInstance().sendAll(buffer);
	}
}
 
 
 
