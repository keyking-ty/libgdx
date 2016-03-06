package com.keyking.service.servlet.resp.impl.admin;

import java.util.List;

import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class TelInfoExportEntity extends RespEntity {
	
	String fileName ;
	
	List<TelInfoEntity> tels ;
	
	public TelInfoExportEntity(String logicName) {
		super(logicName);
	}

	@Override
	public void _serialize_ok(DataBuffer buffer) {
		buffer.putPrefixedString(fileName);
		if (tels != null){
			buffer.putInt(tels.size());
			for (TelInfoEntity tel : tels){
				buffer.putPrefixedString(tel.getTelephone());
			}
		}else{
			buffer.putInt(0);
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setTels(List<TelInfoEntity> tels) {
		this.tels = tels;
	}
}
