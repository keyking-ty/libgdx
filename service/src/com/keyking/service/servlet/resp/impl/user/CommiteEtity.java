package com.keyking.service.servlet.resp.impl.user;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class CommiteEtity extends RespEntity{
	
	TelInfoEntity tel;
	
	public CommiteEtity(String logicName) {
		super(logicName);
	}
	
	@Override
	public void _serialize_ok(DataBuffer buffer) {
		if (tel != null){
			buffer.putInt(1);
			tel._serialize(buffer);
		}else{
			buffer.putInt(0);
			buffer.putPrefixedString(getError());
		}
	}

	public void setTel(TelInfoEntity tel) {
		this.tel = tel;
	}
}
 
 
 
