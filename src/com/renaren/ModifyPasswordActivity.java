package com.renaren;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.renaren.tools.ConstUtil;
import com.renaren.view.LJWebView;
import com.renaren.view.TitleBarView;

public class ModifyPasswordActivity extends Activity {
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;

	LJWebView modifyPasswordWebView;

	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_password);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("找回密码");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sp = getSharedPreferences("login", 0);
		String url = ConstUtil.FORGET_PASSWORD + sp.getString("token", null);
		modifyPasswordWebView = (LJWebView) findViewById(R.id.modifyPasswordWebView);
		initWebview(url);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		modifyPasswordWebView.setVisibility(View.VISIBLE);
		modifyPasswordWebView.setProgressStyle(LJWebView.Circle);
		modifyPasswordWebView.setBarHeight(8);
		modifyPasswordWebView.setClickable(true);
		modifyPasswordWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		modifyPasswordWebView.setSupportZoom(false);
		modifyPasswordWebView.setBuiltInZoomControls(true);
		modifyPasswordWebView.setJavaScriptEnabled(true);
		modifyPasswordWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		modifyPasswordWebView.setWebViewClient(new WebViewClient() {
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
				modifyPasswordWebView.setVisibility(View.GONE);
				/*
				 * Toast.makeText(ModifyPasswordActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

			// 页面加载完
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// modifyPasswordWebView.setVisibility(View.VISIBLE);

			}
		});
		modifyPasswordWebView.loadUrl(url);
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) &&
		 * modifyPasswordWebView.canGoBack()) { modifyPasswordWebView.goBack();
		 * return true; }
		 */
		return super.onKeyDown(keyCode, event);
	}
}
