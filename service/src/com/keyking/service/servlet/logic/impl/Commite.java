package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.user.CommiteEtity;
import com.keyking.service.util.DataBuffer;

public class Commite extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		CommiteEtity entity = new CommiteEtity(logicName);
		long uid = buffer.getLong();
		String info = buffer.getPrefixedString();
		String[] ss = info.split(",");
		TelInfoEntity tel = new TelInfoEntity();
		tel.setName(ss[0]);
		tel.setTelephone(ss[1]);
		tel.setUserId(uid);
		if (DataManager.getInstance().insertCustomer(tel)){
			entity.setTel(tel);
			entity.setError("提交成功");
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
		}else{
			entity.setError("提交失败");
		}
		return entity;
	}

}
 
 
 
