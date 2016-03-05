package com.keyking.service.servlet.logic.impl;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.UserFixEntity;
import com.keyking.service.util.DataBuffer;

public class UserFix extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		UserFixEntity entity = new UserFixEntity(logicName);
		long id = buffer.getLong();
		int age = buffer.getInt();
		int fatherId = buffer.getInt();
		int task = buffer.getInt();
		String name = buffer.getPrefixedString();
		String pass = buffer.getPrefixedString();
		String firstName = buffer.getPrefixedString();
		String lastName = buffer.getPrefixedString();
		String telephone = buffer.getPrefixedString();
		String address = buffer.getPrefixedString();
		String email = buffer.getPrefixedString();
		String post = buffer.getPrefixedString();
		UserEntity user = DataManager.getInstance().searchUser(id);
		GroupEntity father = DataManager.getInstance().searchGroup(fatherId);
		if (user == null){
			entity.setError("找不到要修改的用户");
			return entity;
		}
		user.setAge(age);
		user.setTask(task);
		user.setFather(father);
		user.setUsername(name);
		user.setPassword(pass);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setTelephone(telephone);
		user.setAddress(address);
		user.setEmail(email);
		user.setPost(post);
		user.setChange(true);
		entity.setResult(RespEntity.RESP_RESULT_SUCC);
		return entity;
	}

}
 
 
 
