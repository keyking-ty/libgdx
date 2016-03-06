package com.keyking.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BackBoard extends Group {

	static Texture line;

	static Texture peak;
	
	Texture buth = null;
	
	boolean showBoard = false;
	
	public BackBoard(int w,int h,Color color,boolean showBoard) {
		setWidth(w);
		setHeight(h);
		if (line == null) {
			line = new Texture(Gdx.files.internal("line.png"));
		}
		if (peak == null) {
			peak = new Texture(Gdx.files.internal("peak.png"));
		}
		Pixmap pixmap = new Pixmap(w-10,h,Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		buth = new Texture(pixmap);
		this.showBoard = showBoard;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r,color.g,color.b,color.a * parentAlpha);
		int wb  = buth.getWidth();
		int hb  = buth.getHeight();
		int ws  = peak.getWidth();
		int hs  = peak.getHeight();
		float sw = getWidth()*getScaleX();
		float sh = getHeight()*getScaleX();
		float w = (getWidth()-ws)*getScaleX();
		float h = (getHeight()-hs)*getScaleY();
		int wl = line.getWidth();
		int hl = line.getHeight();
		float x = getX();
		float y = getY() + sh;
		float i = 0;
		batch.draw(buth,x+10,y-hb*getScaleY()+10,0,0,wb*getScaleX(),hb*getScaleY(),1,1,0,0,0,wb,hb,false,false);
		if (showBoard){
			batch.draw(peak,x,y,0,0,ws,hs,1,1,0,0,0,ws,hs,false,false);
			batch.draw(peak,x+sw-ws/2,y,0,0,ws,hs,1,1,0,0,0,ws,hs,true,false);
			batch.draw(peak,x,y-sh,0,0,ws,hs,1,1,0,0,0,ws,hs,false,true);
			batch.draw(peak,x+sw-ws/2,y-sh,0,0,ws,hs,1,1,0,0,0,ws,hs,true,true);
			for (i = 0 ;i < h ; i += hl){
				batch.draw(line,x+ws/2-2,y-i-hs/2,0,0,wl,hl,1,1,0,0,0,wl,hl,false,false);
			}
			float des = i - h;
			if (des > 1){
				batch.draw(line,x+ws/2-2,y-i+hl-hs/2,0,0,wl,hl-des,1,1,0,0,0,wl,hl,false,false);
			}
			for (i = 0 ;i < w ; i += hl){
				batch.draw(line,x+i+hl+ws/2,y+hl-2,0,0,wl,hl,1,1,90,0,0,wl,hl,false,false);
			}
			des = i - w;
			if (des > 1){
				batch.draw(line,x+i+ws/2,y+hl-2,0,0,wl,hl-des,1,1,90,0,0,wl,hl,false,false);
			}
			float offset = ws+wl/2-2;
			for (i = 0 ;i < h ; i += hl){
				batch.draw(line,x+w+offset,y-i-hs/2,0,0,wl,hl,1,1,0,0,0,wl,hl,false,false);
			}
			des = i - h;
			if (des > 1){
				batch.draw(line,x+w+offset,y-i+hl-hs/2,0,0,wl,hl-des,1,1,0,0,0,wl,hl,false,false);
			}
			offset = hl/2-hs+2;
			for (i = 0 ;i < w ; i += hl){
				batch.draw(line,x+i+hl+ws/2,y-h+offset,0,0,wl,hl,1,1,90,0,0,wl,hl,false,false);
			}
			des = i - w;
			if (des > 1){
				batch.draw(line,x+i,y-h+offset,0,0,wl,hl-des,1,1,90,0,0,wl,hl,false,false);
			}
		}
		super.draw(batch,parentAlpha);
	}
	
	public void setBackColor(Color color){
		Pixmap pixmap = buth.getTextureData().consumePixmap();
		pixmap.setColor(color);
	}
}
 
 
