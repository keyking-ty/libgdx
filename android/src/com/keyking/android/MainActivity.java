package com.keyking.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.keyking.android.net.NetUtil;
import com.keyking.net.message.impl.user.ChangePasswardRequest;
import com.keyking.net.message.impl.user.CommiteRequest;
import com.keyking.net.message.impl.user.DownInfoRequest;
import com.keyking.net.message.impl.user.UpdateNumRequest;

public class MainActivity extends TipActivity {

	long userId;

	int task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String url = getString(R.string.server_url);
		net = new NetUtil(this,url);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		userId = bundle.getLong("uid");
		task = bundle.getInt("task");
		Button button = (Button) findViewById(R.id.buttonDown);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog loadDialog = ProgressDialog.show(MainActivity.this,"", "ͬ���С�");
				net.connect(new UpdateNumRequest(userId),loadDialog);
				//showDown();
			}
		});
		
		button = (Button) findViewById(R.id.buttonClear);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showClear();
			}
		});
		
		button = (Button) findViewById(R.id.buttonCommit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCommit();
			}
		});
		
		button = (Button) findViewById(R.id.buttonUpdate);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showUpdate();
			}
		});
		
		button = (Button) findViewById(R.id.buttonExit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});
	}

	public void showDown() {
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.d_down,(ViewGroup)findViewById(R.id.down_dialog));
		TextView left = (TextView)layout.findViewById(R.id.d_down_left_s);
		left.setText(String.valueOf(task));
		new AlertDialog.Builder(this).setTitle("��������").setView(layout)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText downNum = (EditText)layout.findViewById(R.id.input_num);
						if (checkNull(downNum)){
							showDialog("��������������");
							return;
						}
						String str = downNum.getText().toString();
						int num = Integer.parseInt(str);
						ProgressDialog loadDialog = ProgressDialog.show(MainActivity.this,"", "�����С�");
						net.connect(new DownInfoRequest(userId,num),loadDialog);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
	}
	
	private void showClear() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("ȷ������ֻ�ͨѶ¼��").setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						delete();
					}
				}).setNegativeButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showCommit() {
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.d_commit,(ViewGroup)findViewById(R.id.commit_dialog));
		new AlertDialog.Builder(this).setTitle("�ύ����").setView(layout)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText name = (EditText)layout.findViewById(R.id.input_commit_name);
						if (checkNull(name)){
							showDialog("������ͻ�����");
							return;
						}
						EditText tel = (EditText)layout.findViewById(R.id.input_commit_tel);
						if (checkNull(tel)){
							showDialog("������ͻ��绰");
							return;
						}
						String str = name.getText().toString() + "," + tel.getText().toString();
						ProgressDialog loadDialog = ProgressDialog.show(MainActivity.this,"", "�ύ�С�");
						net.connect(new CommiteRequest(userId,str),loadDialog);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
	}
	
	public void showUpdate(){
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.d_update,(ViewGroup)findViewById(R.id.update_dialog));
		new AlertDialog.Builder(this).setTitle("�޸Ľ���").setView(layout)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText pwd1 = (EditText)layout.findViewById(R.id.input_update1);
						if (checkNull(pwd1)){
							showDialog("������ɵ�����");
							return;
						}
						EditText pwd2 = (EditText)layout.findViewById(R.id.input_update2);
						if (checkNull(pwd2)){
							showDialog("�������µ�����");
							return;
						}
						EditText pwd3 = (EditText)layout.findViewById(R.id.input_update3);
						if (checkNull(pwd3)){
							showDialog("������ȷ������");
							return;
						}
						String old  = pwd1.getText().toString();
						String new1 = pwd2.getText().toString();
						String new2 = pwd3.getText().toString();
						if (!new1.equals(new2)){
							showDialog("�������벻ƥ������������");
							return;
						}
						ProgressDialog loadDialog = ProgressDialog.show(MainActivity.this,"","�޸��С�");
						net.connect(new ChangePasswardRequest(userId,old,new1),loadDialog);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}
}
 
 
 