package com.prod.alarm.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import com.prod.alarm.BaseActivity;
import com.prod.alarm.Constants;
import com.prod.alarm.LogActivity;
import com.prod.alarm.R;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {
	RelativeLayout press;
	View circle2, circle3, circle4, circle5, circle6, circle7, circle8;
	TextView typeView;
	int alarmActive = 0;
	int type = 1;
	boolean loading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);
		press = findViewById(R.id.press);
		circle2 = findViewById(R.id.circle_2);
		circle3 = findViewById(R.id.circle_3);
		circle4 = findViewById(R.id.circle_4);
		circle5 = findViewById(R.id.circle_5);
		circle6 = findViewById(R.id.circle_6);
		circle7 = findViewById(R.id.circle_7);
		circle8 = findViewById(R.id.circle_8);
		typeView = findViewById(R.id.type);
		updateFCMToken("admin");
		press.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View view) {
					switchAlarm();
					return false;
				}
		});
		press.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					switchAlarm();
				}
		});
		final ProgressDialog dialog = createDialog("Memuat...");
		dialog.show();
		post(new Listener() {

				@Override
				public void onResponse(String response) {
					try {
						JSONObject obj = new JSONArray(response).getJSONObject(0);
						alarmActive = getInt(obj, "alarm_active", 0);
						if (alarmActive == 1) {
							enableAlarm();
						} else {
							disableAlarm();
						}
						dialog.dismiss();
					} catch (Exception e) {}
				}
		}, Constants.HOST+"/main/get_by_id", "name", "admins", "id", ""+read("user_id", 0));
	}
	
	public void test(View view) {
		get(new Listener() {

				@Override
				public void onResponse(String response)
				{
					// TODO: Implement this method
					show(response);
				}
		}, Constants.HOST+"/main/fcm_test");
	}
	
	public void switchAlarm() {
		if (alarmActive == 0) {
			enableAlarm();
			alarmActive = 1;
		} else if (alarmActive == 1) {
			disableAlarm();
			alarmActive = 0;
		}
	}
	
	public void chooseAlarmType(View view) {
		PopupMenu menu = new PopupMenu(this, view);
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem menuItem) {
					int id = menuItem.getItemId();
					if (id == R.id.type_1) {
						type = 1;
						typeView.setText("Tipe 1");
					} else if (id == R.id.type_2) {
						type = 2;
						typeView.setText("Tipe 2");
					} else if (id == R.id.type_3) {
						type = 3;
						typeView.setText("Tipe 3");
					} else if (id == R.id.type_4) {
						type = 4;
						typeView.setText("Tipe 4");
					} else if (id == R.id.type_5) {
						type = 5;
						typeView.setText("Tipe 5");
					} else if (id == R.id.type_6) {
						type = 6;
						typeView.setText("Tipe 6");
					}
					return false;
				}
		});
		menu.getMenuInflater().inflate(R.menu.type, menu.getMenu());
		menu.show();
	}
	
	public void enableAlarm() {
		if (loading) {
			return;
		}
		loading = true;
		post(new Listener() {

				@Override
				public void onResponse(String response) {
					alarmActive = 1;
					loading = false;
				}
		}, Constants.HOST+"/admin/set_alarm",
				"admin_id", ""+read("user_id", 0),
				"on", "1", "alarm_type", ""+type);
		enableAlarmView();
	}
	
	public void enableAlarmView() {
		press.setBackgroundResource(R.drawable.circle_orange_01);
		circle2.setBackgroundResource(R.drawable.circle_orange_02);
		circle3.setBackgroundResource(R.drawable.circle_orange_03);
		circle4.setBackgroundResource(R.drawable.circle_orange_04);
		circle5.setBackgroundResource(R.drawable.circle_orange_05);
		circle6.setBackgroundResource(R.drawable.circle_orange_06);
		circle7.setBackgroundResource(R.drawable.circle_orange_07);
		circle8.setBackgroundResource(R.drawable.circle_orange_08);
	}
	
	public void disableAlarm() {
		if (loading) {
			return;
		}
		loading = true;
		post(new Listener() {

				@Override
				public void onResponse(String response) {
					alarmActive = 0;
					loading = false;
				}
			}, Constants.HOST+"/admin/set_alarm",
			"admin_id", ""+read("user_id", 0),
			"on", "0", "alarm_type", ""+type);
		disableAlarmView();
	}
	
	public void disableAlarmView() {
		press.setBackgroundResource(R.drawable.circle_01);
		circle2.setBackgroundResource(R.drawable.circle_02);
		circle3.setBackgroundResource(R.drawable.circle_03);
		circle4.setBackgroundResource(R.drawable.circle_04);
		circle5.setBackgroundResource(R.drawable.circle_05);
		circle6.setBackgroundResource(R.drawable.circle_06);
		circle7.setBackgroundResource(R.drawable.circle_07);
		circle8.setBackgroundResource(R.drawable.circle_08);
	}
}
