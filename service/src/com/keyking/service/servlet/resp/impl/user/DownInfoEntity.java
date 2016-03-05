package com.keyking.service.servlet.resp.impl.user;

import java.util.List;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class DownInfoEntity extends RespEntity {
	
	List<TelInfoEntity> tels;
	
	UserEntity user;
	
	public DownInfoEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putInt(user.getTask());
		if (tels != null){
			buffer.putInt(tels.size());
			for (TelInfoEntity tel : tels){
				tel._serialize(buffer);
			}
		}else{
			buffer.putInt(0);
			buffer.putPrefixedString(getError());
		}
	}

	public void setTels(List<TelInfoEntity> tels) {
		this.tels = tels;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
 
 
 
