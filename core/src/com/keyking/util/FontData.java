package com.keyking.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class FontData {
	
	private static FontData instance = new FontData();
	
	public static FontData getInstance(){
		return instance;
	}
	
	public String getCharacters(){
		StringBuffer sb = new StringBuffer();
		XmlReader xml = new XmlReader();
		Element element = null;
		try {
			FileHandle file = Gdx.files.internal("skin/fonts.xml");
			Reader reader = new InputStreamReader(file.read(),"UTF-8");
			element = xml.parse(reader);
			Array<Element> elements = element.getChildrenByName("string");
			for (Element e : elements){
				sb.append(e.getText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
 
