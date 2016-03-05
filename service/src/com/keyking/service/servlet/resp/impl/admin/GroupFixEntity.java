package com.keyking.service.servlet.resp.impl.admin;

import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class GroupFixEntity extends RespEntity {
	
	GroupEntity group;
	
	public GroupFixEntity(String logicName) {
		super(logicName);
	}
	
	@Override
	public void _serialize_ok(DataBuffer buffer){
		if (group != null){
			group._serialize(buffer);
		}
	}
	
	public void setGroup(GroupEntity group) {
		this.group = group;
	}
}
 
 
 
