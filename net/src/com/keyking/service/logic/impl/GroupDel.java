package com.keyking.service.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.resp.impl.admin.GroupDelEntity;
import com.keyking.service.util.DataBuffer;

public class GroupDel implements Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception {
		GroupDelEntity entity = new GroupDelEntity(logicName);
		int len = buffer.getInt();
		if (len > 0){
			GroupEntity[] groups = new GroupEntity[len];
			for (int i = 0 ; i < len ; i++){
				int id  = buffer.getInt();
				groups[i] = DataManager.getInstance().searchGroup(id);
			}
			if (DataManager.getInstance().delGroup(groups)){
				entity.setResult(RespEntity.RESP_RESULT_SUCC);
			}
		}
		return entity;
	}

}
 
 
