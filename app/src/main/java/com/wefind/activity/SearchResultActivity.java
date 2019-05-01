package com.wefind.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.javabean.SearchResultRootBean;

import androidx.annotation.Nullable;

public class SearchResultActivity extends BaseActivity {
    TextView tv_thingName;
    TextView tv_thingDescribe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_page);
        setTheme(R.style.AppTheme);
        init();
    }

    private void init() {
        tv_thingName = findViewById(R.id.tv_thingName);
        tv_thingDescribe = findViewById(R.id.tv_thingDescribe);
        String jsonStr = getIntent().getStringExtra("jsonStr");

        //TODO:规范数据格式。
        SearchResultRootBean resultRootBean = (SearchResultRootBean) JSON.parseArray(jsonStr,SearchResultRootBean.class);
        tv_thingName.setText(resultRootBean.getResult().get(0).getBrief());

    }
}
