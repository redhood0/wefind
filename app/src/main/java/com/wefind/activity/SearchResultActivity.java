package com.wefind.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.adatper.SearchResultItemAdapter;
import com.wefind.decoration.Divider;
import com.wefind.decoration.EmptyRecyclerView;
import com.wefind.javabean.SearchResultBean;
import com.wefind.javabean.SearchResultBrief;
import com.wefind.javabean.SearchResultRootBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class SearchResultActivity extends BaseActivity {
    private EmptyRecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_page);
        //setTheme(R.style.AppTheme);
        init();
    }

    private void init() {
//        tv_thingName = findViewById(R.id.tv_thingName);
//        tv_thingDescribe = findViewById(R.id.tv_thingDescribe);
        String jsonStr = getIntent().getStringExtra("jsonStr");

        //TODO:规范数据格式。
        SearchResultRootBean resultRootBean = JSON.parseObject(jsonStr, SearchResultRootBean.class);
        String brief = resultRootBean.getResult().get(0).getBrief();
        Log.d("BRIEF", "------: " + brief);
        SearchResultBrief briefBean = JSON.parseObject(brief, SearchResultBrief.class);
        Log.d("BRIEF", "------: " + briefBean.getDescribe() + "," + briefBean.getName());
        initRecyclerView(resultRootBean.getResult());
    }

    private void initRecyclerView(List<SearchResultBean> beans) {
//        List<String> list = new ArrayList<>();
//        for(int i =0 ; i < 15; i ++){
//           list.add("飞利浦剃须刀（2012款）" + i);
//        }
        //创建默认垂直布局管理器
        mLayoutManager = new LinearLayoutManager(this);
        //创建适配器
        mAdapter = new SearchResultItemAdapter(beans);
        //获取recyclerView组件
        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.my_recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //禁用默认的change动画，解决数据更新造成的闪屏问题
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        //设置没有数据时的显示
        View view = findViewById(R.id.text_empty);
        mRecyclerView.setEmptyView(view);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //set divider
        //Divider divider = new Divider(new ColorDrawable(0xffE7E7E7), OrientationHelper.VERTICAL);
        //单位:px
        //divider.setMargin(0, 0, 0, 0);
        //divider.setHeight(0);
        //mRecyclerView.addItemDecoration(divider);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
