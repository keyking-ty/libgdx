package com.keyking.net.reader;

import com.badlogic.gdx.Gdx;
import com.keyking.net.message.DataBuffer;
import com.keyking.util.Instances;

public abstract class Reader implements Instances{
	
	public static final int MESSAGE_RESULT_SUCC = 0;
	
	public static final int MESSAGE_RESULT_FAIL = 1;
	
	public void read(DataBuffer data){
		int result = data.getInt();
		if (result == MESSAGE_RESULT_SUCC){
			try {
				read_ok(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			read_fail(data);
		}
	}
	
	public abstract void read_ok(DataBuffer data) throws Exception;
	
	public void read_fail(DataBuffer data) {
		final String str = data.getPrefixedString();
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				ENGINE.getGameScreen().message(str);
			}
		});
	}
}
 
 
