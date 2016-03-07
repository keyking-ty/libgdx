package com.keyking.service.servlet.logic.impl;

import java.sql.Date;
import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.user.DownInfoEntity;
import com.keyking.service.util.DataBuffer;
import com.keyking.service.util.SystemLog;

public class DownInfo extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		DownInfoEntity entity = new DownInfoEntity(logicName);
		long uid = buffer.getLong();
		int num  = buffer.getInt();
		UserEntity user = DataManager.getInstance().searchUser(uid);
		if (user != null){
			String preIp = user.getIp();
			String now = getIp();
			if (preIp != null && !preIp.equals(now)){
				entity.setError("���û����������豸��¼");
			}else if (num > user.getTask()){
				entity.setError("������������");
			}else{
				List<TelInfoEntity> tels = DataManager.getInstance().loadNotUserTel(this,num);
				if (tels.size() > 0){
					long sysTime = System.currentTimeMillis();
					Date date = new Date(sysTime);
					SystemLog.info(user.getUsername() + "��" + date.toString() + "������" + tels.size() + "����Դ");
					for (TelInfoEntity tel : tels){
						tel.setUserId(uid);
						tel.setDownTime(date);
					}
					int task = user.getTask() - tels.size();
					user.setTask(task);
					user.setChange(true);
					entity.setTels(tels);
				}else{
					entity.setError("��̨����������");
				}
			}
			entity.setUser(user);
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
		}
		return entity;
	}

}
 
 
 
 