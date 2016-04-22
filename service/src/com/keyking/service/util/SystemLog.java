package com.keyking.service.util;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keyking.service.dao.DataManager;

/**
 * ��Ϸ��־
 */
public class SystemLog {
	
	private static Logger logger = null;
	
	public static void start(String path){
		int index = path.indexOf("webapps") + 8;
		path = path.substring(0,index);
		String key = "9106";
		System.setProperty("oss.log4j.path.contact_" + key,path);
		PropertyConfigurator.configureAndWatch(path + "contact-service/conf/log4j_" + key + ".properties");
		logger = LoggerFactory.getLogger(SystemLog.class);
		DataManager.getInstance().init(key);
	}
	
	public static void error(String str, Throwable throwable) {
		logger.error(str, throwable);
	}	
	
	public static void error(String str) {
		logger.error(str);
	}
		
	
	public static void info(String info) {
		logger.info(info);
	}
	
	public static void debug(String str) {
		logger.debug(str);
	}
	
		
}
 
 
 
