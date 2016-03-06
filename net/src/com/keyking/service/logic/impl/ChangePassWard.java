package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.user.ChangePasswardEntity;
import com.keyking.service.util.DataBuffer;

public class ChangePassWard implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		ChangePasswardEntity entity = new ChangePasswardEntity(logicName);
		long uid  = buffer.getLong();
		String old = buffer.getPrefixedString();
		String newpassward = buffer.getPrefixedString();
		UserEntity user = DataManager.getInstance().searchUser(uid);
		if (user != null){
			if (user.getPassword().equals(old)){
				entity.setResult(RespEntity.RESP_RESULT_SUCC);
				user.setPassword(newpassward);
				DataManager.getInstance().save(user);
			}else{
				entity.setError("旧密码错误");
			}
		}else{
			entity.setError("系统错误，请联系管理员");
		}
		return entity;
	}

}
 
 
