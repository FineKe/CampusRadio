package com.hdu.kefan.campusradio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdu.kefan.campusradio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by finefine.com on 2017/7/19.
 */

public class ChannelFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private static final int [] pictures={R.drawable.channel_one,R.drawable.channel_two,R.drawable.channel_three};
    private List<ChannelInfo> channelInfos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelInfos=new ArrayList<>();
        ChannelInfo info1=new ChannelInfo();
        info1.setName("空白");
        info1.setClasses("情感频道");
        info1.setIntro("画一张空白的画，打一通无人的电话");
        info1.setPhoto(pictures[0]);

        ChannelInfo info2=new ChannelInfo();
        info2.setName("freestyle");
        info2.setClasses("音乐频道");
        info2.setIntro("我在吹空调，你在吹空调外机");
        info2.setPhoto(pictures[1]);

        ChannelInfo info3=new ChannelInfo();
        info3.setName("日本語を勉強する");
        info3.setClasses("语言频道");
        info3.setIntro("介绍日本文化，讲解日语学习");
        info3.setPhoto(pictures[2]);

        channelInfos.add(info1);
        channelInfos.add(info2);
        channelInfos.add(info3);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_colletion_channel,null);
        recyclerView=view.findViewById(R.id.fragment_collection_channel_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(new MyRecyclerViewAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder>
    {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collection_channel_recycler_view_content,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.className.setText(channelInfos.get(position).getName());
            holder.classes.setText(channelInfos.get(position).getClasses());
            holder.intro.setText(channelInfos.get(position).getIntro());
            Glide.with(getContext()).load(channelInfos.get(position).getPhoto()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return channelInfos.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder
        {
            private ImageView imageView;
            private TextView className;
            private TextView intro;
            private TextView classes;
            public MyHolder(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.fragment_collection_channel_recycler_view_content_imageView);
                className=itemView.findViewById(R.id.fragment_collection_channel_recycler_view_content_textView_className);
                intro=itemView.findViewById(R.id.fragment_collection_channel_recycler_view_content_textView_intro);
                classes=itemView.findViewById(R.id.fragment_collection_channel_recycler_view_content_textView_channeClass);
            }
        }
    }

    private class ChannelInfo
    {
        private String name;
        private String classes;
        private String intro;
        private int photo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getPhoto() {
            return photo;
        }

        public void setPhoto(int photo) {
            this.photo = photo;
        }
    }
}
