package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.frame.AdminScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class UserAdd extends Reader {
	
	@Override
	public void read_ok(DataBuffer data) throws Exception{
		DM.ReadAndInsertUser(data);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				AdminScreen main = (AdminScreen)ENGINE.getGameScreen();
				main.doUserAdd();
			}
		});
	}
}
 
 
