package com.keyking.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.keyking.data.TelInfo;
import com.keyking.data.User;
import com.keyking.net.message.DataBuffer;
import com.keyking.net.message.RequestEntity;
import com.keyking.net.message.impl.LoginRequest;
import com.keyking.net.message.impl.admin.GroupAddRequest;
import com.keyking.net.message.impl.admin.GroupDelRequest;
import com.keyking.net.message.impl.admin.GroupFixRequest;
import com.keyking.net.message.impl.admin.GroupResetRequest;
import com.keyking.net.message.impl.admin.RefreshRequest;
import com.keyking.net.message.impl.admin.TelInfoDown;
import com.keyking.net.message.impl.admin.TelInfoRequest;
import com.keyking.net.message.impl.admin.UserAddRequest;
import com.keyking.net.message.impl.admin.UserDelRequest;
import com.keyking.net.message.impl.admin.UserFixRequest;
import com.keyking.net.message.impl.user.ChangePasswardRequest;
import com.keyking.net.message.impl.user.CommiteRequest;
import com.keyking.net.message.impl.user.DownInfoRequest;
import com.keyking.net.reader.Reader;
import com.keyking.util.DataOperateUtil;
import com.keyking.util.Instances;

public class ServiceUtil implements Instances , HttpResponseListener{

	private static ServiceUtil instance = new ServiceUtil();
	
	protected final static String SERVICE_ADDRESS_URL = "http://36.7.67.250:9001/contact-service/logic";
	
	//private final static String SERVICE_ADDRESS_URL = "http://keyking-ty.xicp.net/contact-service/logic";
	
	protected final static String SERVICE_LOCAL_URL = "http://127.0.0.1:8080/contact-service/logic";
	
	protected static final String READER_PACKAGE_NAME = "com.keyking.net.reader.impl.";
	
	//http://www.whatismyip.com.tw
	
	public static ServiceUtil getInstance(){
		return instance;
	}
	
	public ServiceUtil init(){
		return this;
	}
	
	public void login(String username,String passward){
		write(new LoginRequest(username,passward));
	}
	
	/**
	 * 管理员部分
	 */
	public void add_g(String name,int fatherId){
		write(new GroupAddRequest(name,fatherId));
	}
	
	public void del_g(String ids){
		write(new GroupDelRequest(ids));
	}
	
	public void fix_g(int id ,String name,int fatherId,int task){
		write(new GroupFixRequest(id,name,fatherId,task));
	}
	
	public void add_u(String name,int fatherId){
		write(new UserAddRequest(name,fatherId));
	}
	
	public void del_u(String ids){
		write(new UserDelRequest(ids));
	}
	
	public void fix_u(User user) {
		write(new UserFixRequest(user));
	}
	
	public void reset_g(String ids){
		write(new GroupResetRequest(ids));
	}
	
	/**
	 *
	 * 用户部分
	 * @param num 
	 */
	public void down(int num) {
		write(new DownInfoRequest(DM.getUserId(),num));
	}
	
	public void commite(String name) {
		write(new CommiteRequest(DM.getUserId(),name));
	}
	
	public void changePass(String old, String newpassward) {
		write(new ChangePasswardRequest(DM.getUserId(),old,newpassward));
	}
	
	public void importData(String fileName,boolean flag){
		ENGINE.getGameScreen().showLoading(true);
		List<TelInfo> tels = DataOperateUtil.readText(fileName,flag);
		if (tels.size() > 0){
			write(new TelInfoRequest(tels));
		}
	}
	
	public void refresh(boolean flag,int id){
		write(new RefreshRequest(flag,id));
	}
	
	public void exportData(String fileName , String time1 , String time2 ,boolean flag){
		ENGINE.getGameScreen().showLoading(true);
		write(new TelInfoDown(fileName,time1,time2,flag));
	}
	
	public void write(RequestEntity entity){
		if (entity == null){
			return;
		}
		DataBuffer buffer = DataBuffer.allocate(1024);
		entity.serialize(buffer);
		HttpRequest request = new HttpRequest(Net.HttpMethods.POST);
		byte[] bytes = buffer.arrayToPosition();
		//String url = EngineControler.plat == EngineControler.PLAT_WIN32 ? SERVICE_LOCAL_URL : SERVICE_ADDRESS_URL;
		//request.setUrl(SERVICE_ADDRESS_URL);
		request.setUrl(SERVICE_LOCAL_URL);
		request.setContent(new ByteArrayInputStream(bytes),bytes.length);
		if (entity instanceof TelInfoRequest || entity instanceof  TelInfoDown){
			request.setTimeOut(30*60*1000);
		}else{
			request.setTimeOut(10000);
			ENGINE.getGameScreen().showLoading(true);
		}
		Gdx.net.sendHttpRequest(request,this);
	}

	@Override
	public void handleHttpResponse(HttpResponse response) {
		ENGINE.getGameScreen().showLoading(false);
		try {
			byte[] bytes = read(response.getResultAsStream());
			DataBuffer data = DataBuffer.wrap(bytes);
			String logicName = data.getPrefixedString();
			Class<?> clazz = Class.forName(READER_PACKAGE_NAME + logicName);
			Object obj = clazz.newInstance();
			if (obj instanceof Reader){
				Reader reader = (Reader)obj;
				reader.read(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ENGINE.getGameScreen().message("请求失败");
		}
	}
	
	public byte[] read(InputStream in) throws Exception{
		int count;
		byte data[] = new byte[1024]; 
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((count = in.read(data,0,1024)) != -1) {  
			bos.write(data, 0, count);
		}
		return bos.toByteArray();
	}
	
	@Override
	public void failed(Throwable throwable) {
		ENGINE.getGameScreen().showLoading(false);
		ENGINE.getGameScreen().message("请求超时");
	}

	@Override
	public void cancelled() {
		ENGINE.getGameScreen().showLoading(false);
		ENGINE.getGameScreen().message("用户取消");
	}
}
 
 
