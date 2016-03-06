package com.keyking.event;

import com.keyking.util.Instances;


public interface EventListener extends Instances{
	public Object handleEvent(Event event);
}
 
 
