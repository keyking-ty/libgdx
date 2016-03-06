package com.keyking.service.dao.entity;

import com.keyking.service.util.DataBuffer;

public class TelInfoEntity {
	
	String name;
	
	String telephone;
	
	long userId;

	int flag;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void _serialize(DataBuffer buffer) {
		buffer.putInt(flag);
		buffer.putPrefixedString(name);
		buffer.putPrefixedString(telephone);
	}
}
 
 
