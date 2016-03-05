package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.frame.AdminScreen;
import com.keyking.frame.UserScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class Login extends Reader {
	
	@Override
	public void read_ok(DataBuffer data) throws Exception{
		DM.update(data);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				ENGINE.notifyEvent(EVENT.create_evt_Next(1,DM.getUserId() > 1 ? new UserScreen() : new  AdminScreen()));
			}
		});
	}
}
 
