package com.renaren;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.google.gson.Gson;
import com.renaren.beans.LoginBean;
import com.renaren.tools.ConstUtil;
import com.renaren.tools.HttpUtil;
import com.renaren.view.TitleBarView;

public class RegisterActivity extends Activity {

	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;

	EditText etPhoneNumber;
	EditText etEmail;
	EditText etPassword;
	EditText etPassword2;
	EditText etCaptcha;

	Button btRegister;
	Button btCaptcha;

	String phoneNumber;
	String email;
	String password;
	String password2;
	String captcha;

	LoginBean register, captchaInfo;

	public Map<String, String> captchaParam = new HashMap<String, String>();
	public Map<String, String> registerParam = new HashMap<String, String>();
	public String printJson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("个人注册");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword2 = (EditText) findViewById(R.id.etPassword2);
		etCaptcha = (EditText) findViewById(R.id.etCaptcha);

		btRegister = (Button) findViewById(R.id.btRegister);
		btCaptcha = (Button) findViewById(R.id.btCaptcha);

		btCaptcha.setOnClickListener(listener);
		btRegister.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btRegister:
				if (TextUtils.isEmpty(etCaptcha.getText())) {
					Toast.makeText(getApplicationContext(), "请输入验证码", 0).show();
				}
				if (TextUtils.isEmpty(etPhoneNumber.getText())) {
					Toast.makeText(getApplicationContext(), "请输入手机号", 0).show();
				}
				if (TextUtils.isEmpty(etEmail.getText())) {
					Toast.makeText(getApplicationContext(), "请输入电子邮箱", 0)
							.show();
				}
				if (TextUtils.isEmpty(etPassword.getText())) {
					Toast.makeText(getApplicationContext(), "请输入密码", 0).show();
				}
				if (checkEmail(etEmail.getText().toString())) {
					Toast.makeText(getApplicationContext(), "请输入正确的邮箱地址", 0)
							.show();
				} else {
					phoneNumber = etPhoneNumber.getText().toString();
					email = etEmail.getText().toString();
					password = etPassword.getText().toString();
					password2 = etPassword2.getText().toString();
					captcha = etCaptcha.getText().toString();
					if (password.equals(password2)) {
						new Thread(postRegister).start();
					} else {
						Toast.makeText(getApplicationContext(), "两次密码输入不一致", 0)
								.show();
					}
				}
				break;
			case R.id.btCaptcha:
				phoneNumber = etPhoneNumber.getText().toString();
				if (TextUtils.isEmpty(phoneNumber)) {
					Toast.makeText(getApplicationContext(), "手机号不能为空", 0)
							.show();
				}
				new Thread(getCaptcha).start();
				break;
			default:
				break;
			}
		}
	};

	Runnable getCaptcha = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String json;
			captchaParam.put("captchaType", "mobile_register");
			captchaParam.put("phone", phoneNumber);
			try {
				json = HttpUtil.postRequest(ConstUtil.CAPTCHA, captchaParam);
				Gson gson = new Gson();
				captchaInfo = gson.fromJson(json, LoginBean.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mHandler.sendEmptyMessage(1);
		}
	};

	Runnable postRegister = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String json;
			registerParam.put("mobilecaptcha", captcha);
			registerParam.put("username", phoneNumber);
			registerParam.put("mobilephone", phoneNumber);
			registerParam.put("email", email);
			registerParam.put("password", password);
			registerParam.put("action","register");
			// registerParam.put("realname", realName);
			// registerParam.put("company", company);
			// post.put("action", "register");
			// post.put("mobilephone", "13999999999");
			// post.put("company", "");
			// post.put("realname", "qwe");
			// post.put("email", "wheat.b@163.com");
			try {
				json = HttpUtil.postRequest(ConstUtil.REGISTER, registerParam);
				Gson gson = new Gson();
				register = gson.fromJson(json, LoginBean.class);
				printJson = json;
			} catch (Exception e) {
				// TODO: handle exception
			}

			mHandler.sendEmptyMessage(2);
		}
	};

	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				// etCaptcha.setText(captchaInfo.captcha);
				Toast.makeText(getApplicationContext(), captchaInfo.msg, 0)
						.show();
				break;
			case 2:
				if (register.type == 1) {
					Toast.makeText(getApplicationContext(), register.msg, 0)
							.show();
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), register.msg, 0)
							.show();
				}

				break;
			default:
				break;
			}
		};
	};

	public boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("^/w+([-.]/w+)*@/w+([-]/w+)*/.(/w+([-]/w+)*/.)*[a-z]{2,3}$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public boolean checkPhone(String phone) {
		Pattern pattern = Pattern.compile("^13/d{9}||15[8,9]/d{8}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
}
