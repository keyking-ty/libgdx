package com.keyking.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.keyking.animation.AnimationView;
import com.keyking.util.Instances;

public class LoadingActor extends Actor implements Instances{
	
	AnimationView aniView;
	
	public LoadingActor(){
		aniView = new AnimationView("waiting",1f);
		aniView.setupState("animation",true);
		aniView.setAnimation("animation",true);
	}

	@Override
	public void act(float delta) {
		aniView.act(delta,getX(),getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		ENGINE.getSkeletonRenderer().draw(batch,aniView.getSkeleton());
	}
	
	
}
 
