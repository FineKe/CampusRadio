package com.hdu.kefan.campusradio.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

    private IndexFragment indexFragment;
    private CategoryFragment categoryFragment;
    private CollectionFragment collectionFragment;
    private PersonFragment personFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_main_bottom_bar_linearLayout_index:
                if(indexFragment==null)
                {
                    replaceFragment(new IndexFragment());
                }else
                {
                    replaceFragment(indexFragment);
                }
            break;

            case R.id.activity_main_bottom_bar_linearLayout_category:
                if(indexFragment==null)
                {
                    replaceFragment(new CategoryFragment());
                }else
                {
                    replaceFragment(categoryFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_collection:
                if(indexFragment==null)
                {
                    replaceFragment(new CollectionFragment());
                }else
                {
                    replaceFragment(collectionFragment);
                }
                break;

            case R.id.activity_main_bottom_bar_linearLayout_person:
                if(indexFragment==null)
                {
                    replaceFragment(new PersonFragment());
                }else
                {
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
}
