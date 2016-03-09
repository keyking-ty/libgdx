package com.keyking.service.servlet.logic.impl;

import java.util.List;

import com.keyking.service.dao.DataManager;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.servlet.resp.impl.admin.TelInfoExportEntity;
import com.keyking.service.util.DataBuffer;

public class TelInfoExport extends Logic {

	@Override
	public RespEntity doLogic(DataBuffer buffer, String logicName)
			throws Exception {
		TelInfoExportEntity resp = new TelInfoExportEntity(logicName);
		int downType = buffer.getInt();
		byte type = buffer.get();
		String fileName = buffer.getPrefixedString();
		String start = buffer.getPrefixedString();
		String end   = buffer.getPrefixedString();
		List<TelInfoEntity> tels = DataManager.getInstance().exportTel(downType,start,end,type == 1);
		resp.setFileName(fileName);
		resp.setTels(tels);
		resp.setResult(RespEntity.RESP_RESULT_SUCC);
		return resp;
	}
}
