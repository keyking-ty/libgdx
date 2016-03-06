package com.keyking.event.entity;

import com.keyking.event.Event;
import com.keyking.event.EventListener;
import com.keyking.event.ChangeStateEvent;
import com.keyking.util.Instances;

public class NextEntity implements EventListener,Instances{
	
	@Override
	public Object handleEvent(Event event) {
		if (event.getCode() == Event.EVT_NEXT_ACTION){
			ChangeStateEvent state = ENGINE.transfer(event);
			if (state != null){
				ENGINE.setState(state.getNext());
				return "next";
			}
		}
		return null;
	}

}
 
 
