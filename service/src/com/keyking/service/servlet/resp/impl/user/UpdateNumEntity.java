package com.keyking.service.servlet.resp.impl.user;

import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class UpdateNumEntity extends RespEntity {
	
	int task;
	
	public UpdateNumEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putInt(task);
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}
}
 
 
