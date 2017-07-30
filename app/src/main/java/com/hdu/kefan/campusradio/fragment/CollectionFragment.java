package com.hdu.kefan.campusradio.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.activity.StreamingActivity;

import java.util.List;

/**
 * Created by finefine.com on 2017/7/8.
 */

public class CollectionFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager=null;
    private FragmentTransaction fragmentTransaction=null;

    private ChannelFragment channelFragment=null;
    private HosterFragment hosterFragment=null;

    private FrameLayout frameLayout;
    private TextView textView1;
    private TextView textView2;
    private ImageView imageView1;
    private ImageView imageView2;

    private ImageView listening;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_collection,null);
        frameLayout=view.findViewById(R.id.fragment_collection_frameLayout);
        textView1=view.findViewById(R.id.fragment_collection_textView1);
        textView2=view.findViewById(R.id.fragment_collection_textView2);
        imageView1=view.findViewById(R.id.fragment_collection_imageView1);
        imageView2=view.findViewById(R.id.fragment_collection_imageView2);
        listening=view.findViewById(R.id.fragment_collection_title_bar_imageView);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);

        fragmentManager=getChildFragmentManager();

        fragmentTransaction=fragmentManager.beginTransaction();
        hideImageViews();
        imageView1.setVisibility(View.VISIBLE);
        if(channelFragment==null)
        {
            channelFragment=new ChannelFragment();
        }
        fragmentTransaction.replace(R.id.fragment_collection_frameLayout,channelFragment).commit();

        listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), StreamingActivity.class));
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        hideImageViews();
        fragmentTransaction=fragmentManager.beginTransaction();
        switch (view.getId())
        {
            case R.id.fragment_collection_textView1:
                imageView1.setVisibility(View.VISIBLE);
                if(channelFragment==null)
                {
                    channelFragment=new ChannelFragment();
                }
                fragmentTransaction.replace(R.id.fragment_collection_frameLayout,channelFragment);
                break;

            case R.id.fragment_collection_textView2:
                imageView2.setVisibility(View.VISIBLE);
                if(hosterFragment==null)
                {
                    hosterFragment=new HosterFragment();
                }
                fragmentTransaction.replace(R.id.fragment_collection_frameLayout,hosterFragment);
                break;
        }
        fragmentTransaction.commit();


    }

    public void hideImageViews()
    {
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
    }
}
