package com.keyking.net.message.impl.admin;

import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class TelInfoDown extends RequestEntity {
	String start = null;
	String end = null;
	boolean flag = false;
	String fileName = null;
	
	public TelInfoDown(String fileName,String start , String end,boolean flag){
		super("TelInfoExport");
		this.fileName = fileName;
		this.start = start;
		this.end = end;
		this.flag = flag;
	}
	
	@Override
	public void _serialize(DataBuffer buffer) {
		buffer.put((byte)(flag ? 1 : 0));
		buffer.putPrefixedString(fileName);
		buffer.putPrefixedString(start);
		buffer.putPrefixedString(end);
	}

}
