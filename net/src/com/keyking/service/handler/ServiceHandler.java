package com.keyking.service.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.keyking.service.logic.Logic;
import com.keyking.service.resp.RespEntity;
import com.keyking.service.util.DataBuffer;

public class ServiceHandler extends IoHandlerAdapter {
	
	private static final String LOGIC_PACKAGE_NAME = "com.keyking.service.logic.impl.";
	
	@Override
	public void messageReceived(IoSession session , Object message)
			throws Exception {
		if (message instanceof DataBuffer){
			DataBuffer data = (DataBuffer)message;
			String logicName = data.getPrefixedString();
			Class<?> clazz = null;
			try {
				clazz = Class.forName(LOGIC_PACKAGE_NAME + logicName);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			Object obj = clazz.newInstance();
			if (obj instanceof Logic){
				Logic logic = (Logic)obj;
				try {
					RespEntity entity = logic.doLogic(data,logicName);
					if (entity != null){
						session.write(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		super.sessionCreated(iosession);
		System.out.println(iosession);
	}
}
 
 
