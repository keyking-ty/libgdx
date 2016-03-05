package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.RefreshEntity;
import com.keyking.service.util.DataBuffer;

public class Refresh extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		RefreshEntity refresh = new RefreshEntity(logicName);
		int flag = buffer.getInt();
		int id   = buffer.getInt();
		if (flag == 1){
			GroupEntity group = DataManager.getInstance().searchGroup(id);
			refresh.setGroup(group);
		}else{
			UserEntity user = DataManager.getInstance().searchUser(id);
			refresh.setUser(user);
		}
		refresh.setFlag(flag);
		refresh.setResult(RespEntity.RESP_RESULT_SUCC);
		return refresh;
	}

}
 
