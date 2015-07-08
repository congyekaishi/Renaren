package com.renaren;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.renaren.adapter.IntroduceAdapter;
import com.renaren.beans.AddCode;
import com.renaren.beans.MbtiCode;
import com.renaren.tools.ConstUtil;
import com.renaren.tools.HttpUtil;
import com.renaren.tools.ListItemClickHelp;
import com.renaren.tools.ListUtil;
import com.renaren.view.TitleBarView;

public class IntroduceActivity extends Activity {

	@InjectView(R.id.mbtiList)
	ListView LvMbti;
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
	@InjectView(R.id.newCode)
	EditText etNewCode;

	@InjectView(R.id.addCode)
	Button addCode;

	private IntroduceAdapter adapter;

	public SharedPreferences sp;
	private Map<String, String> param = new HashMap<String, String>();
	private Map<String, String> param1 = new HashMap<String, String>();
	private ArrayList<MbtiCode> MbtiCodes = new ArrayList<MbtiCode>();

	AddCode request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduce);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("我要测评");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		addCode.setOnClickListener(onClickListener);
		new Thread(getMBTIList).start();
	}

	Runnable getMBTIList = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			sp = getSharedPreferences("login", MODE_PRIVATE);
			String json;
			param.put("action", "cp_codeList");
			param.put("user_auth", sp.getString("token", null));
			try {
				json = HttpUtil.postRequest(ConstUtil.CP, param);
				Gson gson = new Gson();
				MbtiCodes = gson.fromJson(json,
						new TypeToken<ArrayList<MbtiCode>>() {
						}.getType());

			} catch (Exception e) {
				// TODO: handle exception
			}
			mHandler.sendEmptyMessage(1);
		}
	};

	Runnable addNewCode = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			sp = getSharedPreferences("login", MODE_PRIVATE);
			String json;
			param1.put("action", "check_code");
			param1.put("code_number", etNewCode.getText().toString());
			param1.put("user_auth", sp.getString("token", null));
			try {
				json = HttpUtil.postRequest(ConstUtil.CP, param1);
				Gson gson = new Gson();
				request = gson.fromJson(json, AddCode.class);

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
				adapter = new IntroduceAdapter(getApplicationContext(),
						MbtiCodes, new ListItemClickHelp() {
							@Override
							public void onClick(View item, int position,
									int which) {
								switch (which) {
								case R.id.testContinue:
									if (MbtiCodes.get(position).matching
											.equals("1")) {
										Intent intent = new Intent(
												getApplicationContext(),
												MBTIReportActivity.class);
										intent.putExtra("cpCode",
												MbtiCodes.get(position).code);
										startActivity(intent);
									} else {
										Intent intent = new Intent(
												getApplicationContext(),
												MBTITestActivity.class);
										intent.putExtra("cpcode",
												MbtiCodes.get(position).code);
										startActivity(intent);
									}
									break;
								default:
									break;
								}
							}
						});
				LvMbti.setAdapter(adapter);
				ListUtil.setListViewHeightBasedOnChildren(LvMbti);
				break;
			case 2:
				Toast.makeText(getApplicationContext(), request.msg, 0).show();
				new Thread(getMBTIList).start();
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.addCode:
				if (TextUtils.isEmpty(etNewCode.getText())) {
					Toast.makeText(getApplicationContext(), "请填写测评码！", 1)
							.show();
				} else {
					new Thread(addNewCode).start();
				}
				break;

			default:
				break;
			}
		}
	};
	
	
}
