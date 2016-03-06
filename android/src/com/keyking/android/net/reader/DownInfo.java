package com.keyking.android.net.reader;

import android.os.Message;

import com.keyking.android.MainActivity;
import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public class DownInfo extends Reader {

	@Override
	public void read_ok(DataBuffer data, TipActivity activity) throws Exception {
		int task = data.getInt();
		int size = data.getInt();
		if (size > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0 ; i < size ; i++){
				String name      = data.getPrefixedString();
				String telephone = data.getPrefixedString();
				sb.append(name + "," + telephone + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			Message msg = new Message();
			msg.what = TipActivity.HANDLER_CODE_DOWN;
			msg.obj  = sb.toString();
			activity.handler(msg);
			((MainActivity)activity).setTask(task);
		}else{
			Message msg = new Message();
			msg.what = TipActivity.HANDLER_CODE_TIP;
			msg.obj  = data.getPrefixedString();
			activity.handler(msg);
		}
	}
}
 
 
