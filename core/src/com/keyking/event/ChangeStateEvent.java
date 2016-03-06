package com.keyking.event;

import com.keyking.frame.GameScreen;

public class ChangeStateEvent extends Event {
	
	GameScreen next = null;
	
	public ChangeStateEvent() {
		super(EVT_NEXT_ACTION);
	}

	public GameScreen getNext() {
		return next;
	}

	public void setNext(GameScreen next) {
		this.next = next;
	}
}
 
 
