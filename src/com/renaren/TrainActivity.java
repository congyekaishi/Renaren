package com.renaren;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.renaren.tools.ConstUtil;
import com.renaren.view.LJWebView;
import com.renaren.view.TitleBarView;

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

public class TrainActivity extends Activity {
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;
	private LJWebView trainWebView;
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
//	String url = "http://www.renaren.com/page/train.php?client=app";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mbti_train);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("培训课程");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		trainWebView = (LJWebView) findViewById(R.id.trainWebView);
		initWebview(ConstUtil.TRAIN);
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) && trainWebView.canGoBack()) {
		 * trainWebView.goBack(); return true; }
		 */
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		trainWebView.setVisibility(View.VISIBLE);
		trainWebView.setProgressStyle(LJWebView.Circle);
		trainWebView.setBarHeight(8);
		trainWebView.setClickable(true);
		trainWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		trainWebView.setSupportZoom(false);
		trainWebView.setBuiltInZoomControls(true);
		trainWebView.setJavaScriptEnabled(true);
		trainWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		trainWebView.setWebViewClient(new WebViewClient() {
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
				trainWebView.setVisibility(View.GONE);
				main_body.setBackgroundResource(R.drawable.no_network);
				/*
				 * Toast.makeText(TrainActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

			// 页面加载完
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// trainWebView.setVisibility(View.VISIBLE);
			}
		});
		trainWebView.loadUrl(url);
	}
}
