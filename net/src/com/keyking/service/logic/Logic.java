package com.keyking.service.logic;

import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;


public interface Logic{
	public RespEntity doLogic(DataBuffer buffer,String logicName) throws Exception;
}
 
