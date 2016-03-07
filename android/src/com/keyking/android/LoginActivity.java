package com.keyking.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.keyking.android.net.NetUtil;
import com.keyking.net.message.impl.LoginRequest;

public class LoginActivity extends TipActivity{
	
	private EditText userEditText,pwdEditText;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		String url = getString(R.string.server_url);
		net = new NetUtil(this,url);
		userEditText = (EditText)findViewById(R.id.userEditText);
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		Button login = (Button)findViewById(R.id.loginButton);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
	}
	
	private void login(){
		if (checkNull(userEditText)){
			showDialog("请输入用户名称");
			return;
		}
		if (checkNull(pwdEditText)){
			showDialog("请输入用户密码");
			return;
		}
		String username = userEditText.getText().toString();
		String password = pwdEditText.getText().toString();
		ProgressDialog loadDialog = ProgressDialog.show(this,"", "登陆中…", true);
		net.connect(new LoginRequest(username,password),loadDialog);
    }
}
 
 
 
