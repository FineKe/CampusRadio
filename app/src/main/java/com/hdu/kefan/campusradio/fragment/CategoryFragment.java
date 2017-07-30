package com.hdu.kefan.campusradio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.activity.StreamingActivity;

/**
 * Created by finefine.com on 2017/7/8.
 */

public class CategoryFragment extends Fragment{
    private ImageView listening;

    private int [] pohoto={R.drawable.broadcast_twenthy,R.drawable.broadcast_twenthy_four,R.drawable.broadcast_five,
        R.drawable.broadcast_twenthy_two,R.drawable.broadcast_tweven,R.drawable.threeten};
    private int [] id={R.id.fragment_category_imageView_one,R.id.fragment_category_imageView_two,R.id.fragment_category_imageView_three,
            R.id.fragment_category_imageView_four,R.id.fragment_category_imageView_five,R.id.fragment_category_imageView_six};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_category,null);
        listening=view.findViewById(R.id.fragment_category_title_bar_imageView_listening);
        listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), StreamingActivity.class));



            }
        });

        for(int i=0;i<pohoto.length;i++)
        {
            Glide.with(getContext()).load(pohoto[i]).into((ImageView) view.findViewById(id[i]));
        }
        return view;
    }
}
