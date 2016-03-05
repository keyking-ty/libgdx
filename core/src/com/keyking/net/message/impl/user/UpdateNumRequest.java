package com.keyking.net.message.impl.user;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class UpdateNumRequest extends RequestEntity {
	
	long uid;
	
	public UpdateNumRequest(long uid) {
		super("UpdateNum");
		this.uid = uid;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(uid);
	}

}
