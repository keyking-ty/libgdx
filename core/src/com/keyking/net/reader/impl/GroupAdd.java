package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.data.Group;
import com.keyking.data.Node;
import com.keyking.frame.AdminScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class GroupAdd extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception{
		Group group = DM.readGroup(data);
		Node father = DM.searchGroup(group.getFid());
		group.setFather(father);
		DM.addGroup(group);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				AdminScreen main = (AdminScreen)ENGINE.getGameScreen();
				main.doGroupAdd();
			}
		});
	}
}
 
