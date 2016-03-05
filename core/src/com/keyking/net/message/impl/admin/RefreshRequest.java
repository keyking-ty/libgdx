package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class RefreshRequest extends RequestEntity {
	boolean flag;
	int id;
	public RefreshRequest(boolean flag,int id) {
		super("Refresh");
		this.flag = flag;
		this.id   = id;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putInt(flag ? 1 : 0);
		buffer.putInt(id);
	}

}
