package com.keyking.net.reader.impl;

import com.badlogic.gdx.Gdx;
import com.keyking.data.Group;
import com.keyking.frame.AdminScreen;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.reader.Reader;

public class GroupFix extends Reader {

	@Override
	public void read_ok(DataBuffer data) throws Exception{
		Group g = DM.readGroup(data);
		Group group = (Group)DM.searchGroup(g.getId());
		Group father = (Group)DM.searchGroup(g.getFid());//ÐÂ¸¸Ç×
		if (group.getFather() == null && father != null){
			DM.getTreeGroups().remove(group);
			father.addChild(group);
		}else if (group.getFather() != null){
			group.getFather().getChildren().remove(group);
			if (father != null){
				father.addChild(group);
			}else{
				DM.getTreeGroups().add(group);
				group.setFather(null);
			}
		}
		group.copy(g);
		Gdx.app.postRunnable(new Runnable(){
			@Override
			public void run() {
				AdminScreen main = (AdminScreen)ENGINE.getGameScreen();
				main.doGroupFix();
			}
		});
	}
}
 
