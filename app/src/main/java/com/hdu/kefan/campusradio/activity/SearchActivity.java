package com.hdu.kefan.campusradio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdu.kefan.campusradio.R;
import com.hdu.kefan.campusradio.fragment.IndexFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView cancel;
    private EditText search;
    private ListView listView;
    private List<Record> records;
    private MyListviewAdapter listviewAdapter;
    public static Map<String,Integer> classmatesListenerPhotoMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        initDatas();
        initViews();
        initEvents();
    }

    private void initDatas() {
        records=new ArrayList<>();
        listviewAdapter=new MyListviewAdapter(records);
        classmatesListenerPhotoMap= IndexFragment.classmatesListenerPhotoMap;
    }

    private void initEvents() {
        cancel.setOnClickListener(this);
    }

    private void initViews() {
        cancel= (TextView) findViewById(R.id.activity_search_textView_cancel);
        search= (EditText) findViewById(R.id.search_view_editText);
        listView= (ListView) findViewById(R.id.activity_search_listView);
        listView.setAdapter(listviewAdapter);

        listView.setDivider(null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SearchActivity.this,StreamingActivity.class);
                intent.putExtra("name",((Record)view.getTag()).getName());
                intent.putExtra("mainPhoto",((Record)view.getTag()).getMainphoto());
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                records.clear();

                if(!search.getText().toString().equals(""))
                {
                Pattern pattern=Pattern.compile(".*"+search.getText().toString()+"*.");
                Matcher matcher;

                for (Map.Entry<String,Integer> entry:classmatesListenerPhotoMap.entrySet())
                {
                    matcher=pattern.matcher(entry.getKey());
                    if (matcher.find())
                    {
                        Record record=new Record();
                        record.setName(entry.getKey());
                        record.setMainphoto(entry.getValue());
                        records.add(record);
                    }
                }

                }

                listviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_search_textView_cancel:
                finish();
            break;
        }
    }


    public class MyListviewAdapter extends BaseAdapter {

        List<Record> recordList;

        public MyListviewAdapter(List<Record> recordList) {
            this.recordList = recordList;
        }

        @Override
        public int getCount() {
            return recordList.size();
        }

        @Override
        public Object getItem(int i) {
            return recordList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view= LayoutInflater.from(SearchActivity.this).inflate(R.layout.activity_search_list_view_item,null);
            TextView name=view.findViewById(R.id.activity_search_list_view_item_textView_name);
            ImageView image=view.findViewById(R.id.activity_search_list_view_item_imageView_mainPhoto);
            Glide.with(SearchActivity.this).load(recordList.get(i).getMainphoto()).into(image);
            name.setText(recordList.get(i).getName());
            view.setTag(recordList.get(i));
            return view;
        }


    }

    public class Record
    {
        private String name;
        private int mainphoto;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMainphoto() {
            return mainphoto;
        }

        public void setMainphoto(int mainphoto) {
            this.mainphoto = mainphoto;
        }
    }
}
