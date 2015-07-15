package com.renaren;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.renaren.beans.VersionInfo;
import com.renaren.tools.ConstUtil;
import com.renaren.tools.HttpUtil;
import com.renaren.tools.UIHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 启动页面
 */
public class Start extends Activity {
    // 创建handler对象
    private Handler handler;
    // 创建接口Runnable
    private Runnable updateThread;
    private ImageView startloading;
    private Dialog dialog;

    public String m_appNameStr; //下载到本地要给这个APP命的名字
    public ProgressDialog m_progressDlg;
    public Handler m_mainHandler;
    public VersionInfo vi;
    public boolean isUpdate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        isUpdate = false;
        m_mainHandler = new Handler();
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_appNameStr = "Renaren.apk";
        thread.start();
        /* 加载 */
        // startloading = (ImageView) findViewById(R.id.startLoading);
        /* 开启软件，几秒后自动跳转。 */
        // 更新UI界面，要是有Handler类
        // 创建handler对象
        handler = new Handler();

        // 创建接口Runnable 线程
        updateThread = new Runnable() {
            public void run() {
                if (!UIHelper.checkNetState(Start.this)) {
                    netDialog();
                    UIHelper.ToastMessage(Start.this, "网络未连接,请连接网络");
                } else {
                    UIHelper.ToastMessage(Start.this, "网络已连接");
                    if (isUpdate) {
                        doNewVersionUpdate();
                    } else {
                        Intent intent = new Intent(Start.this,
                                LoginActivity.class);
                        // 要跳转的界面
                        startActivity(intent);
                        Start.this.finish();
                    }

                }
                // 判断sdk版本，大于5才能用
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);

                if (version >= 5) {
                    // 自定义动画跳转
                    // 淡入浅出
                    overridePendingTransition(R.anim.in, R.anim.out);
                    // 执行handler的postdelayed，放到线程中
                }

            }

        };
        handler.postDelayed(updateThread, 2500);
    }

	/* 自定义的网络Dialog */

    public void netDialog() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("无法连接到网络，请检查网络设置")// 设置内容
                .setPositiveButton("网络设置",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent();
                                intent.setClassName("com.android.settings",
                                        "com.android.settings.Settings");
                                startActivity(intent);
                                System.exit(0);
                            }
                        })
                .setNegativeButton("退出",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        System.exit(0);
                    }
                })
                .create();// 创建
        //触屏对话框以外区域，对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        dialog.show();
    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (vi.version.equalsIgnoreCase(getVerName(getApplicationContext()))) {
                            isUpdate = false;
                        } else {
                            isUpdate = true;
                        }
                    }catch (Exception e){
                        isUpdate=false;
                    }

                    break;
                default:
                    break;
            }
        }
    };


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            String json;
            try {
                json = HttpUtil.getRequest(ConstUtil.VERUP);
                Gson gson = new Gson();
                vi = gson.fromJson(json, VersionInfo.class);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }// 从网络获取数据
            mhandler.sendEmptyMessage(1);
        }
    });


    /**
     * 提示更新新版本
     */
    private void doNewVersionUpdate() {
//        int verCode = getVerCode(getApplicationContext());
        String verName = getVerName(getApplicationContext());

        String str = "当前版本：" + verName + " ,发现新版本,是否更新？";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下载");
                                m_progressDlg.setMessage("请稍候...");
                                downFile(vi.download_url);  //开始下载
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                dialog.dismiss();
                                Intent intent = new Intent(Start.this,
                                        LoginActivity.class);
                                // 要跳转的界面
                                startActivity(intent);
                                Start.this.finish();
                            }
                        }).create();// 创建
        //触屏对话框以外区域，对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        dialog.show();
    }

    private void downFile(final String url) {
        m_progressDlg.show();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();

                    m_progressDlg.setMax((int) length);//设置进度条的最大值

                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                m_appNameStr);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            count += ch;
                            if (length > 0) {
                                m_progressDlg.setProgress(count);
                            }
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    down();  //告诉HANDER已经下载完成了，可以安装了
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 告诉HANDER已经下载完成了，可以安装了
     */
    private void down() {
        m_mainHandler.post(new Runnable() {
            public void run() {
                m_progressDlg.cancel();
                update();
            }
        });
    }

    /**
     * 安装程序
     */
    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), m_appNameStr)),
                "application/vnd.android.package-archive");//是文件类型，具体对应apk类型。
        startActivity(intent);
    }

    /**
     * 提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
//        int verCode = getVerCode(this);
        String verName = getVerName(this);
        String str = "当前版本:" + verName + "，已是最新版,无需更新!";
        Dialog dialog = new AlertDialog.Builder(this).setTitle("软件更新")
                .setMessage(str)// 设置内容
                .setPositiveButton("确定",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).create();// 创建
        //触屏对话框以外区域，对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        // 显示对话框
        dialog.show();
    }


//    /**
//     * 获取软件版本号
//     *
//     * @param context
//     * @return
//     */
//    public static int getVerCode(Context context) {
//        int verCode = -1;
//        try {
//            //注意："com.renaren"对应AndroidManifest.xml里的package="……"部分
//            verCode = context.getPackageManager().getPackageInfo(
//                    "com.renaren", 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("msg", e.getMessage());
//        }
//        return verCode;
//    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.renaren", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }


//    // 按两次返回键退出
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            dialog.dismiss();
//            Start.this.finish();
//            System.exit(0);
//
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
