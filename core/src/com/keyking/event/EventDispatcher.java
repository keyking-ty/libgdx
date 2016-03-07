package com.keyking.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

public class EventDispatcher {
	
	Map<Integer, List<EventListener>> listenerMap = new HashMap<Integer,List<EventListener>>();
	
	List<Event> asyEvents = new ArrayList<Event>() ;//�ӳٴ����¼�
	
	/**
	 * ע���¼�����
	 * @param listener
	 * @param event
	 */
	public void addListener(EventListener listener, int event) {
		List<EventListener> listeners = listenerMap.get(event);
		if (listeners == null){
			listeners = new ArrayList<EventListener>();
			listenerMap.put(event,listeners);
		}
		listeners.add(listener);
	}
	
	/**
	 * �����¼�
	 * @param event
	 */
	private final List<Object> _raise(Event event) {
		List<Object> objects = null;
		List<EventListener> listeners = listenerMap.get(event.getCode());
		if (listeners != null) {
			for (EventListener listener : listeners) {
				try {
					Object object = listener.handleEvent(event);
					if (object != null){
						if (objects == null){
							objects = new ArrayList<Object>();
						}
						objects.add(object);
					}
				} catch (Exception e) {
					Gdx.app.error("error","do GameEvent error : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return objects;
	}
	
	private final List<Object> _preserve(Event event) {
		if (!asyEvents.contains(event)) {
			asyEvents.add(event);
		}
		return null;
	}

	/**
	 * ͬ���¼�
	 * @param event
	 */
	public final List<Object> raise(Event event) {
		if (event.getDelay() > 0) {
			return _preserve(event);
		} else {
			return _raise(event);
		}
	}
	
	/**
	 * �����첽�¼�
	 */
	public void tick(){
		for (int i = 0 ; i < asyEvents.size() ; ){
			Event event = asyEvents.get(i);
			if (event.tick()){
				raise(event);
				asyEvents.remove(i);
				continue;
			}
			i++;
		}
	}
}
 
 