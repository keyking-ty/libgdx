package com.keyking.service.servlet.logic;

import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;


public abstract class  Logic{
	
	String ip;
	
	public abstract RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
 
 
 
