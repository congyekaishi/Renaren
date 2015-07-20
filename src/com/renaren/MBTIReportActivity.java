package com.renaren;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.mob.tools.utils.UIHandler;
import com.renaren.tools.ConstUtil;
import com.renaren.view.LJWebView;
import com.renaren.view.TitleBarView;

public class MBTIReportActivity extends Activity implements Callback {
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;

	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
	LJWebView reportWebView;

	String cpCode;

	String url;

	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mbti_report);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("测评报告");
		mTitleBarView.setRightText("分享");
		reportWebView = (LJWebView) findViewById(R.id.reportWebView);
		sp = getSharedPreferences("login", MODE_PRIVATE);
		Intent intent = getIntent();
		cpCode = intent.getStringExtra("cpCode");
		url = ConstUtil.REPORT_PRINT + cpCode;
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleBarView.setTvRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 分享点击事件
				OnekeyShare oks = new OnekeyShare();
				// // 分享时Notification的图标和文字
				// oks.setNotification(R.drawable.ic_launcher,
				// MainActivity.this.getString(R.string.app_name));
				// text是分享文本，所有平台都需要这个字段
				oks.setTitle("测评报告");
				oks.setTitleUrl(ConstUtil.REPORT_PRINT + cpCode);
				oks.setUrl(ConstUtil.REPORT_PRINT + cpCode);
				oks.setText("哎哟,挺准的");
				oks.setImageUrl(ConstUtil.REPORT_PRINT + cpCode);
				oks.setSilent(false);
				oks.setDialogMode();
				oks.show(v.getContext());
			}
		});

		initWebview(url);

		/* Toast.makeText(getApplicationContext(), url, 0).show(); */
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) && reportWebView.canGoBack())
		 * { reportWebView.goBack(); return true; }
		 */
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		reportWebView.setVisibility(View.VISIBLE);
		reportWebView.setProgressStyle(LJWebView.Circle);
		reportWebView.setBarHeight(8);
		reportWebView.setClickable(true);
		reportWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		reportWebView.setSupportZoom(true);
		// 设置出现缩放工具
		reportWebView.setBuiltInZoomControls(true);
		reportWebView.setJavaScriptEnabled(true);
		reportWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		reportWebView.setInitialScale(25);

		/*
		 * // 设置出现缩放工具 // 扩大比例的缩放 // 自适应屏幕
		 * reportWebView.getSettings().setAllowFileAccess(true);
		 * 
		 * reportWebView.setWebChromeClient(new WebChromeClient());
		 */
		reportWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				main_body.setBackgroundResource(R.drawable.no_network);
				reportWebView.setVisibility(View.GONE);
				/*
				 * Toast.makeText(MBTIReportActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

			// 页面加载完
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// reportWebView.setVisibility(View.VISIBLE);
			}
		});
		reportWebView.loadUrl(url);
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {

		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform palt, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		// String text = MainActivity.actionToString(msg.arg2);
		switch (msg.arg1) {
		case 1: {
			// 成功
			Toast.makeText(getApplicationContext(), "分享成功", 0).show();
		}
			break;
		case 2: {
			// 失败
			Toast.makeText(getApplicationContext(), "分享失败", 0).show();
		}
			break;
		case 3: {
			// 取消
			Toast.makeText(getApplicationContext(), "分享取消", 0).show();
		}
			break;
		}

		// Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
		return false;
	}

}
