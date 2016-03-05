package com.keyking.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.keyking.android.net.NetUtil;

public class TipActivity extends Activity{
	
	public static int HANDLER_CODE_TIP = 1;//提示
	
	public static int HANDLER_CODE_ENTER_IN = 2;//进入主界面
	
	public static int HANDLER_CODE_DOWN = 3;//下载成功
	
	public static int HANDLER_CODE_UPDATE = 4;//下载成功
	
	//SharedPreferences shared;
	
	NetUtil net = null;
	
	Handler handler;
	
	private static List<Activity> activitys = new ArrayList<Activity>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activitys.add(this);
		//shared = getSharedPreferences("contact_cach",0);
		handler = new Handler(new Callback(){
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.what == HANDLER_CODE_TIP) {
					showDialog(msg.obj.toString());
					net.dialogDismiss();
					return true;
				}else if (msg.what == HANDLER_CODE_ENTER_IN) {
					net.dialogDismiss();
					Intent intent = new Intent(TipActivity.this,MainActivity.class);
			        intent.putExtras((Bundle)msg.obj);
			        startActivity(intent);
				}else if (msg.what == HANDLER_CODE_DOWN){
					inserts(msg.obj.toString());
				}else if (msg.what == HANDLER_CODE_UPDATE){
					net.dialogDismiss();
					MainActivity main = (MainActivity)TipActivity.this;
					main.setTask(msg.arg1);
					main.showDown();
				}
				return false;
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		exit();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		activitys.remove(this);
	}

	protected void showDialog(String msg){
		Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}
	
	public void _exit(){
		try {   
            for (Activity activity : activitys) {   
                if (activity != null){
                	activity.finish();
                }
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            android.os.Process.killProcess(android.os.Process.myPid());     
            System.exit(0);   
        }
	}
	
	public void exit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("确定退出系统？").setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						_exit();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public boolean checkNull(EditText text){
		CharSequence cStr =  text.getText();
		if (cStr == null){
			return true;
		}
		if (cStr.toString().equals("")){
			return true;
		}
		return false;
	}
	
	public void handler(Message msg){
		handler.sendMessage(msg);
	}
	
	protected boolean delete() {
		try {
			new Thread() {
				@Override
				public void run() {
					getContentResolver().delete(Uri.parse(ContactsContract.RawContacts.CONTENT_URI.toString() + "?" + "caller_is_syncadapter" + "=true"), "_id>0", null);
				}
			}.start();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	protected void inserts(final String infos) {
		new Thread() {
			@Override
			public void run() {
				String[] ss = infos.split(",");
				for (int i = 0; i < ss.length; i += 2) {
					String num = ss[i + 1];
					ContentValues values = new ContentValues();
					Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI,values);
					long rawContactId = ContentUris.parseId(rawContactUri);
					//添加名字
					values.clear();
					values.put(Data.RAW_CONTACT_ID, rawContactId);
					values.put(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE);
					values.put(StructuredName.GIVEN_NAME,"****");
					getContentResolver().insert(Data.CONTENT_URI,values);
					//添加电话
					values.clear();
					values.put(Data.RAW_CONTACT_ID, rawContactId);
					values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
					values.put(Phone.NUMBER, num);
					values.put(Phone.TYPE,Phone.TYPE_MOBILE);
					getContentResolver().insert(Data.CONTENT_URI,values);
					
				}
				net.dialogDismiss();
				Message msg = new Message();
				msg.what = TipActivity.HANDLER_CODE_TIP;
				msg.obj  = "下载成功";
				handler(msg);
			}
		}.start();
	}
}
 
