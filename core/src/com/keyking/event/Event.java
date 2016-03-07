package com.keyking.event;

public class Event {
	
	public static final int EVT_EXIT_CODE = 1;//�˳��¼�
	
	public static final int EVT_NEXT_ACTION = 2;//��һ���¼�

	int code;
	
	int delay;
	
	public Event(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public boolean tick(){
		delay--;
		return delay == 0;
	}
}
 
 