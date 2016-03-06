package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.data.TelInfo;
import com.keyking.frame.UserScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class Commite extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception {
		int flag = data.getInt();
		if (flag == 1){
			String name      = data.getPrefixedString();
			String telephone = data.getPrefixedString();
			TelInfo info  = new TelInfo();
			info.setName(name);
			info.setTelephone(telephone);
			info.setUserId(DM.getUserId());
			DM.getTels().add(info);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					UserScreen user = (UserScreen)ENGINE.getGameScreen();
					user.doCommite();
				}
			});
		}else{
			final String tip = data.getPrefixedString();
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					ENGINE.getGameScreen().message(tip);
				}
			});
		}
	}
}
 
 
