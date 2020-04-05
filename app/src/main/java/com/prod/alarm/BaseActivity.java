package com.prod.alarm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.Toast;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import com.google.firebase.iid.FirebaseInstanceId;

public class BaseActivity extends Activity {
	public static BaseActivity instance;
	
	public BaseActivity() {
		instance = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	
	public void show(final String message) {
		runLater(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
				}
			});
	}
	
	public void show(final int messageID) {
		runLater(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(BaseActivity.this, messageID, Toast.LENGTH_LONG).show();
				}
			});
	}
	
	public ProgressDialog createDialog(String message) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setCancelable(false);
		dialog.setMessage(message);
		return dialog;
	}
	
	public void run(Runnable runnable) {
		new Thread(runnable).start();
	}

	public void runLater(Runnable runnable) {
		new Handler(Looper.getMainLooper()).post(runnable);
	}
	
	public OkHttpClient getOkHttpClient() {
		OkHttpClient client = new OkHttpClient();
		client.setReadTimeout(60, TimeUnit.SECONDS);
		client.setWriteTimeout(60, TimeUnit.SECONDS);
		client.setConnectTimeout(60, TimeUnit.SECONDS);
		return client;
	}

	public void get(final Listener listener, final String url) {
		run(new Runnable() {

				public void run() {
					try {
						OkHttpClient client = getOkHttpClient();
						Request request = new Request.Builder()
							.url(url)
							.build();
						final String response = client.newCall(request).execute().body().string();
						runLater(new Runnable() {

								public void run() {
									//log("Response: "+response);
									if (listener != null) {
										listener.onResponse(response);
									}
								}
							});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}

	public void post(final Listener listener, final String url, final String ...params) {
		run(new Runnable() {

				public void run() {
					try {
						OkHttpClient client = getOkHttpClient();
						MultipartBuilder builder = new MultipartBuilder()
							.type(MultipartBuilder.FORM);
						for (int i=0; i<params.length;) {
							if (params[i].equals("file")) {
								String fileName = params[i+1];
								String mimeType = params[i+2];
								String filePath = params[i+3];
								builder.addFormDataPart("file", fileName,
														RequestBody.create(MediaType.parse(mimeType), new File(filePath)));
								i += 4;
							} else {
								builder.addFormDataPart(params[i], params[i+1]);
								i += 2;
							}
						}
						Request request = new Request.Builder()
							.url(url)
							.post(builder.build())
							.build();
						final String response = client.newCall(request).execute().body().string();
						runLater(new Runnable() {

								public void run() {
									//log("Response: "+response);
									if (listener != null) {
										listener.onResponse(response);
									}
								}
							});
					} catch (final Exception e) {
						e.printStackTrace();
						runLater(new Runnable() {

								@Override
								public void run()
								{
									// TODO: Implement this method
									//show(e.getMessage());
								}


							});
					}
				}
			});
	}
	
	public interface Listener {
		
		void onResponse(String response);
	}
	
	public void updateFCMToken(String role) {
		String token = FirebaseInstanceId.getInstance().getToken();
		post(new Listener() {

				@Override
				public void onResponse(String message) {
				}
			}, Constants.HOST+"/"+role+"/update_fcm_token", "admin_id", ""+read("user_id", 0), "token", token);
	}
	
	public int read(String name, int defaultValue) {
		SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
		return sp.getInt(name, defaultValue);
	}
	
	public void write(String name, int value) {
		SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putInt(name, value);
		e.commit();
	}
	
	public void write(String name, String value) {
		SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString(name, value);
		e.commit();
	}
	
	public String getString(JSONObject obj, String name, String defaultValue) {
		return Util.getString(obj, name, defaultValue);
	}

	public int getInt(JSONObject obj, String name, int defaultValue) {
		return Util.getInt(obj, name, defaultValue);
	}
}
