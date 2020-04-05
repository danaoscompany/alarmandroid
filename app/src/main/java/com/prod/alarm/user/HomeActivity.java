package com.prod.alarm.user;

import com.prod.alarm.BaseActivity;
import android.os.Bundle;
import com.prod.alarm.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.prod.alarm.Constants;

public class HomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		updateFCMToken("user");
	}
}
