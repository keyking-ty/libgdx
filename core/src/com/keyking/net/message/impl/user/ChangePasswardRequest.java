package com.keyking.net.message.impl.user;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class ChangePasswardRequest extends RequestEntity{
	long uid ;
	
	String old;
	
	String newpassward;
	
	public ChangePasswardRequest(long uid,String old, String newpassward) {
		super("ChangePassWard");
		this.uid = uid;
		this.old = old;
		this.newpassward = newpassward;
	}

	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(uid);
		buffer.putPrefixedString(old);
		buffer.putPrefixedString(newpassward);
	}
}
 
