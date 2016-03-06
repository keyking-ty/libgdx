package com.keyking.service;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.keyking.service.codec.MessageCodecFactory;
import com.keyking.service.dao.DataManager;
import com.keyking.service.handler.ServiceHandler;

public class Service {
	
	public final static String URL = "keyking-ty.xicp.net";
	
	private static int PORT = 3834;
	
	public static void main(String[] args) {
        try {
        	DataManager.getInstance().load();
    		SocketAcceptor acceptor = new NioSocketAcceptor();  
            SocketSessionConfig config = acceptor.getSessionConfig();  
            config.setReadBufferSize(4096);
            config.setIdleTime(IdleStatus.BOTH_IDLE,10);
            DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
            chain.addLast("codec",new ProtocolCodecFilter(new MessageCodecFactory()));
            acceptor.setHandler(new ServiceHandler());
            InetAddress address = InetAddress.getByName(URL);
			acceptor.bind(new InetSocketAddress(address,PORT));
			JOptionPane.showMessageDialog(null,"Æô¶¯³É¹¦");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
}
 
 
