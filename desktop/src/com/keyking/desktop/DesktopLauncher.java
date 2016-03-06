package com.keyking.desktop;

import com.badlogic.gdx.Files.FileType;
import com.keyking.application.LwjglApplication;
import com.keyking.application.LwjglApplicationConfiguration;
import com.keyking.util.Instances;

public class DesktopLauncher implements Instances{
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ÐÂÍþÍ¶×Ê";
		//config.fullscreen = true;
		config.addIcon("title.jpg",FileType.Internal);
		//config.width = 1280;
		new LwjglApplication(ENGINE,config);
	}
}
 
 
 
