package com.keyking.service.logic.impl;

import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.user.CommiteEtity;
import com.keyking.service.util.DataBuffer;

public class Commite implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		CommiteEtity entity = new CommiteEtity(logicName);
		long uid = buffer.getLong();
		String info = buffer.getPrefixedString();
		if (info != null && !info.equals("")){
			String[] ss = info.split(",");
			for (int i = 0 ; i < ss.length ; i += 2){
				String name = ss[i];
				String num  = ss[i+1];
				TelInfoEntity tel = new TelInfoEntity();
				tel.setName(name);
				tel.setTelephone(num);
				tel.setFlag(1);
				tel.setUserId(uid);
				DataManager.getInstance().save(tel);
			}
			UserEntity user = DataManager.getInstance().searchUser(uid);
			List<TelInfoEntity> tels = DataManager.getInstance().loadUserTel(user);
			entity.setUid(uid);
			entity.setTels(tels);
			entity.setError("提交成功");
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
		}else{
			entity.setError("提交失败");
		}
		return entity;
	}

}
 
 
