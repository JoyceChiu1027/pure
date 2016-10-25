package com.bupt.chengde.ui.me;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseAppVersion;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.Utils;
import com.bupt.chengde.util.WebserviceUtils;

/**
 * @author wyf
 * @version 1.0
 * @类 :版本信息
 */
@SuppressWarnings("deprecation")
public class VersionActivity extends BaseActivity {
	private static final String TAG = VersionActivity.class.getSimpleName();
	private LinearLayout backTextView;
	private TextView updateTextView;
	private TextView currentVersion, newVersion;
	private ResponseAppVersion checkAPPVersion = null;

	private int currentCode = -1;// 当前的版本号
	private String appNameStr; // 下载到本地要给这个APP的名字
	private Handler m_mainHandler;
	private ProgressDialog m_progressDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		initView();
		runHandler();
	}

	@Override
	protected void doBackGround() throws Exception {
		checkAPPVersion = WebserviceUtils.checkAPPVersion();

	}

	@Override
	protected void doForeGround() throws Exception {
		newVersion.setText("最新版本：" + checkAPPVersion.getVersionName());
	}

	private void initView() {
		appNameStr = "Chengde.apk";
		currentCode = Utils.getVersionCode(activity);
		m_progressDlg = new ProgressDialog(activity);
		m_mainHandler = new Handler();
		System.out.println("currentCode=========" + currentCode);
		backTextView = (LinearLayout) findViewById(R.id.top_back);
		updateTextView = (TextView) findViewById(R.id.ver_update);
		currentVersion = (TextView) findViewById(R.id.ver_current);// 当前版本
		newVersion = (TextView) findViewById(R.id.ver_new);// 最新版本
		((TextView) findViewById(R.id.top_name)).setText("版本信息");
		currentVersion.setText("当前版本：" + Utils.getAppVersionName(activity));

		backTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		updateTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkAPPVersion == null) {
					application.showToast("网络未返回数据，请稍后重试");
					return;
				}
				if (checkAPPVersion.getVersionCode() > Utils.getVersionCode(activity)) {
					String url = CodeConstants.URL_RES + checkAPPVersion.getApkUrl();// 软件更新包地址
					downFile(url);
				} else if (checkAPPVersion.getVersionCode() <= Utils.getVersionCode(activity)) {
					application.showToast("已是最新版本");
				}
			}
		});
	}

	private void downFile(final String url) {
		com.bupt.chengde.util.LogUtil.d(TAG, "发现新版本，开始下载");
		m_progressDlg.setTitle("正在下载");
		m_progressDlg.setMessage("请稍候...");
		m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		m_progressDlg.setCancelable(false);
		m_progressDlg.show();
		new Thread() {
			public void run() {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response;
					try {
						response = client.execute(get);
						LogUtil.d(TAG, "发现新版本，开始下载" + response.getStatusLine().getStatusCode());
						HttpEntity entity = response.getEntity();
						long length = entity.getContentLength();

						m_progressDlg.setMax((int) length);// 设置进度条的最大值

						InputStream is = entity.getContent();
						FileOutputStream fileOutputStream = null;
						if (is != null) {
							File file = new File(Environment.getExternalStorageDirectory(), appNameStr);
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
						down(); // 告诉HANDER已经下载完成了，可以安装了

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							client.getConnectionManager().shutdown();// 关闭连接
							// 这两种释放连接的方法都可以
						} catch (Exception e) {
							// TODO Auto-generated catch block
							com.bupt.chengde.util.LogUtil.e(TAG, "downFile catch exception"+e.getMessage());
						}
					}
				} else {
					error();// 告诉HANDER没有内存卡，下载失败
				}
			}
		}.start();
	}

	/**
	 * 告诉HANDER没有内存卡，下载失败
	 */
	private void error() {
		com.bupt.chengde.util.LogUtil.d(TAG, "下载失败");
		m_mainHandler.post(new Runnable() {
			public void run() {
				m_progressDlg.cancel();
				m_progressDlg.dismiss();
				application.showToast("没有内存卡，下载失败");
			}
		});
	}

	/**
	 * 告诉HANDER已经下载完成了，可以安装了
	 */
	private void down() {
		com.bupt.chengde.util.LogUtil.d(TAG, "下载完成");
		m_mainHandler.post(new Runnable() {
			public void run() {
				m_progressDlg.cancel();
				m_progressDlg.dismiss();
				update();
			}
		});
	}

	/**
	 * 安装程序
	 */
	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), appNameStr)),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
  public void downLoadApk(String url){
	  DownloadManager downloadManager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
	  String apkUrl=CodeConstants.URL_RES +url;
	  DownloadManager.Request request=new DownloadManager.Request(Uri.parse(apkUrl));
	  request.setDestinationInExternalPublicDir("chengde_cache", "Chengde.apk");
	  request.setMimeType("application/vnd.android.package-archive");

	  long downloadId=downloadManager.enqueue(request);
  }
}
