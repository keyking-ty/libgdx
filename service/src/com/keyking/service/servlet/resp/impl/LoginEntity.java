package com.keyking.service.servlet.resp.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class LoginEntity extends RespEntity {
	
	UserEntity user;
	
	public LoginEntity(String logicName) {
		super(logicName);
	}
	
	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putLong(user.getId());
		if (user.getId() <= 1){//系统管理员
			DataManager.getInstance().sendAll(buffer);
		}else{
			buffer.putPrefixedString("登陆成功");
			buffer.putInt(user.getTask());
		}
	}
	
	@Override
	public void _serialize_fail(DataBuffer buffer) {
		super._serialize_fail(buffer);
	}
	
	public void setUer(UserEntity user) {
		this.user = user;
	}
}
 
 
 
 
