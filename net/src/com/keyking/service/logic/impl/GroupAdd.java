package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.admin.GroupAddEntity;
import com.keyking.service.util.DataBuffer;

public class GroupAdd implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception {
		String name = buffer.getPrefixedString();
		int fatherId = buffer.getInt();
		GroupAddEntity entity = new GroupAddEntity(logicName);
		GroupEntity father = DataManager.getInstance().searchGroup(fatherId);
		if (father == null && fatherId > 0){
			entity.setError("¡• Ù≤ø√≈¥ÌŒÛ");
			return entity;
		}
		GroupEntity group = new GroupEntity();
		group.setName(name);
		group.setFather(father);
		if (DataManager.getInstance().registGroup(group)){
			entity.setResult(RespEntity.RESP_RESULT_SUCC);
			entity.setGroup(group);
		}
		return entity;
	}

}
 
 
