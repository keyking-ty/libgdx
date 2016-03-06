package com.keyking.frame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.keyking.actor.LoadingActor;



public class StartScreen extends GameScreen {
	
	int precess = 0;
	
	Label showLabel;
	LoadingActor load = null;
	@Override
	public boolean init() {
		showLogo(S_WIDTH/2,S_HEIGHT/2);
		load = new LoadingActor();
		load.setPosition(S_WIDTH/2,S_HEIGHT/2);
		stage.addActor(load);
		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont();
		style.fontColor = new Color(0,0,0,1);
		if (showLabel == null){
			showLabel = new Label(precess + "%" ,style);
			showLabel.setPosition(S_WIDTH / 2 - 10 , S_HEIGHT / 2);
			stage.addActor(showLabel);
		}
		return true;
	}
	
	@Override
	public void logic(float f) {
		switch(precess) {
		case 50:
			SKIN.init();
			break;
		case 99:
			ENGINE.notifyEvent(EVENT.create_evt_Next(1,new LoginScreen()));
			break;
		}
		if (precess < 100){
			showLabel.setText(precess + "%");
			precess++;
		}
		clear(1,1,1,0.2f);
	}
	
	@Override
	public boolean onBackPressed(){
		return false;
	}
}
 
 
