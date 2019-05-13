package com.wefind;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.wefind.javabean.ThingItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState,PersistableBundle persistentState) {

    }

    public void noticeFromBmob(String url, ThingItem thingItem){}

    public void noticeFromBmob(ArrayList<ThingItem> thingItems){}
}
