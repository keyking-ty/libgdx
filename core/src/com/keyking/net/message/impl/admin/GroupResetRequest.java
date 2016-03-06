package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class GroupResetRequest extends RequestEntity {
	
	String ids;
	
	public GroupResetRequest(String ids) {
		super("GroupReset");
		this.ids = ids;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		String[] ss = ids.split(",");
		buffer.putInt(ss.length);
		for (String s : ss){
			int id = Integer.parseInt(s);
			buffer.putInt(id);
		}
	}
}
 
 
