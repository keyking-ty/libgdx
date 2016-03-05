package com.keyking.service.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyking.service.servlet.logic.Logic;
import com.keyking.service.servlet.resp.RespEntity;
import com.keyking.service.util.DataBuffer;
import com.keyking.service.util.SystemLog;

@SuppressWarnings("serial")
public class LogicServlet extends HttpServlet {

	private static final String LOGIC_PACKAGE_NAME = "com.keyking.service.servlet.logic.impl.";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// response.setContentType("text/html;UTF-8");
			// response.setCharacterEncoding("UTF-8");
			// response.setContentType("text/html");
			InputStream in = request.getInputStream();
			byte[] bytes = read(in);
			DataBuffer requestBuffer = DataBuffer.wrap(bytes);
			String logicName = requestBuffer.getPrefixedString();
			Class<?> clazz = Class.forName(LOGIC_PACKAGE_NAME + logicName);
			Object obj = clazz.newInstance();
			if (obj instanceof Logic) {
				Logic logic = (Logic) obj;
				logic.setIp(getIpAddr(request));
				RespEntity entity = logic.doLogic(requestBuffer,logicName);
				if (entity != null) {
					OutputStream out = response.getOutputStream();
					DataBuffer respBuffer = DataBuffer.allocate(1024);
					entity.serialize(respBuffer);
					out.write(respBuffer.arrayToPosition());
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			SystemLog.info(e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {

	}

	public byte[] read(InputStream in) throws Exception {
		int count;
		byte data[] = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((count = in.read(data, 0, 1024)) != -1) {
			bos.write(data, 0, count);
		}
		return bos.toByteArray();
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
 
