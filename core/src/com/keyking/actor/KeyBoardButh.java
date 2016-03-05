package com.keyking.actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.keyking.util.Instances;

public class KeyBoardButh extends Table implements Instances{
	
	Label label;
	
	public KeyBoardButh(int w,int h){
		Pixmap pixmap = new Pixmap(w,h,Format.RGBA8888);
		pixmap.setColor(0.5F,0.5F,0.5F,0.9F);
		pixmap.fill();
		Texture text = new Texture(pixmap);
		Image image = new Image(text);
		image.setPosition(0,0);
		label = UI.createLabel("","black");
		label.setPosition(0,h-25);
		addActor(image);
		addActor(label);
	}
	
	public void update(TextField link){
		label.setText(link.getText());
	}
	
	public void setText(String str){
		label.setText(str);
	}
}
 
