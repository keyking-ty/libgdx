package com.keyking.data;

import java.util.List;

import com.keyking.net.message.DataBuffer;

public class TelInfo implements Node{
	
	String name;
	
	String telephone;
	
	long userId;
	
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
		buffer.putLong(userId);
		buffer.putPrefixedString(name);
		buffer.putPrefixedString(telephone);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public String getFixInfo(float space) {
		return "," + getCount() + "," + space + "," + telephone;
	}

	@Override
	public boolean couldOpen() {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		return null;
	}

	@Override
	public Node getFather() {
		return null;
	}

	@Override
	public void setFather(Node father) {
		
	}
}
 
 
