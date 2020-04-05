package com.prod.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent p2) {
		MediaPlayer mp = MediaPlayer.create(ctx, R.raw.alarm1);
		mp.setLooping(true);
        mp.start();
	}
}
