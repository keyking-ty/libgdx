package com.keyking.android.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.ProgressDialog;
import android.os.Message;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.backends.android.AndroidNet;
import com.keyking.android.TipActivity;
import com.keyking.android.net.reader.Reader;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;

public class NetUtil implements HttpResponseListener{
	String url;
	public NetUtil (TipActivity activity,String url){
		this.activity = activity;
		this.url = url;
	}
	
	private static final String READER_PACKAGE_NAME = "com.keyking.android.net.reader.";
	
	//private final static String SERVICE_ADDRESS_URL = "http://keyking-ty.xicp.net/contact-service/logic";
	
	AndroidNet net = new AndroidNet(null);
	
	ProgressDialog dialog = null;
	
	TipActivity activity = null;
	
	public void connect(RequestEntity entity,ProgressDialog dialog){
		if (entity == null){
			return;
		}
		this.dialog = dialog;
		DataBuffer buffer = DataBuffer.allocate(1024);
		entity.serialize(buffer);
		HttpRequest request = new HttpRequest(Net.HttpMethods.POST);
		byte[] bytes = buffer.arrayToPosition();
		request.setUrl(url);
		request.setContent(new ByteArrayInputStream(bytes),bytes.length);
		request.setTimeOut(10000);
		net.sendHttpRequest(request,this);
	}

	@Override
	public void cancelled() {
		dialog.dismiss();
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_TIP;
		msg.obj = "用户取消";
		activity.handler(msg);
	}

	@Override
	public void failed(Throwable arg0) {
		dialog.dismiss();
		Message msg = new Message();
		msg.what = TipActivity.HANDLER_CODE_TIP;
		msg.obj = "请求超时";
		activity.handler(msg);
	}

	@Override
	public void handleHttpResponse(HttpResponse resp) {
		//dialog.dismiss();
		try {
			byte[] bytes = read(resp.getResultAsStream());
			DataBuffer data = DataBuffer.wrap(bytes);
			String logicName = data.getPrefixedString();
			Class<?> clazz = Class.forName(READER_PACKAGE_NAME + logicName);
			Object obj = clazz.newInstance();
			if (obj instanceof Reader){
				Reader reader = (Reader)obj;
				reader.read(data,activity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private byte[] read(InputStream in) throws Exception{
		int count;
		byte data[] = new byte[1024]; 
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((count = in.read(data,0,1024)) != -1) {  
			bos.write(data, 0, count);
		}
		return bos.toByteArray();
	}

	public ProgressDialog getDialog() {
		return dialog;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}
	
	public void dialogDismiss(){
		if (dialog != null){
			dialog.dismiss();
			dialog = null;
		}
	}
}
 
 
