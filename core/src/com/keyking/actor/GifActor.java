package com.keyking.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.keyking.util.GifDecoder;

public class GifActor extends Actor {
	
	private float stateTime;
	
    protected Animation animation;
    
    
    public GifActor(PlayMode type , String name){
    	stateTime = 0;
    	animation = GifDecoder.loadGIFAnimation(type,Gdx.files.internal(name).read());
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	Color color = getColor();
		batch.setColor(color.r,color.g,color.b,color.a * parentAlpha);
    	stateTime += Gdx.graphics.getDeltaTime();
    	if (animation != null){
        	TextureRegion textureRegion = animation.getKeyFrame(stateTime);
        	float w = textureRegion.getRegionWidth();
        	float h = textureRegion.getRegionHeight();
        	float x = getX() - w / 2;
        	float y = getY() - h / 2;
            batch.draw(textureRegion,x,y,0,0,w,h,getScaleX(),getScaleY(),getRotation());
    	}
    }
    
    public static void main(String[] args) {
    	GifActor logo = new GifActor(PlayMode.LOOP,"logo.gif");
    	logo.setPosition(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2);
	}
}
 
