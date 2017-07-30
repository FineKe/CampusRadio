package com.hdu.kefan.campusradio.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
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

import org.w3c.dom.Text;

/**
 * Created by finefine.com on 2017/7/19.
 */

public class HosterFragment extends Fragment{
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private static int pictures[]={R.drawable.hoster_collection_one,R.drawable.hoster_collection_two,R.drawable.hoster_collection_three};
    private static String names[]={"四颗小虎牙呀","睡不醒的话痨","曦晨"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_colletion_host,null);
        recyclerView=view.findViewById(R.id.fragment_collection_hoster_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyRecyclerViewAdapter());
        return view;
    }


    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder>
    {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collection_hoster_recycler_view_content,null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(names[position]);
            Glide.with(getContext()).load(pictures[position]).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class MyHolder extends RecyclerView.ViewHolder
        {   private ImageView imageView;
            private TextView textView;

            public MyHolder(View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.fragment_collection_hoster_recyclerView_view_content_textView_name);
                imageView=itemView.findViewById(R.id.fragment_collection_hoster_recyclerView_view_content_imageView);
            }
        }
    }



}
