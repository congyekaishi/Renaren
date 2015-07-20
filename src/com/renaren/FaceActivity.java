package com.renaren;
import com.renaren.view.TitleBarView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class FaceActivity extends Activity {
	
	@InjectView(R.id.title_bar)
	TitleBarView mTitleBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.facetest);
		ButterKnife.inject(this);
		mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, "返回");
		mTitleBarView.setTitleText("面相大师");
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	} 
}
