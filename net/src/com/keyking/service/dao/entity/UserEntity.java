package com.keyking.service.dao.entity;

import com.keyking.service.dao.DataManager;
import com.keyking.service.util.DataBuffer;


public class UserEntity {
	
	long id;//���
	
	String username;//�û���
	
	String password="123456";//����
	
	String email = "";//����
	
	String telephone = "";//�绰
	
	String firstName = "";//��
	
	String lastName = "";//��
	
	String address = "";//סַ
	
	int age= 16;//����
	
	String post="";//ְλ
	
	int fid;//��������

	GroupEntity father;
	
	int task = 50;//ÿ�������
	
	public long getId() {
		return id;
	}

	public long setId(long id) {
		this.id = id;
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public GroupEntity getFather() {
		return father;
	}

	public void setFather(GroupEntity father) {
		this.father = father;
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

	public void init(DataManager dataManager) {
		father = dataManager.searchGroup(fid);
	}

	public void _serialize(DataBuffer buffer) {
		buffer.putLong(id);
		buffer.putInt(age);
		buffer.putInt(father == null ? 0 : father.getId());
		buffer.putInt(task);
		buffer.putPrefixedString(username);
		buffer.putPrefixedString(password);
		buffer.putPrefixedString(firstName==null ? "" : firstName);
		buffer.putPrefixedString(lastName==null ? "" : lastName);
		buffer.putPrefixedString(telephone==null ? "" : telephone);
		buffer.putPrefixedString(address==null ? "" : address);
		buffer.putPrefixedString(email==null ? "" : email);
		buffer.putPrefixedString(post == null ? "" : post);
	}
}
 
 