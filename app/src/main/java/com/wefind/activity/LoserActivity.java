package com.wefind.activity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.javabean.ClassChooseBean;
import com.wefind.javabean.SearchResultRootBean;
import com.wefind.utils.AiLikePicUtil;

import org.json.JSONException;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

public class LoserActivity extends BaseActivity {
    public static final int CLASSCHOOSE_CODE = 1024;

    Button btn_choosePhote;
    Button btn_search;
    ImageView iv_img4show;
    LinearLayout layout_takePhote;
    ConstraintLayout consLayout_classChoose;
    TextView tv_classChoose;
    EditText et_lostName;
    EditText et_lostDescribe;
    private String imageCompressFilePath;
    private  ClassChooseBean classChooseBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loser_page);
        setTheme(R.style.AppTheme);
        init();
    }

    void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        iv_img4show = findViewById(R.id.iv_img4show);
        btn_choosePhote = findViewById(R.id.btn_choosePhote);
        layout_takePhote = findViewById(R.id.layout_takePhote);
        consLayout_classChoose = findViewById(R.id.consLayout_classChoose);
        tv_classChoose = findViewById(R.id.tv_classChoose);
        btn_search = findViewById(R.id.btn_search);
        et_lostName = findViewById(R.id.et_lostName);
        et_lostDescribe = findViewById(R.id.et_lostDescribe);
        //点击事件
        layout_takePhote.setOnClickListener(n -> startAlbum());
        consLayout_classChoose.setOnClickListener(n -> jumpToClassChoose());
        btn_search.setOnClickListener(n -> searchLostThing());
    }

    // 进入相册
    private void startAlbum() {
        PictureSelector.create(LoserActivity.this)
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

    // 跳转类型选择页面
    public void jumpToClassChoose() {
        Intent intent = new Intent(LoserActivity.this, ClassChooseActivity.class);
        startActivityForResult(intent, CLASSCHOOSE_CODE);
    }
    //传输信息至远端，进行信息匹配
    //TODO:显示匹配图片和对方
    public void searchLostThing(){
        //获取照片
        String imgPath = imageCompressFilePath;
        //获取名称
        String name = et_lostName.getText().toString();
        //获取描述
        String describe = et_lostDescribe.getText().toString();

        //非空判断
        if(TextUtils.isEmpty(imgPath)){
            Toast.makeText(this, "照片不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(classChooseBean == null || TextUtils.isEmpty(classChooseBean.getTypeCode())){
            Toast.makeText(this, "类型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取分类
        String typeCode= classChooseBean.getTypeCode();

        //获取的数据进行传递至ai服务器和个人数据库（异步操作）
        Observable.create(e -> e.onNext(AiLikePicUtil.selectPic(typeCode,imgPath)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(n -> {
                    //SearchResultRootBean bean = (SearchResultRootBean)n;
                    Log.d("JSON!", "searchLostThing: "+ n.toString());
                    //界面跳转
                    Intent intent = new Intent(LoserActivity.this,SearchResultActivity.class);
                    intent.putExtra("jsonStr",n.toString());
                    startActivity(intent);
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //classChoose result
        if (requestCode == CLASSCHOOSE_CODE && resultCode == ClassChooseActivity.RESULT_CODE) {
           classChooseBean = (ClassChooseBean) data.getSerializableExtra("classChooseBean");
            //Log.d("AAAAA-", classChooseBean.getClassName() + "-" + classChooseBean.isSelected() + "-" + classChooseBean.getTypeCode());
            tv_classChoose.setText(classChooseBean.getClassName());
            tv_classChoose.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        //photo pick result
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，
                    // 3.media.getCompressPath();为压缩后path
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //TODO:delete log
                    Log.d("TAG-path", "getCutPath(): " + selectList.get(0).getCutPath());
                    Log.d("TAG-path", "getCompressPath()" + new File(selectList.get(0).getCompressPath()));
                    Log.d("TAG-path", "getCompressSize" + new File(selectList.get(0).getCompressPath()).length());
                    iv_img4show.setImageIcon(Icon.createWithContentUri(selectList.get(0).getCompressPath()));
                    imageCompressFilePath = selectList.get(0).getCompressPath();
                    break;
            }
        }
    }
}
