package com.keyking.util;

import java.util.Random;

public class MathUtil {
	
	private static MathUtil instance = new MathUtil();
	
	Random ramdom = new Random();
	
	public static MathUtil getInstance(){
		return instance ;
	}
	
	public int random(int len){
		return ramdom.nextInt(len);
	}
	
	public int random(int min ,int max){
		if (min == max){
			return min;
		}
		return ramdom.nextInt(max - min + 1) + min;
	}
}
 
