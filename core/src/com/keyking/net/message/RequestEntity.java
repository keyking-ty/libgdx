package com.keyking.net.message;

import com.keyking.util.Instances;

public abstract class RequestEntity implements Instances{
	
	String logicName;
	
	public RequestEntity(String logicName){
		this.logicName = logicName;
	}
	
	public  void serialize(DataBuffer buffer){
		buffer.putPrefixedString(logicName);
		_serialize(buffer);
	}
	
	public abstract void _serialize(DataBuffer buffer);

	public String key() {
		return logicName;
	}
}
 
 
