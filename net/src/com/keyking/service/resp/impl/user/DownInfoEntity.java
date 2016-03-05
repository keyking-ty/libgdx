package com.keyking.service.resp.impl.user;

import java.util.List;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class DownInfoEntity extends RespEntity {
	
	List<TelInfoEntity> tels;
	
	long uid;
	
	public DownInfoEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putLong(uid);
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

	public void setTels(List<TelInfoEntity> tels) {
		this.tels = tels;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
}
 
