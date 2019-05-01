package com.wefind.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.adatper.ClassItemAdapter;
import com.wefind.context.ClassificationContext;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ClassChooseActivity extends BaseActivity {
    private String[] dataSource = {"Android", "Google","Java", "Go","iOS", "Apple", "Objc", "Swift","Android", "Google","Java", "Go","iOS", "Apple", "Objc", "Swift"};

    ListView lv_class;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.class_choose_page);
        setContentView(R.layout.class_choose_page);
        setTheme(R.style.AppTheme);
        init();
    }

    private void init() {
        lv_class = findViewById(R.id.lv_class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClassChooseActivity.this, R.layout.support_simple_spinner_dropdown_item, dataSource);
        //给ListView添加简单适配器数据（单行）
        //lv_class.setAdapter(adapter);
        //添加自定义适配器
        ClassItemAdapter adapter1 = new ClassItemAdapter(ClassChooseActivity.this,R.layout.class_choose_cell, ClassificationContext.setAndGetClassList());
        lv_class.setAdapter(adapter1);
    }


}
