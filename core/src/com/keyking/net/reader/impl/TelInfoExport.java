package com.keyking.net.reader.impl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.keyking.data.TelInfo;
import com.keyking.frame.AdminScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;
import com.keyking.util.DataOperateUtil;

public class TelInfoExport extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception {
		String fileName = data.getPrefixedString();
		int size = data.getInt();
		List<TelInfo> tels = new ArrayList<TelInfo>();
		for (int i = 0 ;i < size ; i++){
			TelInfo info = new TelInfo();
			info.setTelephone(data.getPrefixedString());
			tels.add(info);
		}
		DataOperateUtil.export(fileName,tels);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				AdminScreen main = (AdminScreen)ENGINE.getGameScreen();
				main.doExport();
			}
		});
	}
}
