package com.wefind.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wefind.R;
import com.wefind.adatper.FindDetailAdapter;
import com.wefind.javabean.FindDetailBean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    TextView tv_comprehensive_sort,tv_high_price_sort;
    RecyclerView rv_find_detail;
    List<FindDetailBean> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_fragmentpage,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        rv_find_detail = view.findViewById(R.id.rv_find_detail);
        tv_comprehensive_sort = view.findViewById(R.id.tv_comprehensive_sort);
        tv_high_price_sort = view.findViewById(R.id.tv_high_price_sort);
        initData();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_find_detail.setLayoutManager(manager);
        FindDetailAdapter adapter = new FindDetailAdapter(list,getContext());
        rv_find_detail.setAdapter(adapter);

        tv_comprehensive_sort.setOnClickListener(v->{
            tv_comprehensive_sort.setTextColor(Color.parseColor("#E57E44"));
            tv_high_price_sort.setTextColor(Color.parseColor("#cccccc"));
        });

        tv_high_price_sort.setOnClickListener(v->{
            tv_high_price_sort.setTextColor(Color.parseColor("#E57E44"));
            tv_comprehensive_sort.setTextColor(Color.parseColor("#cccccc"));
        });


    }

    private void initData(){
        list = new ArrayList<>();
        list.add(new FindDetailBean(R.mipmap.shoes_aj1,"丢失一双耐克aj1球鞋","昨天骑共享单车忙着去接女朋友，鞋子忘记在车篮里了，车子停在南大学校南校门门口，有捡到的朋友麻烦联系我，酬劳好说，感激不尽。",50));
        list.add(new FindDetailBean(R.mipmap.dog,"寻找走失柴犬，今年一岁半","丢失于王府井附近，毛色偏白看起来呆的一笔，你叫了他也不会有什么反应。",400));
        list.add(new FindDetailBean(R.mipmap.dog,"寻找走失柴犬，今年一岁半","丢失于王府井附近，毛色偏白看起来呆的一笔",400));
    }
}
