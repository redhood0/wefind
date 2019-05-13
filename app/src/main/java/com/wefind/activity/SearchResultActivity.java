package com.wefind.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.wefind.javabean.ThingItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class SearchResultActivity extends BaseActivity {
    private EmptyRecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    public static SearchResultActivity searchResultActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_page);
        searchResultActivity = this;
        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
    }

    private void init() {

        ArrayList<ThingItem> thingItems = (ArrayList<ThingItem>)getIntent().getSerializableExtra("ThingItems");

//todo:changeback
//        SearchResultRootBean resultRootBean = JSON.parseObject(jsonStr, SearchResultRootBean.class);
        initRecyclerView(thingItems);
    }

    private void initRecyclerView(List<ThingItem> beans) {
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
