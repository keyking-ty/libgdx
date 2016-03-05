package com.keyking.net.message.impl.admin;

import java.util.List;

import com.keyking.data.TelInfo;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class TelInfoRequest extends RequestEntity {
	
	List<TelInfo> tels = null;
	
	public TelInfoRequest(List<TelInfo> tels){
		super("TelInfoAdd");
		this.tels = tels;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.putInt(tels.size());
		while(tels.size() > 0){
			int index = ENGINE.random(tels.size());
			TelInfo tel = tels.get(index);
			tel._serialize(buffer);
			tels.remove(index);
		}
	}
}
 
