package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.data.Group;
import com.keyking.data.User;
import com.keyking.frame.AdminScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class Refresh extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception {
		int flag = data.getInt();
		if (flag == 1){
			Group group = DM.readGroup(data);
			Group saver  = (Group)DM.searchGroup(group.getId());
			saver.copy(group);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					AdminScreen admin = (AdminScreen)ENGINE.getGameScreen();
					admin.refreshWork(3);
				}
			});
		}else{
			User user   = DM.readUser(data);
			User saver  = DM.searchUser(user.getId());
			saver.copy(user);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					AdminScreen admin = (AdminScreen)ENGINE.getGameScreen();
					admin.refreshWork(6);
				}
			});
		}
	}

}
