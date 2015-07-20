package com.renaren;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.renaren.beans.ImgUploadMsg;
import com.renaren.tools.HttpUtil;

public class FaceMasterActivity extends Activity {
	private static final int PHOTO_CAPTURE = 0x11;
	private static final int TIME_OUT = 10 * 1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
	private static String photoPath = "/sdcard/renaren/";
	
	Uri imageUri = Uri.fromFile(new File(Environment
			.getExternalStorageDirectory(), "image.jpg"));
	private Button photo, sc_photo;
	private ImageView img_photo;

//	private String newName = "laoli.jpg";
//	private String uploadFile = "/sdcard/AnBo/laolisb.jpg";
//	private String actionUrl = "http://192.168.1.109:8080/Photo/photoServlet";

	String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	String photoName = photoPath + name + ".jpg";
	
	private String urlUpLoad = "http://192.168.8.204/test/api/app/upload.php?action=upload_img&updatetype=face";//上传图片
    private String urlAnalysis = "http://192.168.8.204/test/api/app/face.php";
    private String jsonPost,jsonAnalysis;
	
    ImgUploadMsg msg;
    public Map<String, String> param = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.face_master);
		photo = (Button) findViewById(R.id.photo);
		sc_photo = (Button) findViewById(R.id.sc_photo);
		sc_photo.setOnClickListener(new sc_photo());
		img_photo = (ImageView) findViewById(R.id.imt_photo);
		// android.os.NetworkOnMainThreadException
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork() 
				.penaltyLog()
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				.penaltyLog()
				.penaltyDeath()
				.build());

		photo.setOnClickListener(new photo());
	}

	class sc_photo implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			dialog();
		}

	}

	class photo implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			File file = new File(photoPath);
			if (!file.exists()) { // 检查图片存放的文件夹是否存在
				file.mkdir(); // 不存在的话 创建文件夹
			}
			
			 File picture = new File(photoName);
			imageUri = Uri.fromFile(picture);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 这样就将文件的存储方式和uri指定到了Camera应用中
			startActivityForResult(intent, PHOTO_CAPTURE);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String sdStatus = Environment.getExternalStorageState();
		switch (requestCode) {
		case PHOTO_CAPTURE:
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
				Log.i("内存卡错误", "请检查您的内存卡");
			} else {
				BitmapFactory.Options op = new BitmapFactory.Options();
				// 设置图片的大小
				Bitmap bitMap = BitmapFactory.decodeFile(photoName);
				int width = bitMap.getWidth();
				int height = bitMap.getHeight();
				// 设置想要的大小
				int newWidth = 480;
				int newHeight = 640;
				// 计算缩放比例
				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;
				// 取得想要缩放的matrix参数
				Matrix matrix = new Matrix();
				matrix.postScale(scaleWidth, scaleHeight);
				// 得到新的图片
				bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
						matrix, true);
				// canvas.drawBitmap(bitMap, 0, 0, paint)
				// 防止内存溢出
				op.inSampleSize = 4; // 这个数字越大,图片大小越小.
				Bitmap pic = null;
				pic = BitmapFactory.decodeFile(photoName, op);
				img_photo.setImageBitmap(pic); // 这个ImageView是拍照完成后显示图片
				FileOutputStream b = null;
				;
				try {
					b = new FileOutputStream(photoName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				if (pic != null) {
					pic.compress(Bitmap.CompressFormat.JPEG, 50, b);
				}
			}
			break;
		default:
			return;
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(FaceMasterActivity.this);
		builder.setMessage("确认上传图片吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
//				File myCaptureFile = new File(photoName);
					new Thread(upload).start();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.create().show();
	}
	
	Runnable upload = new Runnable() {
        @Override
        public void run() {
            File myFile = new File(photoName);
            try {
            	jsonPost = uploadFile(myFile, urlUpLoad);
                Gson gson = new Gson();
                msg = gson.fromJson(jsonPost, ImgUploadMsg.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
            mHandler.sendEmptyMessage(1);
        }
    };
    
    Runnable Analysis = new Runnable() {
    	
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		param.put("action", "face_analysis");
    		param.put("img", msg.img);
    		param.put("user_auth", "VDFQZF9ZT20HNn4CBSZnUgUhB18xByglXWh8T0d1ZFRWaVdgCjUGYCgeeEYCMwI2WDtOKC4ULU97S2BQYQIBM1Q6UDNfaE8hBzZ+SQU2Zw8FZgdkMQsoHV1QfEo");
    		try {
    			jsonAnalysis = HttpUtil.postRequest(urlAnalysis, param);
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    		mHandler.sendEmptyMessage(2);
    	}
    };
    
    Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg){

            switch (msg.what){

                case 1 :
                    Toast.makeText(getApplicationContext(), jsonPost, Toast.LENGTH_LONG).show();
                    new Thread(Analysis).start();
                    break;
                case 2:
                	Toast.makeText(getApplicationContext(), jsonAnalysis, Toast.LENGTH_LONG).show();
                	break;
                default:
                    break;

            }
        }
    };

//	/* 上传文件至Server的方法 */
//	private void uploadFile() {
//		System.out.print("正在发送请求！");
//		String end = "\r\n";
//		String twoHyphens = "--";
//		String boundary = "*****";
//		try {
//			URL url = new URL(actionUrl);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			/* 允许Input、Output，不使用Cache */
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			con.setUseCaches(false);
//			/* 设置传送的method=POST */
//			con.setRequestMethod("POST");
//			/* setRequestProperty */
//			con.setRequestProperty("Connection", "Keep-Alive");
//			con.setRequestProperty("Charset", "UTF-8");
//			con.setRequestProperty("Content-Type",
//					"multipart/form-data;boundary=" + boundary);
//			/* 设置DataOutputStream */
//			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//			ds.writeBytes(twoHyphens + boundary + end);
//			ds.writeBytes("Content-Disposition: form-data; "
//					+ "name=\"file1\";filename=\"" + newName + "\"" + end);
//			ds.writeBytes(end);
//			/* 取得文件的FileInputStream */
//			FileInputStream fStream = new FileInputStream(uploadFile);
//			/* 设置每次写入1024bytes */
//			System.out.print("已经找到数据正在发送！");
//			int bufferSize = 1024*10;
//			byte[] buffer = new byte[bufferSize];
//			int length = -1;
//			/* 从文件读取数据至缓冲区 */
//			while ((length = fStream.read(buffer)) != -1) {
//				/* 将资料写入DataOutputStream中 */
//				ds.write(buffer, 0, length);
//			}
//			ds.writeBytes(end);
//			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
//			/* close streams */
//			fStream.close();
//			ds.flush();
//			/* 取得Response内容 */
//			InputStream is = con.getInputStream();
//			int ch;
//			StringBuffer b = new StringBuffer();
//			while ((ch = is.read()) != -1) {
//				b.append((char) ch);
//			}
//			/* 将Response显示于Dialog */
//			showDialog("上传成功");
//			/* 关闭DataOutputStream */
//			ds.close();
//		} catch (Exception e) {
//			System.out.print("网络出现异常！");
//			showDialog("上传失败");
//			e.printStackTrace();
//		}
//	}
	/* 显示Dialog的method */
	private void showDialog(String mess) {
		new AlertDialog.Builder(FaceMasterActivity.this).setTitle("提示")
				.setMessage(mess)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	/**
     * android上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */

                int res = conn.getResponseCode();
//                System.out.println("res=========" + res);
//                Toast.makeText(getApplicationContext(),res+" ",Toast.LENGTH_SHORT).show();
                Log.i("res", " " + res);
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                } else {
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
