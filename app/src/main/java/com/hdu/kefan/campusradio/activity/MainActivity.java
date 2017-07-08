package com.hdu.kefan.campusradio.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
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

        initViews();
        initDatas();
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

    }

    @Override
    public void onClick(View view) {
        resetImageView();
        switch (view.getId())
        {
            case R.id.activity_main_bottom_bar_linearLayout_index:
                if(indexFragment==null)
                {
                    imageViewIndex.setImageResource(R.mipmap.index_pressed);
                    replaceFragment(new IndexFragment());
                }else
                {
                    imageViewIndex.setImageResource(R.mipmap.index_pressed);
                    replaceFragment(indexFragment);
                }
            break;

            case R.id.activity_main_bottom_bar_linearLayout_category:
                if(indexFragment==null)
                {
                    imageViewCategory.setImageResource(R.mipmap.category_pressed);
                    replaceFragment(new CategoryFragment());
                }else
                {
                    imageViewCategory.setImageResource(R.mipmap.category_pressed);
                    replaceFragment(categoryFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_collection:
                if(indexFragment==null)
                {
                    imageViewCollection.setImageResource(R.mipmap.collection_pressed);
                    replaceFragment(new CollectionFragment());
                }else
                {
                    imageViewCollection.setImageResource(R.mipmap.collection_pressed);
                    replaceFragment(collectionFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_person:
                if(indexFragment==null)
                {
                    imageViewPerson.setImageResource(R.mipmap.person_pressed);
                    replaceFragment(new PersonFragment());
                }else
                {
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

    }
}
