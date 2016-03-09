package com.keyking.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.keyking.service.dao.entity.GroupEntity;
import com.keyking.service.dao.entity.TelInfoEntity;
import com.keyking.service.dao.entity.UserEntity;
import com.keyking.service.dao.impl.GroupDAO;
import com.keyking.service.dao.impl.TelInfoDAO;
import com.keyking.service.dao.impl.UserDAO;
import com.keyking.service.util.DataBuffer;
import com.keyking.service.util.SystemLog;

public class DataManager {
	
	List<UserEntity> users = new ArrayList<UserEntity>();
	
	DBManager dbManager;
	
	List<GroupEntity> groups = new ArrayList<GroupEntity>();
	
	List<TelInfoEntity> tels = new ArrayList<TelInfoEntity>();
	
	List<TelInfoEntity> dels = new ArrayList<TelInfoEntity>();
	
	boolean isFirst = true;
	
	private static DataManager instance = new DataManager();
	
	public static DataManager getInstance(){
		return instance;
	}
	
	public void init(){
		isFirst = true;
		dbManager = new DBManager();
		clear();
		load();
		startSave();
	}
	
	public List<UserEntity> getUsers() {
		return users;
	}

	public List<GroupEntity> getGroups() {
		return groups;
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
	
	public void load(){
		SystemLog.info("加载用户数据");
		List<UserEntity> users = dbManager.getUserDao().load();
		if (users != null){
			this.users = users;
		}
		SystemLog.info("加载部门数据");
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
		SystemLog.info("加载客户数据");
		List<TelInfoEntity> tels = dbManager.getTelInfoDao().load();
		if (tels != null && tels.size() > 0){
			this.tels = tels;
		}
	}
	
	/**
	 * 批量添加客户信息
	 * @param tels
	 * @return
	 */
	public void insertCustomers(TelInfoEntity[] tels){
		for (TelInfoEntity tel : tels){
			if (dbManager.getTelInfoDao().insert(tel)){
				addTel(tel);
			}
		}
	}
	
	public boolean insertCustomer(TelInfoEntity tel){
		return dbManager.getTelInfoDao().insert(tel);
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
		for (UserEntity u : users){
			if (u.getUsername().equals(user.getUsername())){
				return false;
			}
		}
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
				users.remove(user);
			}
			return true;
		}
		return false;
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
	
	public List<UserEntity> searchUsers(int fatherId){
		List<UserEntity> results = new ArrayList<UserEntity>();
		for (UserEntity user : users){
			GroupEntity group  = user.getFather();
			if (group != null && group.getId() == fatherId){
				results.add(user);
			}
		}
		return results;
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
	

	public List<TelInfoEntity> loadNotUserTel(Object locker,int num){
		synchronized (locker) {
			List<TelInfoEntity> result = new ArrayList<TelInfoEntity>();
			if (tels.size() == 0){
				return result;
			}
			Random random = new Random();
			while (num > 0 && tels.size() > 0){
				int index = random.nextInt(tels.size());
				TelInfoEntity tel = tels.get(index);
				tels.remove(index);
				dels.add(tel);
				if (tel.getUserId() == 0){
					result.add(tel);
					num--;
				}
			}
			return result;
		}
	}
	
	public List<TelInfoEntity> exportTel(int downType , String start ,String end,boolean flag){
		return dbManager.getTelInfoDao().load(downType,start, end,flag);
	}
	
	public void addTel(TelInfoEntity tel){
		if (tel.getUserId() == 0){
			tels.add(tel);
		}
	}
	
	public void startSave(){
		//30分钟刷新一次缓存。
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(new SaveEntity(),0,30 * 60 * 1000,TimeUnit.SECONDS);
	}
	
	public void save(){
		if (isFirst){
			isFirst = false;
			return;
		}
		SystemLog.info("服务器开始保存数据...");
		GroupDAO gDao = dbManager.getGroupDao();
		UserDAO uDao = dbManager.getUserDao();
		TelInfoDAO tDao = dbManager.getTelInfoDao();
		for (GroupEntity group : groups){
			if (group.isChange() && gDao.save(group)){
				group.setChange(false);
			}
		}
		for (UserEntity user : users){
			if (user.isChange() && uDao.save(user)){
				user.setChange(false);
			}
		}
		for (int i = 0 ; i < dels.size() ; ){
			TelInfoEntity tel = dels.get(i);
			if (tDao.save(tel)){
				dels.remove(i);
			}else{
				i++;
			}
		}
		SystemLog.info("服务器保存数据结束...");
	}
	
	public void clear(){
		groups.clear();
		tels.clear();
		dels.clear();
		users.clear();
	}
	
	class SaveEntity implements Runnable{
		public void run() {
			save();
		}
	}
}
 
 
 
 
