package com.renaren.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renaren.R;
import com.renaren.util.SystemMethod;

public class TitleBarView extends RelativeLayout {

	private static final String TAG = "TitleBarView";
	private Context mContext;
	private Button btnLeft;
	private TextView tv_center;
	private TextView tv_right;
	public TitleBarView(Context context){
		super(context);
		mContext=context;
		initView();
	}
	
	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btnLeft=(Button) findViewById(R.id.title_btn_left);
		tv_center=(TextView) findViewById(R.id.title_txt);
		tv_right=(TextView) findViewById(R.id.title_tv_right);
		
	}
	
	public void setCommonTitle(int LeftVisibility,int centerVisibility){
		btnLeft.setVisibility(LeftVisibility);
		tv_center.setVisibility(centerVisibility);	
	}
	
	public void setBtnLeft(int icon,String txtRes){
		Drawable img=mContext.getResources().getDrawable(icon);
		int height=SystemMethod.dip2px(mContext, 20);
		int width=img.getIntrinsicWidth()*height/img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnLeft.setText(txtRes);
		btnLeft.setCompoundDrawables(img, null, null, null);
	}
	
	public void setBtnLeft(int txtRes){
		btnLeft.setText(txtRes);
	}

	public void setRightText(String txtRes){
		tv_right.setText(txtRes);
	}
	
	public void setTitleText(String txtRes){
		tv_center.setText(txtRes);
	}
	
	public void setBtnLeftOnclickListener(OnClickListener listener){
		btnLeft.setOnClickListener(listener);
	}
	
	public void setTvRightOnclickListener(OnClickListener listener){
		tv_right.setOnClickListener(listener);
	}
	
	public void destoryView(){
		btnLeft.setText(null);
		tv_center.setText(null);
		tv_right.setText(null);
	}

}
