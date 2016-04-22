package com.keyking.service.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.keyking.service.dao.impl.GroupDAO;
import com.keyking.service.dao.impl.TelInfoDAO;
import com.keyking.service.dao.impl.UserDAO;


public class DBManager {
	
	//ApplicationContext context = null;
	
	UserDAO userDao = null;
	
	GroupDAO groupDao = null;
	
	TelInfoDAO telInfoDao = null;
	
	public void connect(String fileName){
		ApplicationContext context    = new ClassPathXmlApplicationContext(fileName);
		userDao    = (UserDAO) context.getBean("userDao");
		groupDao   = (GroupDAO)context.getBean("groupDao");
		telInfoDao = (TelInfoDAO)context.getBean("telInfoDao");
	}
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public GroupDAO getGroupDao() {
		return groupDao;
	}

	public TelInfoDAO getTelInfoDao() {
		return telInfoDao;
	}
}
 
 
 
 
