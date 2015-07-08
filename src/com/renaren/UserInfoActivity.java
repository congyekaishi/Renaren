package com.renaren;

import java.util.Date;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.renaren.tools.TextFormatter;
import com.renaren.view.TitleBarView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserInfoActivity extends Activity {

	@InjectView(R.id.infoGender)
	TextView tvGender;

	@InjectView(R.id.infoBirth)
	TextView tvBirth;

	@InjectView(R.id.infoAddress)
	TextView tvAddress;

	@InjectView(R.id.infoEmail)
	TextView tvEmail;

	@InjectView(R.id.infoIdentity)
	TextView tvIdNum;

	@InjectView(R.id.infoCompany)
	TextView tvCompany;

	@InjectView(R.id.infoPosition)
	TextView tvPosition;

	@InjectView(R.id.infoRealName)
	TextView tvRealName;

	@InjectView(R.id.infoWorkExperience)
	TextView tvWorkExperience;

	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("个人中心");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		sp = getSharedPreferences("login", 0);

		String gender = sp.getString("gender", null);
		String birth = sp.getString("birth", null);
		String address = sp.getString("address", null);
		String email = sp.getString("email", null);
		String realName = sp.getString("realname", null);
		String idNum = sp.getString("idcard", null);
		String company = sp.getString("company", null);
		String position = sp.getString("position", null);
		String workExperience = sp.getString("workExperience", null);

		if (gender.equals("1")) {
			tvGender.setText("男");
		} else {
			tvGender.setText("女");
		}

		long l = (Float.valueOf(birth).longValue()) * 1000;
		tvBirth.setText(TextFormatter.getDateTime(l));
		tvAddress.setText(address);
		tvEmail.setText(email);
		tvRealName.setText(realName);
		tvCompany.setText(company);
		tvIdNum.setText(idNum);
		tvPosition.setText(position);

		if (workExperience.equals("1")) {
			tvWorkExperience.setText("一年");
		} else if (workExperience.equals("2")) {
			tvWorkExperience.setText("两年");
		} else if (workExperience.equals("3")) {
			tvWorkExperience.setText("三年");
		} else if (workExperience.equals("5")) {
			tvWorkExperience.setText("五年以上");
		} else if (workExperience.equals("10")) {
			tvWorkExperience.setText("十年以上");
		} else {
			tvWorkExperience.setText("无");
		}

	}

}
