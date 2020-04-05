package com.prod.alarm;

import android.os.Bundle;
import android.webkit.WebView;

public class LogActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView web = new WebView(this);
		web.loadData(getIntent().getStringExtra("data"), "text/html", "UTF-8");
		setContentView(web);
	}
}
