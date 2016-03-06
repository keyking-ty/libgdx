package com.keyking.event.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.keyking.EngineControler;
import com.keyking.event.Event;
import com.keyking.event.EventListener;
import com.keyking.frame.GameScreen;

public class ExitEntity implements EventListener {
	@Override
	public Object handleEvent(Event event) {
		if (event.getCode() == Event.EVT_EXIT_CODE){
			Dialog exit = new Dialog("退出",SKIN.getUISkin()){
				protected void result (Object object) {
					if (object.equals(true)){
						if (EngineControler.plat == EngineControler.PLAT_WIN32){
							Gdx.app.exit();
						}else{
							System.exit(0);
						}
					}
					GameScreen.isExitOpen = false;
				}
			};
			Table table = exit.getButtonTable();
			Label label = UI.createLabel("          确定退出系统?","red");
			table.add(label).center().row();
			TextButton sure = UI.createTextButton("确定");
			table.add(sure).left();
			exit.setObject(sure,true);
			TextButton canle = UI.createTextButton("返回");
			table.add(canle).right();
			exit.setObject(canle,false);
			Stage stage = ENGINE.getGameScreen().getStage();
			exit.show(stage);
		}
		return null;
	}

}
 
 
