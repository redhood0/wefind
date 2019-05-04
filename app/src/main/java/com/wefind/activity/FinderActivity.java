package com.wefind.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wefind.BaseActivity;
import com.wefind.R;

import com.wefind.javabean.ClassChooseBean;
import com.wefind.utils.AiLikePicUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FinderActivity extends BaseActivity {
    public static final int CLASSCHOOSE_CODE = 1025;
    //权限工具
    final RxPermissions rxPermissions = new RxPermissions(this);

    private TitleBar mTitleBar;
    private Button btn_upload;
    private ImageView iv_img4show;
    private LinearLayout layout_takePhote;
    private ConstraintLayout consLayout_classChoose;
    private TextView tv_classChoose;
    private TextView tv_location;
    private EditText et_findName;
    private EditText et_findDescribe;
    private String imageCompressFilePath;
    private ClassChooseBean classChooseBean;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);
        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getPermission();
        init();
    }

    //初始化控件元素
    public void init() {
        //取消顶栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //初始化控件
        mTitleBar = findViewById(R.id.titleBar);
        btn_upload = findViewById(R.id.btn_upload);
        iv_img4show = findViewById(R.id.iv_img4show);
        et_findName = findViewById(R.id.et_findName);
        et_findDescribe = findViewById(R.id.et_findDescribe);
        consLayout_classChoose = findViewById(R.id.consLayout_classChoose);
        tv_classChoose = findViewById(R.id.tv_classChoose);
        layout_takePhote = findViewById(R.id.layout_takePhote);
        tv_location = findViewById(R.id.tv_location);
        progressBar = findViewById(R.id.skv_loading);
        //设置点击事件
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
            }
        });
        //拍照事件
        layout_takePhote.setOnClickListener(n -> startAlbum());
        //类型选择
        consLayout_classChoose.setOnClickListener(n -> jumpToClassChoose());
        //上传事件
        btn_upload.setOnClickListener(n -> uploadFindThing());
    }

    // 进入相册
    private void startAlbum() {
        PictureSelector.create(FinderActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.9f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(100, 100)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath()//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(2048)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(2000, 2000)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //跳转类型选择界面
    private void jumpToClassChoose() {
        Intent intent = new Intent(FinderActivity.this, ClassChooseActivity.class);
        startActivityForResult(intent, CLASSCHOOSE_CODE);
    }

    private void uploadFindThing() {
        //获取照片
        String imgPath = imageCompressFilePath;
        //获取名称
        String name = et_findName.getText().toString();
        //获取描述
        String describe = et_findDescribe.getText().toString();
        String place = tv_location.getText().toString();
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        //非空判断
        if (TextUtils.isEmpty(imgPath)) {
            Toast.makeText(this, "照片不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (classChooseBean == null || TextUtils.isEmpty(classChooseBean.getTypeCode())) {
            Toast.makeText(this, "类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取分类
        String typeCode = classChooseBean.getTypeCode();
        //获取的数据进行传递至ai服务器和个人数据库（异步操作）
        Observable.create(e -> e.onNext(AiLikePicUtil.uploadPic(name, describe, place, time, typeCode, imgPath)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(n -> showLoading())
                .subscribe(n -> {
                    deleteLoading();
                    Log.d("JSON!", "searchLostThing: " + n.toString());
                    //返回提示弹窗信息即可
                    Toast.makeText(this, "上传信息成功！", Toast.LENGTH_SHORT).show();
                });
    }

    //获取权限
    public void getPermission() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //classChoose result
        if (requestCode == CLASSCHOOSE_CODE && resultCode == ClassChooseActivity.RESULT_CODE) {
            classChooseBean = (ClassChooseBean) data.getSerializableExtra("classChooseBean");
            tv_classChoose.setText(classChooseBean.getClassName());
            tv_classChoose.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        //接受照相机返回的值
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    iv_img4show.setImageIcon(Icon.createWithContentUri(selectList.get(0).getCompressPath()));
                    imageCompressFilePath = selectList.get(0).getCompressPath();
                    break;
            }
        }
    }

    //展示loading，消失loading
    private void showLoading() {
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void deleteLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
