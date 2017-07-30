package com.hdu.kefan.campusradio.activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdu.kefan.campusradio.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HosterActivity extends AppCompatActivity implements View.OnClickListener{

    private String name;
    private int mainPhoto;
    private ImageView actionBack;
    private CircleImageView circleMainImageView;
    private TextView hosterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hoster);

        initDatas();
        initViews();
        initEvents();
    }

    private void initDatas() {
        name=getIntent().getStringExtra("name");
        mainPhoto=getIntent().getIntExtra("mainPhoto",0);
    }

    private void initEvents() {
        actionBack.setOnClickListener(this);
    }

    private void initViews() {
        actionBack= (ImageView) findViewById(R.id.activity_hoster_imageView_actionBack);
        circleMainImageView= (CircleImageView) findViewById(R.id.activity_hoster_circleImageView_mainPhoto);
        hosterName= (TextView) findViewById(R.id.activity_hoster_textView_name);

        Glide.with(this).load(mainPhoto).into(circleMainImageView);
        hosterName.setText(name);
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
