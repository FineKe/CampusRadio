package com.hdu.kefan.campusradio.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.fragment.CategoryFragment;
import com.hdu.kefan.campusradio.fragment.CollectionFragment;
import com.hdu.kefan.campusradio.fragment.IndexFragment;
import com.hdu.kefan.campusradio.fragment.PersonFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int textNormalColor= Color.rgb(240,133,127);

    private FrameLayout frameLayout;

    private LinearLayout tableIndex;
    private LinearLayout tableCategory;
    private LinearLayout tableCollection;
    private LinearLayout tablePerson;

    private ImageView imageViewIndex;
    private ImageView imageViewCategory;
    private ImageView imageViewCollection;
    private ImageView imageViewPerson;

    private TextView textViewIndex;
    private TextView textViewCategory;
    private TextView textViewCollection;
    private TextView textViewPerson;

    private IndexFragment indexFragment;
    private CategoryFragment categoryFragment;
    private CollectionFragment collectionFragment;
    private PersonFragment personFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initDatas();

        initViews();

        initEvents();
    }

    private void initEvents() {
        tableIndex.setOnClickListener(this);
        tableCategory.setOnClickListener(this);
        tableCollection.setOnClickListener(this);
        tablePerson.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        fragmentManager=getSupportFragmentManager();//得到fragmentManager
        fragmentTransaction=fragmentManager.beginTransaction();//开启一个事务

    }

    /**
     * 初始化控件（绑定控件）
     */
    private void initViews() {
        frameLayout= (FrameLayout) findViewById(R.id.activity_main_frameLayout);
        tableIndex= (LinearLayout) findViewById(R.id.activity_main_bottom_bar_linearLayout_index);
        tableCategory= (LinearLayout) findViewById(R.id.activity_main_bottom_bar_linearLayout_category);
        tableCollection= (LinearLayout) findViewById(R.id.activity_main_bottom_bar_linearLayout_collection);
        tablePerson= (LinearLayout) findViewById(R.id.activity_main_bottom_bar_linearLayout_person);

        imageViewIndex= (ImageView) findViewById(R.id.activity_main_bottom_bar_imageView_index);
        imageViewCategory= (ImageView) findViewById(R.id.activity_main_bottom_bar_imageView_category);
        imageViewCollection= (ImageView) findViewById(R.id.activity_main_bottom_bar_imageView_collection);
        imageViewPerson= (ImageView) findViewById(R.id.activity_main_bottom_bar_imageView_person);

        textViewIndex= (TextView) findViewById(R.id.activity_main_bottom_bar_textView_index);
        textViewCategory= (TextView) findViewById(R.id.activity_main_bottom_bar_textView_category);
        textViewCollection= (TextView) findViewById(R.id.activity_main_bottom_bar_textView_collection);
        textViewPerson= (TextView) findViewById(R.id.activity_main_bottom_bar_textView_person);

        if(indexFragment==null)
        {
            indexFragment=new IndexFragment();
        }
        replaceFragment(indexFragment);
        resetImageView();
        imageViewIndex.setImageResource(R.mipmap.index_pressed);
        textViewIndex.setTextColor(Color.WHITE);


    }

    @Override
    public void onClick(View view) {
        resetImageView();
        resetTextViewColor();
        switch (view.getId())
        {
            case R.id.activity_main_bottom_bar_linearLayout_index:
                if(indexFragment==null)
                {
                    textViewIndex.setTextColor(Color.WHITE);
                    imageViewIndex.setImageResource(R.mipmap.index_pressed);
                    replaceFragment(new IndexFragment());
                }else
                {
                    textViewIndex.setTextColor(Color.WHITE);
                    imageViewIndex.setImageResource(R.mipmap.index_pressed);
                    replaceFragment(indexFragment);
                }
            break;

            case R.id.activity_main_bottom_bar_linearLayout_category:
                if(categoryFragment==null)
                {
                    textViewCategory.setTextColor(Color.WHITE);
                    imageViewCategory.setImageResource(R.mipmap.category_pressed);
                    replaceFragment(new CategoryFragment());
                }else
                {
                    textViewCategory.setTextColor(Color.WHITE);
                    imageViewCategory.setImageResource(R.mipmap.category_pressed);
                    replaceFragment(categoryFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_collection:
                if(collectionFragment==null)
                {
                    textViewCollection.setTextColor(Color.WHITE);
                    imageViewCollection.setImageResource(R.mipmap.collection_pressed);
                    replaceFragment(new CollectionFragment());
                }else
                {
                    textViewCollection.setTextColor(Color.WHITE);
                    imageViewCollection.setImageResource(R.mipmap.collection_pressed);
                    replaceFragment(collectionFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_person:
                if(personFragment==null)
                {
                    textViewPerson.setTextColor(Color.WHITE);
                    imageViewPerson.setImageResource(R.mipmap.person_pressed);
                    replaceFragment(new PersonFragment());
                }else
                {
                    textViewPerson.setTextColor(Color.WHITE);
                    imageViewPerson.setImageResource(R.mipmap.person_pressed);
                    replaceFragment(personFragment);
                }
                break;
        }
    }

    public void replaceFragment(Fragment fragment)
    {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_main_frameLayout,fragment).commit();
    }

    public void resetImageView()
    {
        imageViewCategory.setImageResource(R.mipmap.category_normal);
        imageViewIndex.setImageResource(R.mipmap.index_normal);
        imageViewCollection.setImageResource(R.mipmap.collection_normal);
        imageViewPerson.setImageResource(R.mipmap.person_normal);
    }

    public void resetTextViewColor()
    {
        textViewIndex.setTextColor(textNormalColor);
        textViewCategory.setTextColor(textNormalColor);
        textViewCollection.setTextColor(textNormalColor);
        textViewPerson.setTextColor(textNormalColor);
    }
}
