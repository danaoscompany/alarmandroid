package com.prod.alarm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import org.json.JSONObject;

public class Util {
	
	public static String getString(JSONObject obj, String name, String defaultValue) {
		try {
			String value = obj.getString(name);
			if (value == null || value.equals("null") || value.equals("NULL")) {
				return defaultValue;
			} else {
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static int getInt(JSONObject obj, String name, int defaultValue) {
		try {
			String value = obj.getString(name);
			if (value == null || value.equals("null") || value.equals("NULL")) {
				return defaultValue;
			} else {
				return Integer.parseInt(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	
	public static void run(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static void runLater(Runnable runnable) {
		new Handler(Looper.getMainLooper()).post(runnable);
	}
	
	public static void show(final Context ctx, final String message) {
		runLater(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
				}
		});
	}

	public static void show(Context ctx, int messageID) {
		Toast.makeText(ctx, messageID, Toast.LENGTH_LONG).show();
	}
}
