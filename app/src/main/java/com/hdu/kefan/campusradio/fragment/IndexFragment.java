package com.hdu.kefan.campusradio.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.kefan.campusradio.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by finefine.com on 2017/7/8.
 */

public class IndexFragment extends Fragment {
    private static final int OK=0;
    private ScheduledExecutorService scheduledExecutorService;

    private List<View> viewList;
    private int viewsId[]={R.drawable.adv_one,R.drawable.adv_two,R.drawable.adv_three};

    private RecyclerView recyclerViewChinnels;
    private ViewPager viewPager;

    private RecyclerViewChinnelsAdapter chinnelsAdapter;

    private MyViewPagerAdapter pagerAdapter;


    private int currentIndex=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_index,null);

        recyclerViewChinnels=view.findViewById(R.id.fragment_index_recyclerView_chinnels);
        viewPager=view.findViewById(R.id.fragment_index_viewPager);

        List<String> titles=new ArrayList<>();
        for(int i=0;i<25;i++)
        {
            titles.add("我是频道"+i);
        }
        chinnelsAdapter=new RecyclerViewChinnelsAdapter(titles);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewChinnels.setLayoutManager(layoutManager);
        recyclerViewChinnels.setAdapter(chinnelsAdapter);
        createAdvViews();
        pagerAdapter=new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);


    }

    @Override
    public void onStop() {
        super.onStop();
        if(scheduledExecutorService!=null)
        {
            scheduledExecutorService.shutdown();
        }
    }

    public class RecyclerViewChinnelsAdapter extends RecyclerView.Adapter<RecyclerViewChinnelsAdapter.MyHolder>
    {   private List<String> title;

        public RecyclerViewChinnelsAdapter(List<String> title) {
            this.title = title;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_view_chinnels,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(title.get(position));
        }

        @Override
        public int getItemCount() {
            return title.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder
        {
            private ImageView imageView;
            private TextView textView;
            public MyHolder(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.content_recycler_view_chinnels_imageView);
                textView=itemView.findViewById(R.id.content_recycler_view_chinnels_textView);
            }
        }
    }


    public void createAdvViews()
    {
        viewList=new ArrayList<>();
        for(int i:viewsId) {
            ImageView imageView=new ImageView(getContext());
            imageView.setImageResource(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewList.add(imageView);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter
    {   List<View> viewList;

        public MyViewPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=viewList.get(position);
            container.addView(view);
            return view;
        }
    }

    private class ViewPagerTask implements Runnable
    {

        @Override
        public void run() {
         currentIndex=(currentIndex+1)%viewList.size();
            Message message=new Message();
            message.what=OK;
            handler.sendMessage(message);
        }
    }


    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case OK:
                    viewPager.setCurrentItem(currentIndex);
                    break;
            }
        }
    };
}
