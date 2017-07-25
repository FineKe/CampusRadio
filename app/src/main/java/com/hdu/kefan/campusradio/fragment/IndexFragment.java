package com.hdu.kefan.campusradio.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algebra.sdk.API;
import com.algebra.sdk.ChannelApi;
import com.algebra.sdk.OnChannelListener;
import com.algebra.sdk.entity.Channel;
import com.algebra.sdk.entity.Contact;
import com.bumptech.glide.Glide;
import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.activity.StreamingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entity.User;

/**
 * Created by finefine.com on 2017/7/8.
 */

public class IndexFragment extends Fragment implements OnChannelListener{
    private static final int OK=0;
    public static Map<String,Integer> classmatesListenerPhotoMap;
    private ScheduledExecutorService scheduledExecutorService;

    private List<View> viewList;
    private int viewsId[]={R.drawable.adv_one,R.drawable.adv_two,R.drawable.adv_three};

    private RecyclerView recyclerViewChinnels;
    private RecyclerView recyclerViewClassmatesListening;
    private ViewPager viewPager;

    private RecyclerViewChinnelsAdapter chinnelsAdapter;

    private MyViewPagerAdapter pagerAdapter;


    private int currentIndex=0;

    private ChannelApi channelApi=null;
    private User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelApi= API.getChannelApi();
        if(channelApi==null)
        {
            System.out.println("channelAPI is null");
        }else
        {
            channelApi.setOnChannelListener(this);
        }
        user=new User(getContext());

        classmatesListenerPhotoMap=new HashMap<>();
        classmatesListenerPhotoMap.put("FM30807大学在路上",R.drawable.broadcast_one);
        classmatesListenerPhotoMap.put("FM1248142背着梦想起飞的女孩",R.drawable.broadcast_two);
        classmatesListenerPhotoMap.put("FM1370723反派影评",R.drawable.broadcast_three);
        classmatesListenerPhotoMap.put("FM1487919Nothing is impossible",R.drawable.broadcast_four);
        classmatesListenerPhotoMap.put("FM655814考研政治战",R.drawable.broadcast_five);
        classmatesListenerPhotoMap.put("FM1272386Read English For You",R.drawable.broadcast_six);
        classmatesListenerPhotoMap.put("FM2780838Ceciliahuang带你学英语",R.drawable.broadcast_seven);
        classmatesListenerPhotoMap.put("FM523000恋那一地日光",R.drawable.broadcast_eight);
        classmatesListenerPhotoMap.put("FM1413165私の日剧人生",R.drawable.broadcast_nine);
        classmatesListenerPhotoMap.put("FM3846478帝京日语么么哒",R.drawable.broadcast_ten);
        classmatesListenerPhotoMap.put("FM436045同EXO一起学韩语",R.drawable.broadcast_eleven);
        classmatesListenerPhotoMap.put("FM2767304faded..",R.drawable.broadcast_tweven);
        classmatesListenerPhotoMap.put("FM93605一念之差、遊走世間",R.drawable.threeten);
        classmatesListenerPhotoMap.put("FM366281若如初恋",R.drawable.broadcast_fortheen);
        classmatesListenerPhotoMap.put("FM587279我们最后没有在一起",R.drawable.broadcast_fiftheen);
        classmatesListenerPhotoMap.put("FM1601028当你跑步时在想些什么",R.drawable.broadcast_sixteen);
        classmatesListenerPhotoMap.put("FM3225106尘晨话说NBA",R.drawable.broadcast_seventeen);
        classmatesListenerPhotoMap.put("FM147208耳朵里的深夜食堂",R.drawable.broadcast_eighteen);
        classmatesListenerPhotoMap.put("FM1377030「食野」",R.drawable.broadcast_nintheen);
        classmatesListenerPhotoMap.put("FM3286952Manual 定音台",R.drawable.broadcast_twenthy);
        classmatesListenerPhotoMap.put("FM208945我还有民谣和孤独",R.drawable.broadcast_twenthy_one);
        classmatesListenerPhotoMap.put("FM1774594一听就会爱上的钢琴曲",R.drawable.broadcast_twenthy_two);
        classmatesListenerPhotoMap.put("FM1363758韩系清凉小纯音",R.drawable.broadcast_twenthy_three);
        classmatesListenerPhotoMap.put("FM147859臟言脏吾",R.drawable.broadcast_twenthy_four);
        classmatesListenerPhotoMap.put("FM251301经典文学名著",R.drawable.broadcast_twenthyfive);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_index,null);

        recyclerViewChinnels=view.findViewById(R.id.fragment_index_recyclerView_chinnels);
        recyclerViewClassmatesListening=view.findViewById(R.id.fragment_index_recyclerView_classmates_listening);
        viewPager=view.findViewById(R.id.fragment_index_viewPager);

        List<Hoster> hosters=new ArrayList<>();

        int hosterMainPhoto[]={R.drawable.hoster_one,R.drawable.hoster_two,R.drawable.hoster_three,R.drawable.hoster_four,
                R.drawable.hoster_five};

        String hosterName[]={"橘络feel","反派官方播客","你若安好","LARRY.Z","Ceciling"};

        for(int i=0;i<5;i++)
        {
            Hoster hoster= new Hoster();
            hoster.setId(i);
            hoster.setName(hosterName[i]);
            hoster.setMainPhoto(hosterMainPhoto[i]);
            hosters.add(hoster);
        }
        chinnelsAdapter=new RecyclerViewChinnelsAdapter(hosters,getContext());


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
//        gridLayoutManager.setSpanCount(3);

        recyclerViewChinnels.setLayoutManager(layoutManager);
        recyclerViewChinnels.setAdapter(chinnelsAdapter);
        createAdvViews();
        pagerAdapter=new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);

        recyclerViewClassmatesListening.setLayoutManager(gridLayoutManager);
        recyclerViewClassmatesListening.setAdapter(new RecyclerViewClassmatesListenseAdapter());

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);

//        channelApi.adverChannelsGet(Integer.valueOf(user.getUserId()));
        channelApi.adverChannelsGet(4850);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(scheduledExecutorService!=null)
        {
            scheduledExecutorService.shutdown();
        }
    }

    //------------------------------------------------频道监听实现--------------------------------------------------------------

    @Override
    public void onDefaultChannelSet(int i, int i1, int i2) {

    }

    @Override
    public void onAdverChannelsGet(int i, Channel channel,/* 默认频道*/ List<Channel> list) {
        if(list!=null)
        {
            for (Channel channel1:list)
            {
                System.out.println(channel1.name+":"+channel1.memberCount+":"+channel1.cid.getType()+":"+channel1.cid.getId());
            }
        }
        else
        {
            System.out.println(i);
        }

    }

    @Override
    public void onChannelListGet(int i, Channel channel, List<Channel> list) {

    }

    @Override
    public void onChannelMemberListGet(int i, int i1, int i2, List<Contact> list) {

    }

    @Override
    public void onChannelNameChanged(int i, int i1, int i2, String s) {

    }

    @Override
    public void onChannelAdded(int i, int i1, int i2, String s) {

    }

    @Override
    public void onChannelRemoved(int i, int i1, int i2) {

    }

    @Override
    public void onChannelMemberAdded(int i, int i1, List<Contact> list) {

    }

    @Override
    public void onChannelMemberRemoved(int i, int i1, List<Integer> list) {

    }

    @Override
    public void onPubChannelCreate(int i, int i1, int i2) {

    }

    @Override
    public void onPubChannelSearchResult(int i, List<Channel> list) {

    }

    @Override
    public void onPubChannelFocusResult(int i, int i1) {

    }

    @Override
    public void onPubChannelUnfocusResult(int i, int i1) {

    }

    @Override
    public void onPubChannelRenamed(int i, int i1) {

    }

    @Override
    public void onPubChannelDeleted(int i, int i1) {

    }

    @Override
    public void onCallMeetingStarted(int i, int i1, int i2, List<Contact> list) {

    }

    @Override
    public void onCallMeetingStopped(int i, int i1) {

    }

    public class RecyclerViewChinnelsAdapter extends RecyclerView.Adapter<RecyclerViewChinnelsAdapter.MyHolder>
    {
        private List<Hoster> hosterList;
        private Context mContext;

        public RecyclerViewChinnelsAdapter(List<Hoster> hosterList, Context mContext) {
            this.hosterList = hosterList;
            this.mContext = mContext;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_view_chinnels,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Glide.with(mContext).load(hosterList.get(position).getMainPhoto()).into(holder.imageView);
            holder.textView.setText(hosterList.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return hosterList.size();
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

    public class Hoster
    {
        private int id;
        private String name;
        private int mainPhoto;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMainPhoto() {
            return mainPhoto;
        }

        public void setMainPhoto(int mainPhoto) {
            this.mainPhoto = mainPhoto;
        }
    }


    public class RecyclerViewClassmatesListenseAdapter extends RecyclerView.Adapter<RecyclerViewClassmatesListenseAdapter.MyHolder>
    {
        List<Map.Entry<String,Integer>> list;

        public RecyclerViewClassmatesListenseAdapter() {
            list=new ArrayList<>();
            for(Map.Entry<String,Integer> entry:classmatesListenerPhotoMap.entrySet())
            {
                list.add(entry);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_index_recycle_view_classmates_listening_content,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
//            holder.imageView.setImageResource(list.get(position).getValue());
            Glide.with(getContext()).load(list.get(position).getValue()).into(holder.imageView);
            holder.textView.setText(list.get(position).getKey());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), StreamingActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder
        {   private LinearLayout linearLayout;
            private ImageView imageView;
            private TextView textView;
            public MyHolder(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.fragment_index_recyclerView_classmates_listening_imageView);
                textView=itemView.findViewById(R.id.fragment_index_recyclerView_classmates_listening_textView);
                linearLayout=itemView.findViewById(R.id.fragment_index_recyclerView_classmates_listening_linearLayout);
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
