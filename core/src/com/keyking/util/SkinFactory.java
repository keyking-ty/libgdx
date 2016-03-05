package com.keyking.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinFactory implements Instances{
	
	private static SkinFactory instance;
	
	Skin uiSkin = new Skin();//UIskin
	
	FreeTypeFontGenerator generator;
	
	FreeTypeFontParameter parameter;
	
	public static final int FONT_SIZE = 35;
	
	public static SkinFactory getInstance(){
		if (instance == null){
			instance = new SkinFactory();
		}
		return instance;
	}
	
	public void init(){
		generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/default.fnt"));
		parameter = new FreeTypeFontParameter();
		parameter.size = FONT_SIZE;	
		parameter.characters = FONT.getCharacters();
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		uiSkin.add("default-font",font);
		FileHandle file = Gdx.files.internal("skin/uiskin.json");
		FileHandle atlasFile = file.sibling(file.nameWithoutExtension() + ".atlas");
		if (atlasFile.exists()) {
			TextureAtlas atlas = new TextureAtlas(atlasFile);
			uiSkin.addRegions(atlas);
		}
		uiSkin.load(file);
	}

	public Skin getUISkin() {
		return uiSkin;
	}
	
	public BitmapFont getDefaultFont(){
		BitmapFont font = null;
		try {
			font = uiSkin.getFont("default-font");
		} catch (Exception e) {
		}
		return font;
	}
}
 
