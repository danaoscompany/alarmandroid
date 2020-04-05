package com.prod.alarm;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.text.method.PasswordTransformationMethod;
import android.app.ProgressDialog;
import android.content.Intent;
import org.json.JSONObject;
import com.prod.alarm.admin.HomeActivity;

public class LoginActivity extends BaseActivity {
	EditText emailField, passwordField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		emailField = findViewById(R.id.email);
		passwordField = findViewById(R.id.password);
		passwordField.setTransformationMethod(new PasswordTransformationMethod());
	}
	
	public void login(View view) {
		final String email = emailField.getText().toString().trim();
		final String password = passwordField.getText().toString();
		if (email.equals("")) {
			show("Mohon masukkan email");
			return;
		}
		if (password.equals("")) {
			show("Mohon masukkan kata sandi");
			return;
		}
		final ProgressDialog dialog = createDialog("Sedang masuk...");
		dialog.show();
		post(new Listener() {

				@Override
				public void onResponse(String response) {
					dialog.dismiss();
					if (response.equals("-1")) {
						show("Email atau kata sandi salah");
					} else {
						try {
							JSONObject obj = new JSONObject(response);
							String role = getString(obj, "role", "").trim();
							int userID = getInt(obj, "id", 0);
							write("user_id", userID);
							write("role", role);
							if (role.equals("user")) {
								startActivity(new Intent(LoginActivity.this, com.prod.alarm.user.HomeActivity.class));
								finish();
							} else if (role.equals("admin")) {
								startActivity(new Intent(LoginActivity.this, com.prod.alarm.admin.HomeActivity.class));
								finish();
							}
						} catch (Exception e) {
							
						}
					}
				}
		}, Constants.HOST+"/main/login", "email", email, "password", password);
	}
	
	public void signup(View view) {
		startActivity(new Intent(this, SignupActivity.class));
		finish();
	}
}
