package com.keyking.service.servlet.logic.impl;

import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.GroupResetEntity;
import com.keyking.service.util.DataBuffer;
import com.keyking.service.util.SystemLog;

public class GroupReset extends Logic {

	@Override
	public RespEntity doLogic(final DataBuffer buffer, final String logicName)
			throws Exception {
		GroupResetEntity entity = new GroupResetEntity(logicName);
		int len = buffer.getInt();
		if (len > 0){
			for (int i = 0 ; i < len ; i++){
				int id  = buffer.getInt();
				List<UserEntity> users = DataManager.getInstance().searchUsers(id);
				for (UserEntity user : users){
					int pre = user.getTask();
					user.reset();
					user.setChange(true);
					SystemLog.info(user.getUsername() + " 的任务数量由:" + pre + "变为:" + user.getTask());
				}
			}
		}
		entity.setResult(RespEntity.RESP_RESULT_SUCC);
		return entity;
	}

}
 
 
 
 
