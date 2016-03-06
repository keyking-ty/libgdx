package com.keyking.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.util.DataBuffer;

public class DataManager {
	
	Logger logger = null;
	
	List<UserEntity> users = new ArrayList<UserEntity>();
	
	DBManager dbManager;
	
	List<GroupEntity> groups = new ArrayList<GroupEntity>();
	
	public static DataManager instance = null;
	
	public static DataManager getInstance(){
		if (instance == null){
			instance = new DataManager();
		}
		return instance;
	}
	
	public DataManager(){
		logger = LoggerFactory.getLogger("log");
		dbManager = new DBManager();
		dbManager.init();
	}
	
	public List<UserEntity> getUsers() {
		return users;
	}

	public List<GroupEntity> getGroups() {
		return groups;
	}

	public Logger getLogger() {
		return logger;
	}

	public void sendAll(DataBuffer buffer){
		buffer.putInt(groups.size());
		for (GroupEntity group : groups){
			group._serialize(buffer);
		}
		buffer.putInt(users.size());
		for (UserEntity user : users){
			user._serialize(buffer);
		}
	}
	
	public void load() throws Exception{
		List<UserEntity> users = dbManager.getUserDao().load();
		if (users != null){
			this.users = users;
		}
		List<GroupEntity> groups = dbManager.getGroupDao().load();
		if (groups != null && groups.size() > 0){
			this.groups = groups;
		}
		for (UserEntity user : users){
			user.init(this);
		}
		for (GroupEntity group : groups){
			group.init(this);
		}
	}
	
	/**
	 * 批量添加客户信息
	 * @param tels
	 * @return
	 */
	public boolean insertCustomer(List<TelInfoEntity> tels){
		for (TelInfoEntity tel : tels){
			dbManager.getTelInfoDao().insert(tel);
		}
		return true;
	}
	
	/**
	 * 用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public UserEntity login(String username , String password){
		for (UserEntity user : users){
			if (user.getUsername().equals(username) && user.getPassword().equals(password)){
				return user;
			}
		}
		UserEntity user = dbManager.getUserDao().login(username,password);
		if (user != null){
			insert(user);
		}
		return user;
	}
	
	public boolean registUser(UserEntity user){
		long uid = dbManager.getUserDao().insert(user);
		if (uid > 0){
			insert(user);
			return true;
		}
		return false;
	}
	
	public boolean delUser(UserEntity... entitys){
		if (dbManager.getUserDao().Del(entitys)){
			for (UserEntity user :entitys){
				users.remove(user.getUsername());
			}
			return true;
		}
		return false;
	}
	
	public String fixPassword(String username , String oldPassword , String newPassword){
		for (UserEntity user : users){
			if (user.getUsername().equals(username)){
				if (user.getPassword().equals(oldPassword)){
					user.setPassword(newPassword);
					if (save(user)){
						return "true";
					}
				}else{
					return "旧密码错误";
				}
			}
		}
		return "找不到账号：" + username;
	}
	
	public boolean save(UserEntity user){
		return dbManager.getUserDao().save(user);
	}
	
	private void insert(UserEntity user){
		if (user == null){
			return;
		}
		users.add(user);
	}
	
	public UserEntity searchUser(long id){
		for (UserEntity user : users){
			if (user.getId() == id){
				return user;
			}
		}
		return null;
	}

	/***
	 * 部门部分
	 * @param id
	 * @return
	 */
	public GroupEntity searchGroup(int id){
		for (GroupEntity group : groups){
			if (group.getId() == id){
				return group;
			}
		}
		return null;
	}
	
	public boolean registGroup(GroupEntity group){
		if (dbManager.getGroupDao().insert(group)){
			if (!groups.contains(group)){
				groups.add(group);
			}
			return true;
		}
		return false;
	}
	
	public void save(GroupEntity group){
		dbManager.getGroupDao().save(group);
	}
	
	public boolean delGroup(GroupEntity... entitys){
		if (dbManager.getGroupDao().Del(entitys)){
			Map<GroupEntity,List<GroupEntity>> fatherandsun = new HashMap<GroupEntity,List<GroupEntity>>();
			List<UserEntity> temps = new ArrayList<UserEntity>();
			for (GroupEntity group : entitys){
				for (UserEntity user : users){
					GroupEntity father = user.getFather();
					if (father != null && group.equals(father)){
						temps.add(user);
					}
				}
				GroupEntity father = group.getFather();
				if (father != null){
					List<GroupEntity> suns = fatherandsun.get(father);
					if (suns == null){
						suns = new ArrayList<GroupEntity>();
						fatherandsun.put(father,suns);
					}
					suns.add(group);
				}
			}
			for (GroupEntity group : fatherandsun.keySet()){
				GroupEntity father = findNotDel(group,entitys);
				List<GroupEntity> suns = fatherandsun.get(group);
				for (GroupEntity sun : suns){
					sun.setFather(father);
				}
			}
			for (GroupEntity group : entitys){
				groups.remove(group);
			}
			for (GroupEntity group : groups){
				dbManager.getGroupDao().save(group);
			}
			for (UserEntity user : temps){
				GroupEntity father = findNotDel(user.getFather(),entitys);
				user.setFather(father);
				dbManager.getUserDao().save(user);
			}
			return true;
		}
		return false;
	}
	
	public GroupEntity findNotDel(GroupEntity group , GroupEntity... entitys){
		GroupEntity father = group;
		while (father != null){
			for (GroupEntity g : entitys){
				if (father.equals(g)){
					father = father.getFather();
					break;
				}
			}
		}
		return null;
	}
	
	public List<TelInfoEntity> loadUserTel(UserEntity user){
		return dbManager.getTelInfoDao().loadLogin(user);
	}
	
	public List<TelInfoEntity> loadNotUserTel(UserEntity user){
		int task = user.getTask();
		if (user.getFather() != null){
			user.getFather().getTask();
			task = Math.max(task,user.getFather().getTask());
		}
		return dbManager.getTelInfoDao().loadDown(user,task);
	}
	
	public void save(TelInfoEntity tel){
		dbManager.getTelInfoDao().save(tel);
	}
}
 
 
