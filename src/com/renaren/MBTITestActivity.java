package com.renaren;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.renaren.tools.ConstUtil;
import com.renaren.view.LJWebView;
import com.renaren.view.TitleBarView;

import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MBTITestActivity extends Activity {
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;

	private LJWebView testWebView;

	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mbti_test);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("继续测评");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sp = getSharedPreferences("login", 0);
		Intent intent = getIntent();
		String cpCode = intent.getStringExtra("cpcode");
		testWebView = (LJWebView) findViewById(R.id.testWebView);

		String url = ConstUtil.DETAIL_CODENUM + cpCode;

		initWebview(url);

	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		String appCacheDir = this.getApplicationContext()
				.getDir("cache", Context.MODE_PRIVATE).getPath();
		testWebView.setVisibility(View.VISIBLE);
		testWebView.setProgressStyle(LJWebView.Circle);
		testWebView.setBarHeight(8);
		testWebView.setClickable(true);
		testWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		testWebView.setSupportZoom(false);
		testWebView.setBuiltInZoomControls(true);
		testWebView.setJavaScriptEnabled(true);
		testWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		testWebView.setAppCacheEnabled(true);
		testWebView.setAppCachePath(appCacheDir);
		testWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		synCookies(getApplicationContext(), ConstUtil.TEST);
		testWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				CookieManager cookieManager = CookieManager.getInstance();
				String cookie = cookieManager.getCookie(url);
				// Toast.makeText(getApplicationContext(), cookie, 0).show();
				// testWebView.setVisibility(View.VISIBLE);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				main_body.setBackgroundResource(R.drawable.no_network);
				testWebView.setVisibility(View.GONE);
				/*
				 * Toast.makeText(MBTITestActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

		});
		testWebView.loadUrl(url);
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) && testWebView.canGoBack()) {
		 * testWebView.goBack(); return true; }
		 */
		return super.onKeyDown(keyCode, event);
	}

	public void synCookies(Context context, String url) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeAllCookie();
		// cookieManager.removeSessionCookie();
		// String pre_user_auth= sp.getString("token", null);
		// String gmt = FormatDate.nDaysAfterNowDate(30).toGMTString();
		String a;

		cookieManager.setCookie(url,
				"pre_user_auth=" + URLEncoder.encode(sp.getString("token", null)) + ";domain="
						+ ConstUtil.COOKIE_DOMAIN + ";path=/;expires=3600");
		CookieSyncManager.getInstance().sync();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeCookie(getApplicationContext());
	}

	private void removeCookie(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}

}
