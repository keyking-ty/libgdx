package com.keyking.frame;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.keyking.EngineControler;
import com.keyking.actor.KeyBoardButh;
import com.keyking.actor.LoadingActor;
import com.keyking.util.Instances;

public abstract class GameScreen implements Screen ,Instances{
	
	Stage stage;//平台
	
	boolean isBackPressed = false;
	
	static LoadingActor loader = null;
	
	static Image logo = null;
	
	Dialog loadDialog = null;
	
	public static int S_WIDTH  = 640;
	
	public static int S_HEIGHT = 480;

	EnterClick enter = new EnterClick();
	
	public static KeyBoardButh keyButh = null;
	
	public static boolean isExitOpen = false;
	
	public GameScreen(){
		if (EngineControler.plat == EngineControler.PLAT_ANDROID){
			S_WIDTH = 800;
		}
		Viewport  view = new ScalingViewport(Scaling.stretch,S_WIDTH,S_HEIGHT, new OrthographicCamera());
		stage = new Stage(view, new SpriteBatch());
		stage.addCaptureListener(new InputListener(){
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				int keyCode = event.getKeyCode();
				if (EngineControler.plat == EngineControler.PLAT_WIN32 && keyCode == Input.Keys.ESCAPE){
					return onBackPressed();
				}else if (EngineControler.plat == EngineControler.PLAT_WIN32 && keyCode == Input.Keys.F5){
					return refresh();
				}
				return false;
			}
		});
		if (loader == null){
			loader = new LoadingActor();
		}
		if (logo == null){
			Texture texture = new Texture(Gdx.files.internal("logo.jpg"));
			logo = new Image(texture);
			float w = logo.getWidth() * logo.getScaleX() / 2;
			float h = logo.getHeight() * logo.getScaleY() / 2;
			logo.setPosition(Gdx.graphics.getWidth()/2-w,Gdx.graphics.getHeight()/2-h);
		}
		stage.addListener(enter);
	}
	
	public abstract boolean init();
	
	@Override
	public void render(float f){
		logic(f);
		ENGINE.tick();
		stage.act(f);
		stage.draw();
	}
	
	public abstract void logic(float f);
	
	public void clear(float r,float g,float b, float a){
		Gdx.gl.glClearColor(r,g,b,a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void resize(int i,int j) {
		
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		Gdx.app.log("INFO","do hide");
	}
	
	@Override
	public void pause() {
		Gdx.app.log("INFO","do pause");
		//mainStage.unfocusAll();
	}

	@Override
	public void resume() {
		Gdx.app.log("INFO","do resume");
	}

	@Override
	public void dispose() {
		stage.dispose();
		Gdx.app.log("INFO","do dispose");
	}
	
	public Stage getStage() {
		return stage;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findActor(String name){
		Actor actor = stage.getRoot().findActor(name);
		return (T)actor;
	}
	
	public boolean Exit(int delay){
		List<Object> objects = ENGINE.notifyEvent(EVENT.create_evt_exit(delay));
		if (objects != null){
			return objects.size() > 0;
		}
		return true;
	}
	
	public void showLoading(boolean flag){
		if (flag){
			loadDialog = new Dialog("联网中…",SKIN.getUISkin());
			Table table = loadDialog.getContentTable();
			table.add(loader).pad(40);
			table.add(UI.createLabel("请稍候","white")).left().row();
			loadDialog.show(stage);
		}else if (loadDialog != null){
			loadDialog.hide();
			loadDialog = null;
		}
	}
	
	public void showLogo(float x , float y){
		if (logo.getParent() != null && logo.getStage() == stage){
			logo.remove();
		}
		float w = logo.getWidth() * logo.getScaleX()/2;
		float h = logo.getHeight() * logo.getScaleY()/2;
		logo.setPosition(x-w,y-h);
		stage.addActor(logo);
	}
	
	public boolean onBackPressed(){
		if (ENGINE.isKeyboard() && EngineControler.plat != EngineControler.PLAT_WIN32){
			Gdx.input.setOnscreenKeyboardVisible(false);
			ENGINE.setKeyboard(false,null);
			return true;
		}
		if (isExitOpen){
			return true;
		}
		isExitOpen = true;
		return Exit(0);
	}
	
	public void message(String msg){
		Label label = UI.createLabel(msg,"red");
		TipDialog tip = new TipDialog(null);
		tip.text(label).button("确定",true).show(stage);
	}
	
	public void message(String msg,DoAfter logic){
		Label label = UI.createLabel(msg,"red");
		TipDialog tip = new TipDialog(logic);
		tip.text(label).button("确定",true).key(Keys.ENTER,true).show(stage);
	}
	
	public class NumFieldFieldFilter implements TextFieldFilter{
		@Override
		public boolean acceptChar(TextField textField, char c) {
			if (c < '0' || c > '9'){
				return false;
			}
			return true;
		}
	}
	
	public interface DoAfter{
		public void next();
	}
	
	public class TipDialog extends Dialog{
		DoAfter logic = null;
		public TipDialog(DoAfter logic) {
			super("message",SKIN.getUISkin());
			this.logic = logic;
		}
		@Override
		protected void result(Object object){
			if (logic != null){
				logic.next();
			}
		}
	}
	
	public boolean _enter(){
		return false;
	}
	
	public class EnterClick extends InputListener{
		@Override
		public boolean keyDown(InputEvent event,int keycode) {
			if (keycode == Keys.ENTER){
				if (ENGINE.isKeyboard()){
					Gdx.input.setOnscreenKeyboardVisible(false);
					ENGINE.setKeyboard(false,null);
					return true;
				}
				return _enter();
			}
			return false;
		}
	}

	public void showKeyBoardButh(String str) {
		if (keyButh == null){
			keyButh = new KeyBoardButh(S_WIDTH,S_HEIGHT);
			keyButh.setPosition(0,0);
		}
		if (keyButh.getStage() != null){
			if (keyButh.getStage() == stage){
				return;
			}
			keyButh.remove();
		}
		stage.addActor(keyButh);
	}
	
	public void hideKeyBoardButh(){
		if (keyButh.getStage() != null){
			keyButh.remove();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T>T searchFromTable(Table table , int code){
		@SuppressWarnings("rawtypes")
		Array<Cell> cells = table.getCells();
		for (Cell<?> cell : cells){
			Actor actor = cell.getActor();
			String name = actor.getName();
			if (name != null && !name.equals("")){
				int ac = Integer.parseInt(name);
				if (ac == code){
					return (T)actor;
				}
			}
		}
		return null;
	}
	
	public boolean refresh(){
		return false;
	}
}
 
 
