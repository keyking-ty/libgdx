package com.keyking.service.util;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

public class BufferUtil {

	public static String readUTF(IoBuffer ioBuffer) {
		String str = null;
		try {
			short len = ioBuffer.getShort();
			byte[] datas = new byte[len];
			ioBuffer.get(datas);
			str = new String(datas,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void writeUTF(String str,IoBuffer ioBuffer) {
		try {
			byte[] datas = str.getBytes("UTF-8");
			ioBuffer.putShort((short)datas.length);
			ioBuffer.put(datas);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
 
 
