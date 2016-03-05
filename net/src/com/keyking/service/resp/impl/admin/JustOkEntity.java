package com.keyking.service.resp.impl.admin;

import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class JustOkEntity extends RespEntity {

	public JustOkEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		
	}
}
 
