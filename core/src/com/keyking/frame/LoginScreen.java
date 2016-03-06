package com.keyking.frame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.keyking.actor.BackBoard;
import com.keyking.util.SkinFactory;

public class LoginScreen extends GameScreen {
	TextField userField = null;
	TextField passwardField = null;
	TextField tokenField = null;
	TextButton showToken= null;
	String tokenStr;
	TextButton login= null;
	BackBoard board = null;
	
	AddListener nextDo = new AddListener();
	
	public static final char[] TOKENS = { 
		'a','b','c','d','e','f','g','h','i','j',
		'k','l','m','n','o','p','q','r','s','t',
		'u','v','w','x','y','z','0','1','2','3',
		'4','5','6','7','8','9'};
	
	@Override
	public boolean init() {
		randomToken();
		showLogo(S_WIDTH/2,S_HEIGHT/2);
		userField = UI.createTextField(SkinFactory.FONT_SIZE * 10,SkinFactory.FONT_SIZE + 10);
		passwardField = UI.createTextField(SkinFactory.FONT_SIZE * 10,SkinFactory.FONT_SIZE + 10);
		passwardField.setPasswordCharacter("*".charAt(0));
		passwardField.setPasswordMode(true);
//		tokenField = UI.createTextField(SkinFactory.FONT_SIZE * 5,SkinFactory.FONT_SIZE + 10);
//		showToken = UI.createTextButton(tokenStr);
//		showToken.addListener(new ClickListener(){
//			public void clicked(InputEvent event,float x,float y){
//				randomToken();
//				showToken.setText(tokenStr);
//				showToken.setSize(showToken.getPrefWidth(),showToken.getPrefHeight());
//			}
//		});
		login = UI.createTextButton("登录");
		login.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				login();
			}
		});
		board = new BackBoard(S_WIDTH-16,200,new Color(0.1f,0.2f,1,0.5f),true);
		stage.addActor(board);
		refresh(8,S_HEIGHT/4);
		return true;
	}
	
	public void refresh(float x, float y){
		float width = board.getWidth();
		float height = board.getHeight();
		float starty = height - 75;
		board.setPosition(x,y);
		Label label = UI.createLabel("用户登录");
		label.setPosition(width/2-label.getWidth()/2,height-30);
		board.addActor(label);
		TextButton user = UI.createTextButton("账号:");
		user.setTouchable(Touchable.disabled);
		float startx = (width - user.getWidth() - userField.getWidth())/2;
		user.setPosition(startx,starty);
		board.addActor(user);
		userField.setPosition(startx + user.getWidth() + 5,starty);
		board.addActor(userField);
		
		float space = userField.getHeight() + 10;
		TextButton passward = UI.createTextButton("密码:");
		passward.setTouchable(Touchable.disabled);
		starty -= space;
		passward.setPosition(startx,starty);
		board.addActor(passward);
		passwardField.setPosition(startx + passward.getWidth() + 5,starty);
		board.addActor(passwardField);
		//TextButton check = UI.createTextButton("验证:");
		//check.setTouchable(Touchable.disabled);
		//starty -= space;
		//check.setPosition(startx,starty);
		//board.addActor(check);
		//tokenField.setPosition(startx + check.getWidth() + 5,starty);
		//board.addActor(tokenField);
		//showToken.setPosition(tokenField.getX() + tokenField.getWidth() + 5,starty);
		//board.addActor(showToken);
		starty -= 50;
		login.setPosition(width/2 - login.getWidth() / 2,starty);
		board.addActor(login);
	}
	
	public boolean check(){
		String username = userField.getText();
		String passward = passwardField.getText();
		//String token = tokenField.getText();
		if (username == null || username.equals("")){
			message("账号不能为空",nextDo);
			return false;
		}
		if (passward == null || passward.equals("")){
			message("密码不能为空",nextDo);
			return false;
		}
//		if (token == null || token.equals("")){
//			message("验证码不能为空",nextDo);
//			return false;
//		}
//		if (!token.equals(tokenStr)){
//			message("验证码错误",nextDo);
//			return false;
//		}
		return true;
	}
	
	public boolean login(){
		if (!check()){
			return false;
		}
		NET.login(userField.getText(),passwardField.getText());
		return true;
	}
	
	@Override
	public void logic(float f) {
		clear(1,1,1,1);
	}
	
	public void randomToken(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; i < 4 ; i++){
			int index = MATH.random(TOKENS.length);
			sb.append(TOKENS[index]);
		}
		tokenStr = sb.toString();
	}
	
	public class AddListener implements DoAfter{
		@Override
		public void next() {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run(){
					stage.addListener(enter);
				}
			});
		}
	}
	
	@Override
	public boolean _enter(){
		if (!check()){
			stage.removeListener(enter);
			return false;
		}
		return login();
	}

}
 
 
