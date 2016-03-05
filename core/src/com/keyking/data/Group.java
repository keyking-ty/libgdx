package com.keyking.data;

import java.util.ArrayList;
import java.util.List;

public class Group implements Node{
	
	int id ;
	
	String name;
	
	Node father;
	
	List<Node> children = new ArrayList<Node>();
	
	int fid;
	
	int task;
	
	public Group(int id){
		this.id = id;
	}
	
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

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}
	
	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}

	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node node){
		if (node == null){
			return;
		}
		node.setFather(this);
		children.add(node);
	}
	
	public int getCount(){
		int count = 1;
		for (Node node : children){
			count += node.getCount();
		}
		return count;
	}

	@Override
	public String getFixInfo(float space) {
		return "," + getCount() + "," + space + "," + id;
	}

	@Override
	public boolean couldOpen() {
		return true;
	}

	public void copy(Group g) {
		this.name = g.name;
		this.task = g.task;
		this.fid  = g.fid;
	}
}
 
