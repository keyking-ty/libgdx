package com.keyking.data;

import java.util.List;

import com.keyking.net.message.DataBuffer;

public class User implements Node{
	
	long id;
	
	String name;
	
	String pass="123456";
	
	String firstName="张";
	
	String lastName="三";
	
	String telephone="12345678901";
	
	String address="安徽省合肥市";
	
	String email="123456789@qq.com";
	
	int age=18;
	
	String post="组长";
			
	Node father;
	
	int fid;
	
	int task;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}
	
	public void _serialize(DataBuffer buffer) {
		buffer.putLong(id);
		buffer.putInt(age);
		buffer.putInt(fid);
		buffer.putInt(task);
		buffer.putPrefixedString(name);
		buffer.putPrefixedString(pass);
		buffer.putPrefixedString(firstName);
		buffer.putPrefixedString(lastName);
		buffer.putPrefixedString(telephone);
		buffer.putPrefixedString(address);
		buffer.putPrefixedString(email);
		buffer.putPrefixedString(post);
	}

	public void copy(User user) {
		this.name = user.name;
		this.pass = user.pass;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.telephone = user.telephone;
		this.address = user.address;
		this.email = user.email;
		this.post = user.post;
		this.age = user.age;
		this.task = user.task;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public String getFixInfo(float space) {
		return "," + getCount() + "," + space + "," + id;
	}

	@Override
	public boolean couldOpen() {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		return null;
	}
}
 
