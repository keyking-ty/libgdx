package com.keyking.service.dao;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.keyking.service.dao.impl.GroupDAO;
import com.keyking.service.dao.impl.TelInfoDAO;
import com.keyking.service.dao.impl.UserDAO;


public class DBManager {
	
	ApplicationContext context = null;
	
	UserDAO userDao = null;
	
	GroupDAO groupDao = null;
	
	TelInfoDAO telInfoDao = null;
	
	public DBManager() {
		connect();
	}
	

	
	public void connect(){
		//context  = new FileSystemXmlApplicationContext(path + "conf/userDB.xml");
		context    = new ClassPathXmlApplicationContext("userDB.xml");
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



	public void stop() {
		BasicDataSource base = (BasicDataSource)context.getBean("CONTACT_DS");
		if (base != null){
			try {
				base.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
 
 
 
