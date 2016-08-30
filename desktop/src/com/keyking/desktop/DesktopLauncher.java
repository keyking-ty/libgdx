package com.keyking.desktop;

import com.badlogic.gdx.Files.FileType;
import com.keyking.application.LwjglApplication;
import com.keyking.application.LwjglApplicationConfiguration;
import com.keyking.util.Instances;

public class DesktopLauncher implements Instances{
	
	public static void main (String[] arg) {
		NET.init();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = NET.getTitle();
		config.addIcon("title.jpg",FileType.Internal);
		new LwjglApplication(ENGINE,config);
	}
}
 
 
 
