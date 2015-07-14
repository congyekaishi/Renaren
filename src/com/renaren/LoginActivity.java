package com.renaren;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.renaren.beans.LoginBean;
import com.renaren.beans.UserBean;
import com.renaren.tools.ConstUtil;
import com.renaren.tools.ExitApplication;
import com.renaren.tools.HttpUtil;
import com.renaren.view.TextURLView;

public class LoginActivity extends Activity {
	// 判断是否登录
	public String isLogin = null;

	private TextURLView mTextViewURL;

	// 声明控件对象
	public EditText et_name, et_pass;
	// 声明显示返回数据库的控件对象
	public TextView tv_result;

	// public TextURLView tv_forget;

	public String result;

	public String token;

	public String msg;

	public String nickName;

	public int userId;

	public int type;

	public SharedPreferences sp;

	public String headIconUrl;

	public Map<String, String> param = new HashMap<String, String>();

	public LoginBean login;
	public UserBean user;

	public String userName;
	public String userPass;

	// /String Url = "http://www.renaren.com/api/app/member.php";
	// / String Url = "http://192.168.1.229/test/api/app/member.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("login", MODE_PRIVATE);
		isLogin = sp.getString("token", null);
		if (isLogin != null) {
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			finish();
		} else {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// 设置显示的视图
			setContentView(R.layout.login);
			ExitApplication.getInstance().addActivity(this);

			// 通过 findViewById(id)方法获取用户名的控件对象
			et_name = (EditText) findViewById(R.id.etUserName);
			// 通过 findViewById(id)方法获取用户密码的控件对象
			et_pass = (EditText) findViewById(R.id.etPassWord);
			// 通过 findViewById(id)方法获取显示返回数据的控件对象
			// tv_result = (TextView) findViewById(R.id.tv_result);
			mTextViewURL = (TextURLView) findViewById(R.id.tv_forget_password);
			mTextViewURL.setText("忘记密码");

		}
	}

	/**
	 * 通过android:onClick="login"指定的方法 ， 要求这个方法中接受你点击控件对象的参数v
	 * 
	 * @param v
	 */
	public void login(View v) {
		// 获取点击控件的id
		int id = v.getId();
		// 根据id进行判断进行怎么样的处理
		switch (id) {
		// 登陆事件的处理
		case R.id.login:
			// 获取用户名
			userName = et_name.getText().toString();
			// 获取用户密码
			userPass = et_pass.getText().toString();
			if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
				Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
			} else {
				// 开启子线程
				try {
					new Thread(postLogin).start();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "网络异常", 0).show();
				}
			}
			break;
		case R.id.register:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_forget_password:
			Intent intent1 = new Intent(LoginActivity.this,
					ModifyPasswordActivity.class);
			startActivity(intent1);
			break;
		}
	}

	Runnable postLogin = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String json;
			param.put("action", "login");
			param.put("password", userPass);
			param.put("userlogin", userName);

			try {
				json = HttpUtil.postRequest(ConstUtil.MEMBER, param);
				Gson gson = new Gson();
				login = gson.fromJson(json, LoginBean.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mHandler.sendEmptyMessage(1);
		}
	};

	Thread thread2 = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			token = sp.getString("token", null);
			String json;
			param.put("action", "getUserInfo");
			param.put("user_auth", token);
			try {
				json = HttpUtil.postRequest(ConstUtil.MEMBER, param);
				Gson gson = new Gson();
				user = gson.fromJson(json, UserBean.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}// 从网络获取数据
			mHandler.sendEmptyMessage(2);
		}
	});

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Editor editor = sp.edit();
			switch (msg.what) {
			case 1:
				try {
					if (login.type == 1) {
						editor.putString("token", login.user_auth);
						editor.commit();
						thread2.start();
					} else {
						Toast.makeText(getApplicationContext(), login.msg, 0)
								.show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "网络异常，请检查网络设置",
							1000).show();
				}
				break;
			case 2:
				editor.putString("username", user.msg.username);
				editor.putString("sign", user.msg.sign);
				editor.putString("gender", user.msg.gender);
				editor.putString("birth", user.msg.birth);
				editor.putString("address", user.msg.address);
				editor.putString("email", user.msg.email);
				editor.putString("avatar", user.msg.avatar);
				editor.putString("realname", user.msg.realname);
				editor.putString("idcard", user.msg.idcard);
				editor.putString("company", user.msg.company);
				editor.putString("position", user.msg.position1);
				editor.putString("workExperience", user.msg.workExperience1);
				editor.putString("mobilephone", user.msg.mobilephone);
				editor.commit();
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		};
	};


}
