package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.JustOkEntity;
import com.keyking.service.util.DataBuffer;

public class TelInfoAdd extends Logic{
	
	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)throws Exception {
		JustOkEntity entity = new JustOkEntity(logicName);
		int len = buffer.getInt();
		final TelInfoEntity[] tels = new TelInfoEntity[len];
		for (int i = 0 ; i < len ; i ++){
			long userId = buffer.getLong();
			String name = buffer.getPrefixedString();
			String telephone  = buffer.getPrefixedString();
			TelInfoEntity tel = new TelInfoEntity();
			tel.setName(name);
			tel.setTelephone(telephone);
			tel.setUserId(userId);
			tels[i] = tel;
		}
		new Thread(){
			@Override
			public void run() {
				DataManager.getInstance().insertCustomers(tels);
			}
		}.start();
		entity.setResult(RespEntity.RESP_RESULT_SUCC);
		return entity;
	}

}
 
 
 
 
