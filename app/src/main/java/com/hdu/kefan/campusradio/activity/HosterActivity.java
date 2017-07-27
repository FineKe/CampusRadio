package com.hdu.kefan.campusradio.activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hdu.kefan.campusradio.R;

public class HosterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView actionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hoster);

        initViews();
        initEvents();
    }

    private void initEvents() {
        actionBack.setOnClickListener(this);
    }

    private void initViews() {
        actionBack= (ImageView) findViewById(R.id.activity_hoster_imageView_actionBack);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_hoster_imageView_actionBack:
                actionBack();
                break;
        }
    }

    private void actionBack() {
        this.finish();
    }
}
