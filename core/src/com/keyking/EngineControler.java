package com.keyking;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.keyking.android.AndroidLogicBody;
import com.keyking.event.Event;
import com.keyking.event.EventDispatcher;
import com.keyking.event.EventListener;
import com.keyking.event.entity.ExitEntity;
import com.keyking.event.entity.NextEntity;
import com.keyking.frame.GameScreen;
import com.keyking.frame.StartScreen;
import com.keyking.util.Instances;

public class EngineControler extends Game implements Instances{

	static EngineControler instance = new EngineControler();
	
	SkeletonRenderer skeletonRenderer;
	
	EventDispatcher dispatcher = new EventDispatcher();
	
	ExitEntity exit = new ExitEntity();
	
	NextEntity next = new NextEntity();
	
	AndroidLogicBody android;
	
	public static int plat = 0; //
	
	public static final int PLAT_WIN32 = 0;
	
	public static final int PLAT_ANDROID = 1;
	
	public static final int PLAT_IOS = 2;

	boolean keyboard = false;
	
	Random random = new Random();
	
	public static EngineControler getInstance() {
		return instance;
	}

	@Override
	public void create() {
		skeletonRenderer = new SkeletonRenderer();
		setState(new StartScreen());
		registEventListener(exit,Event.EVT_EXIT_CODE);
		registEventListener(next,Event.EVT_NEXT_ACTION);
		NET.init();
	}

	public void setState(GameScreen game) {
		if (game != null && game.init()){
			//if (plat == PLAT_WIN32){
				//DisplayMode desktopDisplayMode = Gdx.graphics.getDesktopDisplayMode();
				//Gdx.graphics.setDisplayMode(desktopDisplayMode.width,desktopDisplayMode.height,false);
			//}
			Gdx.input.setOnscreenKeyboardVisible(false);
			Gdx.input.setInputProcessor(game.getStage());
			setScreen(game);
		}
	}
	
	public GameScreen getGameScreen(){
		return transfer(getScreen());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T transfer(Object obj) {
		return (T) obj;
	}

	public SkeletonRenderer getSkeletonRenderer() {
		return skeletonRenderer;
	}
	
	public final void registEventListener(EventListener listener,int event) {
		dispatcher.addListener(listener,event);
	}
	
	public final List<Object> notifyEvent(Event event){
		return dispatcher.raise(event);
	}

	public void tick() {
		dispatcher.tick();
	}
	
	public int random(int min ,int max) {
		if (min == max){
			return min;
		}
		return random.nextInt(max - min + 1) + min;
	}
	
	public int random(int len) {
		return random.nextInt(len);
	}

	public void deleteContact() {
		if (android != null){
			if (android.delete()){
				getGameScreen().message("É¾³ý³É¹¦");
			}else{
				getGameScreen().message("É¾³ýÊ§°Ü");
			}
		}
	}
	
	public void insertContact(String infos) {
		if (android != null){
			android.inserts(infos);
		}
	}
	
	public void setAndroid(AndroidLogicBody android) {
		this.android = android;
	}

	public boolean isKeyboard() {
		return keyboard;
	}

	public void setKeyboard(boolean keyboard,TextField field) {
		this.keyboard = keyboard;
		if (plat != PLAT_WIN32){
			if (keyboard){
				getGameScreen().showKeyBoardButh(field.getText());
			}else {
				getGameScreen().hideKeyBoardButh();
			}
		}
	}
	
	public boolean onBackPressed(){
		return getGameScreen().onBackPressed();
	}
}
 
 
