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

public class RecommendActivity extends Activity {

	int isNetwork = 1;
	@InjectView(R.id.layout_main_body)
	LinearLayout main_body;
	private LJWebView recommendWebView;
	SharedPreferences sp;

	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;
//	String url = "http://192.168.1.229/test/page/description.php?client=app";

	// String url =
	// "http://192.168.1.229/test/cp/index.php?action=explanation&client=app&user_auth=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mbti_recommend);
		ButterKnife.inject(this);
		recommendWebView = (LJWebView) findViewById(R.id.recommendWebView);
		sp = getSharedPreferences("login", 0);
		// sp.getString("user_auth", null);
		// String url1 = url + sp.getString("token", null);

		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("测评说明");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initWebview(ConstUtil.DESCRIPTION);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebview(String url) {
		recommendWebView.setVisibility(View.VISIBLE);
		recommendWebView.setProgressStyle(LJWebView.Circle);
		recommendWebView.setBarHeight(8);
		recommendWebView.setClickable(true);
		recommendWebView.setUseWideViewPort(true);
		// 设置可以支持缩放
		recommendWebView.setSupportZoom(false);
		recommendWebView.setBuiltInZoomControls(true);
		recommendWebView.setJavaScriptEnabled(true);
		recommendWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		recommendWebView.setWebViewClient(new WebViewClient() {
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
				recommendWebView.setVisibility(View.GONE);
				/*
				 * Toast.makeText(RecommendActivity.this, description,
				 * Toast.LENGTH_SHORT).show();
				 */
			}

			// 页面加载完
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// recommendWebView.setVisibility(View.VISIBLE);
			}
		});
		recommendWebView.loadUrl(url);
	}

	@Override
	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		 * if ((keyCode == KeyEvent.KEYCODE_BACK) &&
		 * recommendWebView.canGoBack()) { recommendWebView.goBack(); return
		 * true; }
		 */
		return super.onKeyDown(keyCode, event);
	}

}
