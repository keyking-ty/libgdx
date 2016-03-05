package com.keyking.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.keyking.EngineControler;
import com.keyking.frame.GameScreen;
import com.keyking.util.Instances;


public class UIFactory implements Instances{
	
	private static UIFactory instance = new UIFactory();
	
	public static UIFactory getInstance(){
		return instance;
	}
	
	public Label createLabel(String str){
		LabelStyle style = SKIN.getUISkin().get("default",LabelStyle.class);
		return new Label(str,style);
	}

	public TextButton createTextButton(String str){
		return new TextButton(str,SKIN.getUISkin(),"toggle");
	}
	
	public TextButton createTextButton(String str,String stye){
		return new TextButton(str,SKIN.getUISkin(),stye);
	}
	
	public TextField createTextField(int width ,int height){
		TextField textfield = new TextField("", SKIN.getUISkin());
		textfield.setWidth(width);
		textfield.setHeight(height);
		if (EngineControler.plat != EngineControler.PLAT_WIN32){
			textfield.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent inputevent, float f, float f1) {
					ENGINE.setKeyboard(true,(TextField)inputevent.getListenerActor());
				}
			});
			textfield.setTextFieldListener(new TextFieldListener() {
				@Override
				public void keyTyped(TextField textfield, char c) {
					GameScreen.keyButh.update(textfield);
				}
			});
		}
		return textfield;
	}
	
	public TextField createTextField(String text , Skin skin){
		TextField textfield = new TextField(text, skin);
		if (EngineControler.plat != EngineControler.PLAT_WIN32){
			textfield.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent inputevent, float f, float f1) {
					ENGINE.setKeyboard(true,(TextField)inputevent.getListenerActor());
				}
			});
			textfield.setTextFieldListener(new TextFieldListener() {
				@Override
				public void keyTyped(TextField textfield, char c) {
					GameScreen.keyButh.update(textfield);
				}
			});
		}
		return textfield;
	}
	
	public Label createLabel(String str,String color){
		LabelStyle style = new LabelStyle();
		style.font = SKIN.getDefaultFont();
		style.fontColor = SKIN.getUISkin().getColor(color);
		return new Label(str,style);
	}
}
 
