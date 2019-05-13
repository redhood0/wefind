package com.wefind.activity;

import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.fragment.DiscoverFragment;
import com.wefind.fragment.HomeFragment;
import com.wefind.fragment.MsgFragment;
import com.wefind.fragment.PersonFragment;
import com.wefind.javabean.Person;
import com.wefind.utils.BmobUtil;
import com.wefind.utils.LocationUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseActivity {
    //todo:delete
    final RxPermissions rxPermissions = new RxPermissions(this);

    public static HomeActivity homeActivity;

    private BottomNavigationViewEx bnve;
    //fragment list
    private DiscoverFragment discoverFragment;
    private MsgFragment msgFragment;
    private PersonFragment personFragment;
    private HomeFragment homeFragment;
    private List<Fragment> fragments = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = this;
        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.home_page);
        //清理缓存
        PictureFileUtils.deleteCacheDirFile(HomeActivity.this);
        //启动bmob
        //Bmob.initialize(this, "cf13d0a4f1a3b2f067ff3cfb19efc717");
//        Person p2 = new Person();
//        p2.setObjectId("1fa36cac40");
      //  BmobUtil<Person> bmobUtil = new BmobUtil<Person>();
//        Log.d("BMOB", "onCreate:== " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/1557288109979.jpg");
        //bmobUtil.add1Pic(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/IMG_20190503_212905.jpg");
        init();
    }

    //初始化控件元素
    public void init() {
        //取消顶栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getPermission();
        bnve = findViewById(R.id.bnve);
        //禁止底栏动画效果
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        //初始化home_fragment
        showFragment(homeFragment, HomeFragment.class, getSupportFragmentManager().beginTransaction());
        //底栏设置点击事件
        bnve.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            switch (item.getItemId()) {
                case R.id.action_home:
                    showFragment(homeFragment, HomeFragment.class, transaction);
                    break;
                case R.id.action_discover:
                    showFragment(discoverFragment, DiscoverFragment.class, transaction);
                    break;
                case R.id.action_msg:
                    showFragment(msgFragment, MsgFragment.class, transaction);
                    break;
                case R.id.action_my:
                    showFragment(personFragment, PersonFragment.class, transaction);
                    break;
            }
            return true;
        });
    }

    //获取定位权限
    public void getPermission() {
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            Log.i("PERMISSION", "get permission success!");
                        } else {
                            Log.i("PERMISSION", "get permission fale");
                            //Toast.makeText(getApplicationContext(),"拒绝将无法使用相机进行拍照",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //展示fragment
    private void showFragment(Fragment fragment, Class<? extends Fragment> fclass, FragmentTransaction transaction) {
        if (fragment == null) {
            try {
                Constructor c = fclass.getConstructor();
                fragment = (Fragment) c.newInstance();
                String className = fragment.getClass().getName();
                switch (className) {
                    case "com.wefind.fragment.DiscoverFragment":
                        discoverFragment = (DiscoverFragment) fragment;
                        break;
                    case "com.wefind.fragment.MsgFragment":
                        msgFragment = (MsgFragment) fragment;
                        break;
                    case "com.wefind.fragment.PersonFragment":
                        personFragment = (PersonFragment) fragment;
                        break;
                    case "com.wefind.fragment.HomeFragment":
                        homeFragment = (HomeFragment) fragment;
                        break;
                }
                transaction.add(R.id.frame_4_replace, fragment);
                fragments.add(fragment);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction transaction) {
        //todo:delete
        //Toast.makeText(this, "fragments.size():" + fragments.size(), Toast.LENGTH_SHORT).show();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }
}
