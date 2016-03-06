package com.keyking.net.message.impl.user;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class DownInfoRequest extends RequestEntity {
	
	long uid;
	
	int num;
	
	public DownInfoRequest(long uid ,int num) {
		super("DownInfo");
		this.uid = uid;
		this.num = num;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(uid);
		buffer.putInt(num);
	}
	
	
}
 
 
