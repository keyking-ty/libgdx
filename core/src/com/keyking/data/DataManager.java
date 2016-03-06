package com.keyking.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.keyking.net.message.DataBuffer;
import com.keyking.util.Instances;

public class DataManager implements Instances{
	
	List<Node> tree = new ArrayList<Node>();
	
	List<Node> nodes  = new ArrayList<Node>();
	
	long userId = 0;
	
	int taskNum = 0;
	
	List<Node> tels = new ArrayList<Node>();
	
	private static DataManager instance = new DataManager();
	
	public static DataManager getInstance(){
		return instance;
	}
	
	public void clear(){
		nodes.clear();
		tree.clear();
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public List<Node> getTels() {
		return tels;
	}

	public Group readGroup(DataBuffer data){
		int id  = data.getInt();
		String name = data.getPrefixedString();
		int fid = data.getInt();
		int task = data.getInt();
		Group group = new Group(id);
		group.setName(name);
		group.setFid(fid);
		group.setTask(task);
		return group;
	}
	
	public User readUser(DataBuffer data){
		User user = new User();
		long id = data.getLong();
		int age = data.getInt();
		int fid = data.getInt();
		int task = data.getInt();
		String name = data.getPrefixedString();
		String pass = data.getPrefixedString();
		String firstName = data.getPrefixedString();
		String lastName = data.getPrefixedString();
		String telephone = data.getPrefixedString();
		String address = data.getPrefixedString();
		String email = data.getPrefixedString();
		String post = data.getPrefixedString();
		user.setId(id);
		user.setName(name);
		user.setPass(pass);
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setAge(age);
		user.setPost(post);
		user.setTask(task);
		user.setFid(fid);
		return user;
	}
	
	public User ReadAndInsertUser(DataBuffer data){
		User user = readUser(data);
		if (user.getFid() > 0){
			for (Node node : nodes){
				if (node.couldOpen() && (node instanceof Group)){
					Group group = (Group)node;
					if (group.getId() == user.getFid()){
						group.addChild(user);
						break;
					}
				}
			}
		}else if (user.getId() > 1){//普通 用户才显示
			addGroup(user);
		}
		return user;
	}
	
	public void readAdmin(DataBuffer data){
		clear();
		int size = data.getInt();//部门信息
		if (size > 0){
			List<Group> groups = new ArrayList<Group>();
			Map<Integer,List<Group>> hgs = new HashMap<Integer, List<Group>>();
			for (int i = 0 ; i < size ; i++){
				Group group = readGroup(data);
				if (group.getFid() > 0){
					List<Group> temps = hgs.get(group.getFid());
					if (temps == null){
						temps = new ArrayList<Group>();
						hgs.put(group.getFid(),temps);
					}
					temps.add(group);
				}
				groups.add(group);
			}
			for (Integer id : hgs.keySet()){
				for (Group group : groups){
					if (group.getId() == id.intValue()){
						for (Group g : hgs.get(id)){
							g.setFather(group);
						}
						break;
					}
				}
			}
			for (Group group : groups){
				addGroup(group);
			}
		}
		size = data.getInt();//用户信息
		for (int i = 0 ; i < size ; i++){
			ReadAndInsertUser(data);
		}
	}
	
	public void update(DataBuffer data){
		userId = data.getLong();
		if (userId > 1){
			final String tip = data.getPrefixedString();
			if (tip != null && !tip.equals("")){
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						ENGINE.getGameScreen().message(tip);
					}
				});
			}
			taskNum = data.getInt();
			tels.clear();
			int size = data.getInt();
			if (size > 0){
				for (int i = 0 ; i < size ; i++){
					String name      = data.getPrefixedString();
					String telephone = data.getPrefixedString();
					TelInfo info  = new TelInfo();
					info.setName(name);
					info.setTelephone(telephone);
					info.setUserId(userId);
					tels.add(info);
				}
			}
		}else {
			readAdmin(data);
		}
	}
	
	public void addGroup(Node node){
		if (node == null){
			return;
		}
		if (node.couldOpen()){
			if (!nodes.contains(node)){
				nodes.add(node);
			}
		}
		if (node.getFather() == null){
			tree.add(node);
		}else{
			Node father = node.getFather();
			father.getChildren().add(node);
		}
	}
	
	public void removeNode(Node node){
		if (node == null){
			return;
		}
		if (nodes.contains(node)){
			nodes.remove(node);
		}
		if (tree.contains(node)){
			tree.remove(node);
			for (Node sun : node.getChildren() ){
				sun.setFather(null);
				tree.add(sun);
			}
		}else{
			Node father = node.getFather();
			father.getChildren().remove(node);
			for (Node sun : node.getChildren() ){
				sun.setFather(father);
				father.getChildren().add(sun);
			}
		}
	}
	
	public List<Node> getTreeGroups() {
		return tree;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		for (Node node : nodes){
			groups.add((Group)node);
		}
		return groups;
	}
	
	public Node searchGroup(int id){
		for (Node node : nodes){
			if (((Group)node).getId() == id){
				return node;
			}
		}
		return null;
	}
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (Node node : nodes){
			List<Node> nodes = node.getChildren();
			for (Node n : nodes){
				if (!n.couldOpen() && (n instanceof User)){
					User user = (User)n;
					users.add(user);
				}
			}
		}
		for (Node node : tree){
			if (!node.couldOpen() && (node instanceof User)){
				User user = (User)node;
				users.add(user);
			}
		}
		return users;
	}
	
	public User searchUser(long id) {
		for (Node node : nodes){
			List<Node> children = node.getChildren();
			for (Node child : children){
				if (!child.couldOpen() && child instanceof User){
					User user = (User)child;
					if (user.getId() == id){
						return user;
					}
				}
			}
		}
		for (Node node : tree){
			if (!node.couldOpen() && node instanceof User){
				User user = (User)node;
				if (user.getId() == id){
					return user;
				}
			}
		}
		return null;
	}
}
 
 
