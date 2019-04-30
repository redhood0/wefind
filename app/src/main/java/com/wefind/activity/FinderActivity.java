package com.wefind.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.controller.FinderController;
import com.wefind.utils.CameraUtil;

import org.json.JSONException;

import java.io.FileNotFoundException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import io.reactivex.functions.Consumer;

public class FinderActivity extends BaseActivity {
    //权限工具
    final RxPermissions rxPermissions = new RxPermissions(this);

    Button btn_takePhoto;
    Button btn_choosePhote;
    Button btn_update;
    ImageView iv_img4show;
    TextView tv_result;
    EditText et_lostName;
    EditText et_lostDescribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.find_page);

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
        btn_takePhoto = findViewById(R.id.btn_takePhoto);
        btn_choosePhote = findViewById(R.id.btn_choosePhote);
        btn_update = findViewById(R.id.btn_update);
        iv_img4show = findViewById(R.id.iv_img4show);
       // tv_result = findViewById(R.id.tv_result);
        et_lostName = findViewById(R.id.et_lostName);
        et_lostDescribe = findViewById(R.id.et_lostDescribe);
        //设置点击事件
        //拍照事件
        btn_takePhoto.setOnClickListener(n -> {
            getPermission();
            CameraUtil cameraUtil = new CameraUtil();
            cameraUtil.dispatchTakePictureIntent(this,"com.wefind");
        });
        //上传事件（获取图片资源和描述，上传ai服务器和数据库）
        btn_update.setOnClickListener(n -> {
            //TODO:进行非空判断
            if(iv_img4show.getDrawable() == null){
                Toast.makeText(this, "上传图片不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String str_lostName = et_lostName.getText().toString();
            String str_lostDescribe = et_lostDescribe.getText().toString();
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            FinderController.uploadPic(str_lostName,str_lostDescribe,CameraUtil.currentPhotoPath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
        });

    }
    //获取权限
    public void getPermission(){
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            Log.i("PERMISSION","get permission success!");
                        } else {
                            Log.i("PERMISSION","get permission fale");
                            //Toast.makeText(getApplicationContext(),"拒绝将无法使用相机进行拍照",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//接受照相机返回的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        CameraUtil cameraUtil = new CameraUtil();
        cameraUtil.setPic(iv_img4show);
        try {
            cameraUtil.makeImgSmall(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
