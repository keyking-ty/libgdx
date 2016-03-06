package com.keyking.service.dao.entity;

import com.keyking.service.dao.DataManager;
import com.keyking.service.util.DataBuffer;

public class GroupEntity {
	
	int id;
	
	String name;
	
	int fid;
	
	GroupEntity father;
	
	int task = 50;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public GroupEntity getFather() {
		return father;
	}

	public void setFather(GroupEntity father) {
		this.father = father;
	}
	
	public void setFid(int fid) {
		this.fid = fid;
	}

	public void init(DataManager dataManager) {
		father = dataManager.searchGroup(fid);
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}

	public void _serialize(DataBuffer buffer) {
		buffer.putInt(id);
		buffer.putPrefixedString(name == null ? "" : name);
		buffer.putInt(father == null ? 0 : father.getId());
		buffer.putInt(task);
	}
}
 
 
