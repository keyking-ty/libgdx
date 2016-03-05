package com.keyking.animation;

import com.badlogic.gdx.utils.ObjectMap;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.keyking.util.Instances;

public class AnimationView implements Instances{
	
	AnimationState animationState;
	
	SkeletonData skeletonData;
	
	Skeleton skeleton;
	
	boolean needSet = false;//–Ë“™…Ë÷√
	
	ObjectMap<String, StateView> states = new ObjectMap<String,StateView>();
	
	public AnimationView(String name,float scale){
		skeletonData = ANI.load(name,this,scale);
		skeleton     = new Skeleton(skeletonData);
		AnimationStateData data = new AnimationStateData(skeletonData);
		data.setDefaultMix(0.2f);
		animationState = new AnimationState(data);
	}
	
	public boolean isNeedSet() {
		return needSet;
	}

	public void setNeedSet(boolean needSet) {
		this.needSet = needSet;
	}

	public void setMix (String from, String to,float mix) {
		Animation fromAnimation = skeletonData.findAnimation(from);
		Animation toAnimation = skeletonData.findAnimation(to);
		if (fromAnimation == null || toAnimation == null){
			return;
		}
		animationState.getData().setMix(fromAnimation,toAnimation, mix);
	}
	
	public boolean setAnimation (String name , boolean force) {
		StateView state = states.get(name);
		Animation animation = state.getAnimation();
		TrackEntry current = animationState.getCurrent(0);
		Animation oldAnimation = current == null ? null : current.getAnimation();
		if (force || oldAnimation != animation) {
			if (state.getAnimation() == null) {
				return true;
			}	
			TrackEntry entry = animationState.setAnimation(0,state.getAnimation(),state.isLoop());
			if (oldAnimation != null) {
				float time = state.getStartTimes().get(oldAnimation,state.getDefaultStartTime());
				entry.setTime(time);
			}
			if (!state.isLoop()){
				entry.setEndTime(9999);
			}
			return true;
		}
		return false;
	}
	
	public StateView setupState (String name,boolean loop) {
		Animation animation = skeletonData.findAnimation(name);
		if (animation == null){
			return null;
		}
		StateView stateView = new StateView();
		stateView.setAnimation(animation);
		stateView.setLoop(loop);
		states.put(name,stateView);
		return stateView;
	}

	public Skeleton getSkeleton(){
		return skeleton;
	}
	
	public void act(float delta,float x,float y) {
		skeleton.setX(x);
		skeleton.setY(y);
		skeleton.updateWorldTransform();
		animationState.update(delta);
		animationState.apply(skeleton);
	}
}
 
