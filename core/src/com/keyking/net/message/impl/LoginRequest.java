package com.keyking.net.message.impl;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class LoginRequest extends RequestEntity {
	
	String username;
	
	String passward;
	
	public LoginRequest(String username,String passward){
		super("Login");
		this.username = username;
		this.passward = passward;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putPrefixedString(username);
		buffer.putPrefixedString(passward);
	}
}
 
 
