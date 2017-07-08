package com.hdu.kefan.campusradio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.activity.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView newUser;
    private TextView forgetPassword;
    private TextView login;
    private EditText userName;
    private EditText userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();
    }

    private void initEvents() {
        login.setOnClickListener(this);
    }

    private void initViews() {
        newUser= (TextView) findViewById(R.id.activity_login_textView_newuser);
        forgetPassword= (TextView) findViewById(R.id.activity_login_textView_forgetpassword);
        login= (TextView) findViewById(R.id.activity_login_textView_login);
        userName= (EditText) findViewById(R.id.activity_login_editText_username);
        userPassword= (EditText) findViewById(R.id.activity_login_editText_userpassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_login_textView_login:
                login();
                break;
        }
    }

    public void login() {
       startActivity(new Intent(this,MainActivity.class));
    }
}
