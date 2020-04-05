package com.prod.alarm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.content.Intent;
import com.google.firebase.iid.FirebaseInstanceId;
import android.media.MediaPlayer;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
	public static final String ALERT_ON = "com.prod.alarm.ALERT_ON";
	public static final String ALERT_OFF = "com.prod.alarm.ALERT_OFF";
	MediaPlayer mp = null;
	
	@Override
	public void onMessageReceived(RemoteMessage message) {
		super.onMessageReceived(message);
		try {
			String clickAction = message.getNotification().getClickAction();
			if (clickAction.equals(ALERT_ON)) {
				int alarmType = Integer.parseInt(message.getData().get("alarm_type"));
				if (alarmType == 1) {
					mp = MediaPlayer.create(this, R.raw.alarm1);
				} else if (alarmType == 2) {
					mp = MediaPlayer.create(this, R.raw.alarm2);
				} else if (alarmType == 3) {
					mp = MediaPlayer.create(this, R.raw.alarm3);
				} else if (alarmType == 4) {
					mp = MediaPlayer.create(this, R.raw.alarm4);
				} else if (alarmType == 5) {
					mp = MediaPlayer.create(this, R.raw.alarm5);
				} else if (alarmType == 6) {
					mp = MediaPlayer.create(this, R.raw.alarm6);
				}
				if (mp != null) {
					mp.start();
				}
			} else if (clickAction.equals(ALERT_OFF)) {
				if (mp != null) {
					mp.stop();
					mp.release();
					mp = null;
				}
			}
		} catch (Exception e) {}
	}
}
