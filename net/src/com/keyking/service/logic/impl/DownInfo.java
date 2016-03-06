package com.keyking.service.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.user.DownInfoEntity;
import com.keyking.service.util.DataBuffer;

public class DownInfo implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		DownInfoEntity entity = new DownInfoEntity(logicName);
		long uid = buffer.getLong();
		UserEntity user = DataManager.getInstance().searchUser(uid);
		if (user != null){
			List<TelInfoEntity> tels = new ArrayList<TelInfoEntity>();
			List<TelInfoEntity> tels1 = DataManager.getInstance().loadUserTel(user);
			List<TelInfoEntity> tels2 = DataManager.getInstance().loadNotUserTel(user);
			if (tels2 != null && tels2.size() > 0){
				for (TelInfoEntity tel : tels2){
					tel.setUserId(uid);
					DataManager.getInstance().save(tel);
				}
				tels.addAll(tels2);
			}else{
				entity.setError("服务器数据已用完");
			}
			if (tels1 != null && tels1.size() > 0){
				tels.addAll(tels1);
			}
			entity.setTels(tels);
			entity.setUid(uid);
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
		}
		return entity;
	}

}
 
 
