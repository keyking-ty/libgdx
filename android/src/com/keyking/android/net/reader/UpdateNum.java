package com.keyking.android.net.reader;

import android.os.Message;

import com.keyking.android.TipActivity;
import com.keyking.net.message.DataBuffer;

public class UpdateNum extends Reader {

	@Override
	public void read_ok(DataBuffer data, TipActivity activity) throws Exception {
		int task = data.getInt();
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_UPDATE;
		msg.arg1 = task;
		activity.handler(msg);
	}
}
