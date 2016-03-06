package com.keyking.service.servlet.resp;

import com.keyking.service.util.DataBuffer;

public abstract class RespEntity {
	
	
	public static final int RESP_RESULT_SUCC = 0;
	
	public static final int RESP_RESULT_FAIL = 1;
	
	int result = RESP_RESULT_FAIL;
	
	String logicName;
	
	String error = " ";
	
	long uid;
	
	public RespEntity(String logicName){
		this.logicName = logicName;
	}
	
	public void serialize(DataBuffer buffer){
		buffer.putPrefixedString(logicName);
		buffer.putInt(result);
		if (result == RESP_RESULT_SUCC){
			_serialize_ok(buffer);
		}else{
			_serialize_fail(buffer);
		}
	}
	
	public abstract void _serialize_ok(DataBuffer buffer);
	
	public void _serialize_fail(DataBuffer buffer){
		buffer.putPrefixedString(error);
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getLogicName() {
		return logicName;
	}

	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
}
 
 
 
 
