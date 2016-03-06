package com.keyking.service.resp.impl;

import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class LoginEntity extends RespEntity {
	
	long uerId;
	
	List<TelInfoEntity> tels;
	
	public LoginEntity(String logicName) {
		super(logicName);
	}
	
	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putLong(uerId);
		if (uerId <= 1){//系统管理员
			DataManager.getInstance().sendAll(buffer);
		}else{
			buffer.putPrefixedString(getError());
			if (tels != null){
				buffer.putInt(tels.size());
				for (TelInfoEntity tel : tels){
					tel._serialize(buffer);
				}
			}else{
				buffer.putInt(0);
			}
		}
	}
	
	@Override
	public void _serialize_fail(DataBuffer buffer) {
		super._serialize_fail(buffer);
	}
	
	public void setUerId(long uerId) {
		this.uerId = uerId;
	}

	public void setTels(List<TelInfoEntity> tels) {
		this.tels = tels;
	}
}
 
 
