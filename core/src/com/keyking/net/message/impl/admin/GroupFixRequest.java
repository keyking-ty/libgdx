package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class GroupFixRequest extends RequestEntity {
	
	int id;
	
	String name;
	
	int fatherId;
	
	int task;
	
	public GroupFixRequest(int id, String name, int fatherId, int task) {
		super("GroupFix");
		this.id = id;
		this.name = name;
		this.fatherId = fatherId;
		this.task = task;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putInt(id);
		buffer.putPrefixedString(name);
		buffer.putInt(fatherId);
		buffer.putInt(task);
	}
}
 
