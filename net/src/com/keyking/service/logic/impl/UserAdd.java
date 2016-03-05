package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.admin.UserAddEntity;
import com.keyking.service.util.DataBuffer;

public class UserAdd implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer,String logicName)throws Exception {
		UserAddEntity entity = new UserAddEntity(logicName);
		String name = buffer.getPrefixedString();
		int fatherId = buffer.getInt();
		UserEntity user = new UserEntity();
		user.setUsername(name);
		GroupEntity father = DataManager.getInstance().searchGroup(fatherId);
		if (father != null){
			user.setFather(father);
		}
		if (DataManager.getInstance().registUser(user)){
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
			entity.setUser(user);
		}else{
			entity.setError("×¢²áÊ§°Ü");
		}
		return entity;
	}

}
 
