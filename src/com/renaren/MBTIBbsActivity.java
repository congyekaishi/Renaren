package com.renaren;

import com.renaren.tools.ConstUtil;
import com.renaren.view.LJWebView;
import com.renaren.view.TitleBarView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MBTIBbsActivity extends Activity {
	int isNetwork = 1;
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;

	private LJWebView bbsWebView;
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;

	// String url = "http://www.renaren.com/bbs/forum.php?client=app";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mbti_bbs);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("测评论坛");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		bbsWebView = (LJWebView) findViewById(R.id.bbsWebView);
		initWebview(ConstUtil.FORUM);
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) && bbsWebView.canGoBack()) {
		 * bbsWebView.goBack(); return true; }
		 */
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		bbsWebView.setVisibility(View.VISIBLE);
		bbsWebView.setProgressStyle(LJWebView.Circle);
		bbsWebView.setBarHeight(8);
		bbsWebView.setClickable(true);
		bbsWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		bbsWebView.setSupportZoom(false);
		bbsWebView.setBuiltInZoomControls(true);
		bbsWebView.setJavaScriptEnabled(true);
		bbsWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		bbsWebView.setWebViewClient(new WebViewClient() {
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
				bbsWebView.setVisibility(View.GONE);
				/*
				 * Toast.makeText(MBTIBbsActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

			// 页面加载完
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// bbsWebView.setVisibility(View.VISIBLE);
			}
		});
		bbsWebView.loadUrl(url);
	}

}
