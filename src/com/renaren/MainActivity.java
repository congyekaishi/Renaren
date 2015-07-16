package com.renaren;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.renaren.beans.UserBean;
import com.renaren.tools.ConstUtil;
import com.renaren.tools.DataCleanManager;
import com.renaren.view.TitleBarView;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {
    IntentFilter mFilter = new IntentFilter();
    @InjectView(R.id.title_bar)
    TitleBarView mTitleBarView;

    ImageView defaultIcon;

    TextView tvUserName;
    TextView tvSign;

    RelativeLayout userInfo;
    RelativeLayout appraisal;
    RelativeLayout loginOut;
    RelativeLayout recommend;
    RelativeLayout bbs;
    RelativeLayout train;

    SharedPreferences sp;

    public Map<String, String> m = new HashMap<String, String>();

    public UserBean user;
    private long mExitTime;

    String token;
    String userName;
    String sign;
    String iconUrl;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE);
        mTitleBarView.setTitleText("欢迎回到人啊人");
        mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgLoaderInit();
        tvUserName = (TextView) findViewById(R.id.userName);
        tvSign = (TextView) findViewById(R.id.signature);

        defaultIcon = (ImageView) findViewById(R.id.defaultIcon);
        userInfo = (RelativeLayout) findViewById(R.id.userInfo);
        appraisal = (RelativeLayout) findViewById(R.id.appraisal);
        loginOut = (RelativeLayout) findViewById(R.id.loginOut);
        recommend = (RelativeLayout) findViewById(R.id.recommend);
        bbs = (RelativeLayout) findViewById(R.id.bbs);
        train = (RelativeLayout) findViewById(R.id.train);

        userInfo.setOnClickListener(listener);
        appraisal.setOnClickListener(listener);
        loginOut.setOnClickListener(listener);
        recommend.setOnClickListener(listener);
        bbs.setOnClickListener(listener);
        train.setOnClickListener(listener);

        sp = getSharedPreferences("login", 0);
        token = sp.getString("token", null);
        if (token == null) {
            tvUserName.setText("点击登录");
            tvUserName.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            userName = sp.getString("username", null);
            sign = sp.getString("sign", null);
            iconUrl = ConstUtil.TEST + sp.getString("avatar", null);
            tvUserName.setText(userName);
            tvSign.setText(sign);
            ImageLoader.getInstance().displayImage(iconUrl, defaultIcon,
                    options);

        }
    }

    OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.train:
                    Intent trainIntent = new Intent(getApplicationContext(),
                            TrainActivity.class);
                    startActivity(trainIntent);
                    break;
                case R.id.bbs:
                    Intent bbsIntent = new Intent(getApplicationContext(),
                            MBTIBbsActivity.class);
                    startActivity(bbsIntent);
                    break;
                case R.id.recommend:
                    Intent intentRecommd = new Intent(getApplicationContext(),
                            RecommendActivity.class);
                    startActivity(intentRecommd);
                    break;
                case R.id.userInfo:
                    Intent intentUI = new Intent(getApplicationContext(),
                            UserInfoActivity.class);
                    startActivity(intentUI);
                    break;
                case R.id.appraisal:
                    Intent intentIntroduce = new Intent(getApplicationContext(),
                            IntroduceActivity.class);
                    startActivity(intentIntroduce);
                    break;
                case R.id.loginOut:
                    sp = getSharedPreferences("login", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    // editor.remove("avatar");
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intent);

                    finish();
                    DataCleanManager.cleanSharedPreference(getApplicationContext());
                    break;
                default:
                    break;
            }
        }
    };

    public void imgLoaderInit() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();

    }

    // public static void cleanSharedPreference(Context context) {
    // deleteFilesByDirectory(new File("/data/data/"
    // + context.getPackageName() + "/shared_prefs"));
    // }

    // @Override
    // protected void onDestroy() {
    // // TODO Auto-generated method stub
    // super.onDestroy();
    // DataCleanManager.cleanSharedPreference(getApplicationContext());
    // DataCleanManager.cleanInternalCache(getApplicationContext());
    // DataCleanManager.cleanExternalCache(getApplicationContext());
    // }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出

                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime

            } else {
                System.exit(0);// 否则退出程序

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
