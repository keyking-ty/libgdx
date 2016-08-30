package com.keyking.service.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keyking.service.dao.DataManager;

/**
 * сно╥хуж╬
 */
public class SystemLog {
	
	private static Logger logger = null;
	
	public static void start(String path) throws Exception{
		Properties prop = new Properties();
		int index = path.indexOf("webapps") + 8;
		path = path.substring(0,index);
		prop.load(new FileInputStream(path + "contact-service/conf/conf.properties"));
		String key = prop.getProperty("file");
		System.setProperty(key,path);
		PropertyConfigurator.configureAndWatch(path + "contact-service/conf/log4j.properties");
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
 
 
 
