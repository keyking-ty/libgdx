package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class UserDelRequest extends RequestEntity {
	
	String ids;
	
	public UserDelRequest(String ids){
		super("UserDel");
		this.ids = ids;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(DM.getUserId());
		String[] ss = ids.split(",");
		buffer.putInt(ss.length);
		for (String s : ss){
			int id = Integer.parseInt(s);
			buffer.putInt(id);
		}
	}
}
 
