package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class GroupAddRequest extends RequestEntity {

	String name;
	
	int fatherId;
	
	public GroupAddRequest(String name , int fatherId){
		super("GroupAdd");
		this.name = name;
		this.fatherId = fatherId;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putPrefixedString(name);
		buffer.putInt(fatherId);
	}
}
 
 
