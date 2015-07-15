package com.renaren;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.renaren.tools.TextFormatter;
import com.renaren.view.TitleBarView;


public class UserInfoActivity extends Activity {
    TextView tvGender;

    TextView tvBirth;

    TextView tvAddress;

    TextView tvEmail;

    TextView tvIdNum;

    TextView tvCompany;

    TextView tvPosition;

    TextView tvRealName;

    TextView tvWorkExperience;

    TitleBarView mTitleBarView;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_info);

        TextView tvGender = (TextView) findViewById(R.id.infoGender);

        TextView tvBirth = (TextView) findViewById(R.id.infoBirth);

        TextView tvAddress = (TextView) findViewById(R.id.infoAddress);

        TextView tvEmail = (TextView) findViewById(R.id.infoEmail);

        TextView tvIdNum = (TextView) findViewById(R.id.infoIdentity);

        TextView tvCompany = (TextView) findViewById(R.id.infoCompany);

        TextView tvPosition = (TextView) findViewById(R.id.infoPosition);

        TextView tvRealName = (TextView) findViewById(R.id.infoRealName);

        TextView tvWorkExperience = (TextView) findViewById(R.id.infoWorkExperience);

        TitleBarView mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);


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
