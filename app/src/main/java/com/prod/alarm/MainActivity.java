package com.prod.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.Calendar;
import com.prod.alarm.admin.HomeActivity;

public class MainActivity extends BaseActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		/*Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.MONTH, 2);
		c.set(Calendar.YEAR, 2020);
		c.set(Calendar.HOUR_OF_DAY, 16);
		c.set(Calendar.MINUTE, 3);
		c.set(Calendar.SECOND, 0);
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
			getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);*/
		init();
    }
	
	public void init() {
		int userID = read("user_id", 0);
		if (userID == 0) {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		} else {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
	}
}
