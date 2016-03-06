package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.GroupFixEntity;
import com.keyking.service.util.DataBuffer;

public class GroupFix extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer , String logicName)throws Exception {
		GroupFixEntity fix = new GroupFixEntity(logicName);
		int id = buffer.getInt();
		String name = buffer.getPrefixedString();
		int fatherId = buffer.getInt();
		int task = buffer.getInt();
		GroupEntity group  = DataManager.getInstance().searchGroup(id);
		GroupEntity father = DataManager.getInstance().searchGroup(fatherId);
		if (group == null){
			fix.setError("找不到要修改的部门");
			return fix;
		}
		if (group.getFather() != null && group.getFather().getId() == fatherId){//父亲没有变
			group.setName(name);
			group.setTask(task);
		}else{
			group.setFather(father);
			group.setName(name);
			group.setTask(task);
		}
		group.setChange(true);
		fix.setGroup(group);
		fix.setResult(RespEntity.RESP_RESULT_SUCC);
		return fix;
	}

}
 
 
 
 
