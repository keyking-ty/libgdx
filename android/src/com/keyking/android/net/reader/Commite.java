package com.keyking.android.net.reader;

import android.os.Message;

import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public class Commite extends Reader {

	@Override
	public void read_ok(DataBuffer data, TipActivity activity) throws Exception {
		int flag = data.getInt();
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_TIP;
		if (flag == 1){
			msg.obj  = "提交成功";
		}else{
			msg.obj  = data.getPrefixedString();
		}
		activity.handler(msg);
	}
}
 
 
