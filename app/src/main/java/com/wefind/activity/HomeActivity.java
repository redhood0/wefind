package com.wefind.activity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.wefind.BaseActivity;
import com.wefind.R;

import androidx.appcompat.app.ActionBar;

public class HomeActivity extends BaseActivity {
    ImageView btn_find;
    ImageView btn_lost;
    ImageView btn_hunting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.home_page);
        //清理缓存
        PictureFileUtils.deleteCacheDirFile(HomeActivity.this);
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
        btn_find = findViewById(R.id.btn_find);
        btn_lost = findViewById(R.id.btn_lost);
        btn_hunting = findViewById(R.id.btn_hunting);
        //设置点击事件
        btn_find.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, FinderActivity.class)));
        btn_lost.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, LoserActivity.class)));
        btn_hunting.setOnClickListener(n -> startActivity(new Intent(HomeActivity.this, HuntingActivity.class)));

        //todo:delete
//        findViewById(R.id.btn_test).setOnClickListener(n ->
//                startActivity(new Intent(HomeActivity.this, TestHomeActivity.class)));
    }
}
