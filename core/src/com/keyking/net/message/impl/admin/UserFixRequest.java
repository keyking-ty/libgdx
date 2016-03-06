package com.keyking.net.message.impl.admin;

import com.keyking.data.User;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class UserFixRequest extends RequestEntity {
	
	User user;
	
	public UserFixRequest(User user){
		super("UserFix");
		this.user = user;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		user._serialize(buffer);
	}
}
 
 
