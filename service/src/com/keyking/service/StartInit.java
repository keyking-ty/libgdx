package com.keyking.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.keyking.service.dao.DataManager;
import com.keyking.service.util.SystemLog;

public class StartInit implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			DataManager.getInstance().save();
		} catch (Exception e) {
			SystemLog.error("�ر��쳣",e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			String path = arg0.getServletContext().getRealPath("");
			SystemLog.start(path);
			DataManager.getInstance().init();
		} catch (Exception e) {
			SystemLog.error("����һ��",e);
		}
	}  
}
 
 
 