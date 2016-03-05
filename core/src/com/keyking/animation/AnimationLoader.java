package com.keyking.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

public class AnimationLoader {
	
	private static AnimationLoader instance = new AnimationLoader();

	ObjectMap<String,SkeletonData> skeletonDatas = new ObjectMap<String, SkeletonData>();
	
	public static AnimationLoader getInstance(){
		return instance;
	}
	
	public SkeletonData load(String name,AnimationView view,float scale){
		SkeletonData data = skeletonDatas.get(name);
		if (data == null){
			view.setNeedSet(true);
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skeleton/" + name + ".atlas"));
			SkeletonJson json  = new SkeletonJson(atlas);
			json.setScale(scale);
			data = json.readSkeletonData(Gdx.files.internal("skeleton/" + name + ".json"));
			skeletonDatas.put(name,data);
		}
		return data;
	}
	
	public void destory(String... strs){
		if (strs == null){
			skeletonDatas.clear();
		}else{
			for (String key : strs){
				skeletonDatas.remove(key);
			}
		}
	}
}
 
