package com.keyking.android.net.reader;

import android.os.Bundle;
import android.os.Message;

import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public class Login extends Reader {

	@Override
	public void read_ok(DataBuffer data,TipActivity activity) throws Exception {
		long userId = data.getLong();
		String tip = data.getPrefixedString();
		if (tip != null && !tip.equals("")){
			Message msg = new Message();
			msg.what = TipActivity.HANDLER_CODE_TIP;
			msg.obj  = tip;
			activity.handler(msg);
		}
		int taskNum = data.getInt();
		//Ìø×ªactivity
		Bundle bundle = new Bundle();  
        bundle.putLong("uid",userId);  
        bundle.putInt("task",taskNum);
        Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_ENTER_IN;
		msg.obj  = bundle;
		activity.handler(msg);
	}
}
 
 
