package com.wefind.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.adatper.MyFindAdapter;
import com.wefind.javabean.ThingItem;
import com.wefind.utils.BmobUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-23
 */
public class MyFindActivity extends BaseActivity {
    RecyclerView rv_my_find;
    BmobUtil bmobUtil;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_find);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initView();
    }

    private void initView(){
        rv_my_find = findViewById(R.id.rv_my_find);
        iv_back = findViewById(R.id.iv_back);
        bmobUtil = new BmobUtil();
        bmobUtil.setActivity(this);
        bmobUtil.getThingItemByUid("1234", 1);
        iv_back.setOnClickListener(v->this.finish());


    }

    @Override
    public void noticeFromBmob(ArrayList<ThingItem> thingItems) {
        Log.d("thingItemByUid",thingItems.size()+"");
        if (thingItems != null){
            GridLayoutManager manager = new GridLayoutManager(MyFindActivity.this,2);
            rv_my_find.setLayoutManager(manager);
            MyFindAdapter adapter = new MyFindAdapter(thingItems,MyFindActivity.this);
            rv_my_find.setAdapter(adapter);
        }
    }
}
