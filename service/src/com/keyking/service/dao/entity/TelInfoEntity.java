package com.keyking.service.dao.entity;

import java.sql.Date;

import com.keyking.service.util.DataBuffer;

public class TelInfoEntity {
	
	String name;
	
	String telephone;
	
	long userId;
	
	Date downTime;
	
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

	public void _serialize(DataBuffer buffer) {
		buffer.putPrefixedString(name);
		buffer.putPrefixedString(telephone);
	}

	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}
}
 
 
 
 
