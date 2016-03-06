package com.keyking.service.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.admin.JustOkEntity;
import com.keyking.service.util.DataBuffer;

public class TelInfoAdd implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)throws Exception {
		JustOkEntity entity = new JustOkEntity(logicName);
		int len = buffer.getInt();
		List<TelInfoEntity> tels = new ArrayList<TelInfoEntity>();
		for (int i = 0 ; i < len ; i ++){
			long userId  = buffer.getLong();
			int flag     = buffer.getInt();
			String name = buffer.getPrefixedString();
			String telephone = buffer.getPrefixedString();
			TelInfoEntity tel = new TelInfoEntity();
			tel.setName(name);
			tel.setTelephone(telephone);
			tel.setUserId(userId);
			tel.setFlag(flag);
			tels.add(tel);
		}
		if (DataManager.getInstance().insertCustomer(tels)){
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
		}
		return entity;
	}

}
 
 
