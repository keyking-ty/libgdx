package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.LoginEntity;
import com.keyking.service.util.DataBuffer;

public class Login implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception {
		String username = buffer.getPrefixedString();
		String password = buffer.getPrefixedString();
		LoginEntity resp = new LoginEntity(logicName);
		UserEntity entity = DataManager.getInstance().login(username,password);
		if (entity != null){
			if (entity.getId() > 1){
				resp.setTels(DataManager.getInstance().loadUserTel(entity));
			}
			resp.setUerId(entity.getId());
			resp.setResult(RespEntity.RESP_RESULT_SUCC);
		}else{
			resp.setError("ÓÃ»§ºÍÃÜÂë´íÎó");
		}
		return resp;
	}

}
 
 
