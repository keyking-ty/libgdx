package com.keyking.android.net.reader;

import android.os.Message;

import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public class ChangePassWard extends Reader {

	@Override
	public void read_ok(DataBuffer data, TipActivity activity) throws Exception {
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_TIP;
		msg.obj  = "ÐÞ¸Ä³É¹¦";
		activity.handler(msg);
	}
}
 
 
