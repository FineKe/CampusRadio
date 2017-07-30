package com.hdu.kefan.campusradio.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.activity.StreamingActivity;

/**
 * Created by finefine.com on 2017/7/8.
 */

public class PersonFragment extends Fragment{
    private ImageView listening;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,null);
        listening=view.findViewById(R.id.fragment_person_title_bar_imageView_listening);
        listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), StreamingActivity.class));
            }
        });
        return view;
    }
}
