package com.keyking.event;

import com.keyking.frame.GameScreen;
import com.keyking.util.Instances;

public class EventFactory implements Instances{
	
	private static EventFactory instance = new EventFactory();
	
	public static EventFactory getInstance(){
		return instance;
	}
	
	public Event create_evt_exit(int delay){
		Event event = new Event(Event.EVT_EXIT_CODE);
		event.setDelay(delay);
		return event;
	}
	
	public Event create_evt_Next(int delay,GameScreen next){
		ChangeStateEvent state = new ChangeStateEvent();
		state.setDelay(delay);
		state.setNext(next);
		return state;
	}
}
 
 
