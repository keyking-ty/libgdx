package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.user.UpdateNumEntity;
import com.keyking.service.util.DataBuffer;

public class UpdateNum extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		UpdateNumEntity update = new UpdateNumEntity(logicName);
		long uid = buffer.getLong();
		UserEntity user= DataManager.getInstance().searchUser(uid);
		if (user != null){
			update.setTask(user.getTask());
			update.setResult(RespEntity.RESP_RESULT_SUCC);
		}else{
			update.setError("”√ªß±‡∫≈¥ÌŒÛ");
		}
		return update;
	}

}
 
