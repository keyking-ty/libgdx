package com.keyking.animation;

import com.badlogic.gdx.utils.ObjectFloatMap;
import com.esotericsoftware.spine.Animation;

public class StateView {
	private Animation animation;
	private boolean loop;
	private ObjectFloatMap<Animation> startTimes = new ObjectFloatMap<Animation>();
	private float defaultStartTime;
	
	public Animation getAnimation() {
		return animation;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public boolean isLoop() {
		return loop;
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public ObjectFloatMap<Animation> getStartTimes() {
		return startTimes;
	}
	
	public void setStartTimes(ObjectFloatMap<Animation> startTimes) {
		this.startTimes = startTimes;
	}
	
	public float getDefaultStartTime() {
		return defaultStartTime;
	}
	
	public void setDefaultStartTime(float defaultStartTime) {
		this.defaultStartTime = defaultStartTime;
	}
}
 
