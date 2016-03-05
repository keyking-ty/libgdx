package com.keyking.net.reader.impl;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.keyking.data.Node;
import com.keyking.data.TelInfo;
import com.keyking.frame.UserScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class DownInfo extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception {
		int task = data.getInt();
		DM.setTaskNum(task);
		List<Node> tels = DM.getTels();
		int size = data.getInt();
		if (size > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0 ; i < size ; i++){
				String name      = data.getPrefixedString();
				String telephone = data.getPrefixedString();
				TelInfo info  = new TelInfo();
				info.setName(name);
				info.setTelephone(telephone);
				info.setUserId(DM.getUserId());
				sb.append(name + "," + telephone + ",");
				tels.add(info);
			}
			sb.deleteCharAt(sb.length()-1);
			ENGINE.insertContact(sb.toString());
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					UserScreen user = (UserScreen)ENGINE.getGameScreen();
					user.doDown();
				}
			});
		}else{
			final String tip = data.getPrefixedString().trim();
			if (tip != null && tip.equals("")){
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						ENGINE.getGameScreen().message(tip);
					}
				});
			}
		}
	}
}
 
