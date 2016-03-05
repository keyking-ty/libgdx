package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.frame.UserScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class ChangePassWard extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				UserScreen user = (UserScreen)ENGINE.getGameScreen();
				user.doFix();
			}
		});
	}
}
 
