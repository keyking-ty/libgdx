package com.keyking.service.resp.impl.admin;

import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class GroupAddEntity extends RespEntity {
	
	public GroupAddEntity(String logicName) {
		super(logicName);
	}
	
	GroupEntity group = null;
	
	@Override
	public void _serialize_ok(DataBuffer buffer) {
		group._serialize(buffer);
	}
	
	@Override
	public void _serialize_fail(DataBuffer buffer) {
		super._serialize_fail(buffer);
	}
	
	public void setGroup(GroupEntity group) {
		this.group = group;
	}
}
 
 
