package com.wefind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wefind.R;
import com.wefind.activity.FinderActivity;
import com.wefind.activity.HuntingActivity;
import com.wefind.activity.LoserActivity;
import com.wefind.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private ImageView btn_find;
    private ImageView btn_lost;
    private ImageView btn_hunting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragmentpage, container, false);
        init(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //初始化控件
    private void init(View v) {
        //初始化按钮
        btn_find = v.findViewById(R.id.btn_find);
        btn_lost = v.findViewById(R.id.btn_lost);
        btn_hunting = v.findViewById(R.id.btn_hunting);
        //设置点击事件
        btn_find.setOnClickListener(n -> startActivity(new Intent(getActivity(), FinderActivity.class)));
        btn_lost.setOnClickListener(n -> startActivity(new Intent(getActivity(), LoserActivity.class)));
        btn_hunting.setOnClickListener(n -> startActivity(new Intent(getActivity(), HuntingActivity.class)));

        //todo:动态获取轮播图
        Integer[] images = {R.drawable.lunbo1, R.drawable.lunbo2, R.drawable.lunbo3, R.drawable.lunbo4};
        String[] title = {"寻找走丢的柴柴宝宝","有没有人找到我的Airpods","我朋友送的雨伞不见了，有没有人见到过","寻找丢失的耳机"};
        Banner banner = v.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(Arrays.asList(images));
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(Arrays.asList(title));
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setDelayTime(1500);
        //banner点击事件
        banner.setOnBannerListener(n -> {
            Log.d("LOGD", "banner.setOnBannerListener: " + n);
        });
        banner.start();
    }
}
