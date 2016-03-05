package com.keyking.android.net.reader;

import android.os.Message;

import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public abstract class Reader {
	
	public static final int MESSAGE_RESULT_SUCC = 0;
	
	public static final int MESSAGE_RESULT_FAIL = 1;
	
	public void read(DataBuffer data,TipActivity activity){
		int result = data.getInt();
		if (result == MESSAGE_RESULT_SUCC){
			try {
				read_ok(data,activity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			read_fail(data,activity);
		}
	}
	
	public abstract void read_ok(DataBuffer data,TipActivity activity) throws Exception;
	
	public void read_fail(DataBuffer data,TipActivity activity) {
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_TIP;
		msg.obj = data.getPrefixedString();
		activity.handler(msg);
	}
}
 
 
