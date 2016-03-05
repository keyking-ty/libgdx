package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.admin.UserDelEntity;
import com.keyking.service.util.DataBuffer;

public class UserDel implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName) throws Exception {
		UserDelEntity del = new UserDelEntity(logicName);
		int len = buffer.getInt();
		if (len > 0){
			UserEntity[] users = new UserEntity[len];
			for (int i = 0 ; i < len ; i++){
				int id  = buffer.getInt();
				users[i] = DataManager.getInstance().searchUser(id);
			}
			if (DataManager.getInstance().delUser(users)){
				del.setResult(RespEntity.RESP_RESULT_SUCC);
			}
		}
		return del;
	}

}
 
