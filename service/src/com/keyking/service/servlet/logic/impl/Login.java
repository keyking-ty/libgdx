package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.LoginEntity;
import com.keyking.service.util.DataBuffer;
import com.keyking.service.util.SystemLog;

public class Login extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception {
		String username = buffer.getPrefixedString();
		String password = buffer.getPrefixedString();
		LoginEntity resp = new LoginEntity(logicName);
		UserEntity entity = DataManager.getInstance().login(username,password);
		if (entity != null){
			entity.setIp(getIp());
			resp.setUer(entity);
			resp.setResult(RespEntity.RESP_RESULT_SUCC);
			SystemLog.info(username + "µÇÂ¼³É¹¦");
		}else{
			resp.setError("ÓÃ»§ºÍÃÜÂë´íÎó");
			SystemLog.info(username + "µÇÂ¼Ê§°Ü");
		}
		return resp;
	}

}
 
 
 
 
