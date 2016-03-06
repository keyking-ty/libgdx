package com.keyking.net.message.impl.user;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class CommiteRequest extends RequestEntity {
	
	long uid;
	
	String infos;
	
	public CommiteRequest(long uid , String infos) {
		super("Commite");
		this.infos = infos;
		this.uid = uid;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(uid);
		buffer.putPrefixedString(infos);
	}
}
 
 
