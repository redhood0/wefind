package com.wefind.activity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;

import com.wefind.BaseActivity;
import com.wefind.R;

import androidx.appcompat.app.ActionBar;

public class HomeActivity extends BaseActivity {
    Button btn_finder;
    Button btn_loser;
    Button btn_hunting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Thread.sleep(1800);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        setContentView(R.layout.activity_main);

        init();
    }

    //初始化控件元素
    public void init() {
        //取消顶栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //初始化按钮
        btn_finder = findViewById(R.id.btn_finder);
        btn_loser = findViewById(R.id.btn_loser);
        btn_hunting = findViewById(R.id.btn_hunting);
        //设置点击事件
        btn_finder.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, FinderActivity.class)));
        btn_loser.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, LoserActivity.class)));
        btn_hunting.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, HuntingActivity.class)));

        //todo:delete
//        findViewById(R.id.btn_test).setOnClickListener(n ->
//                startActivity(new Intent(HomeActivity.this, SearchResultActivity.class)));
    }
}
