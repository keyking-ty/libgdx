package com.keyking.data;

import java.util.List;

public interface Node {
	
	public String getName();
	
	public int getCount();
	
	public String getFixInfo(float space);
	
	public boolean couldOpen();
	
	public List<Node> getChildren();
	
	public Node getFather();
	
	public void setFather(Node father);
	
}
 
 
