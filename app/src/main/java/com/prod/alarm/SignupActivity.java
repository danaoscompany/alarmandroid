package com.prod.alarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.prod.alarm.admin.HomeActivity;
import android.widget.RadioButton;

public class SignupActivity extends BaseActivity {
	EditText nameField, emailField, passwordField;
	RadioButton commandant, user;
	int selectedRole = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		nameField = findViewById(R.id.name);
		emailField = findViewById(R.id.email);
		commandant = findViewById(R.id.commandant);
		user = findViewById(R.id.user);
		passwordField = findViewById(R.id.password);
		passwordField.setTransformationMethod(new PasswordTransformationMethod());
		commandant.setChecked(true);
		commandant.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					user.setChecked(false);
					selectedRole = 0;
				}
		});
		user.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					commandant.setChecked(false);
					selectedRole = 1;
				}
			});
	}

	public void signup(View view) {
		final String name = nameField.getText().toString().trim();
		final String email = emailField.getText().toString().trim();
		final String password = passwordField.getText().toString();
		if (name.equals("")) {
			show("Mohon masukkan nama lengkap");
			return;
		}
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
					show(response);
					dialog.dismiss();
					if (response.equals("1")) {
						show("Pendaftaran berhasil");
						startActivity(new Intent(SignupActivity.this, LoginActivity.class));
						finish();
					} else if (response.equals("-1")) {
						show("Email sudah digunakan");
					}
				}
			}, Constants.HOST+"/main/signup", "name", name, "email", email, "password", password, "role", ""+selectedRole);
	}
	
	public void login(View view) {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
