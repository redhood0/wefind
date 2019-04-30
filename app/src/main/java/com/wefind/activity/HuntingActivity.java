package com.wefind.activity;

import android.os.Bundle;


import com.wefind.BaseActivity;
import com.wefind.R;

import androidx.annotation.Nullable;

public class HuntingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.hunting);
    }
}
