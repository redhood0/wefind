package com.wefind.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.adatper.ClassItemAdapter;
import com.wefind.context.ClassificationContext;
import com.wefind.javabean.ClassChooseBean;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ClassChooseActivity extends BaseActivity {
    public static final int RESULT_CODE = 1024;
    private TitleBar mTitleBar;
    private ListView lv_class;
    public static ClassChooseActivity classChooseActivity;
    public static ArrayList<ClassChooseBean> classList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_choose_page);
        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        classChooseActivity = this;
        classList = ClassificationContext.setAndGetClassList();
        init();
    }

    private void init() {
        lv_class = findViewById(R.id.lv_class);
        mTitleBar = findViewById(R.id.titleBar);

        //给ListView添加简单适配器数据（单行）
        //        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClassChooseActivity.this, R.layout.support_simple_spinner_dropdown_item, dataSource);
        //        //lv_class.setAdapter(adapter);
        //        //给ListView添加添加自定义适配器
        ClassItemAdapter adapter1 = new ClassItemAdapter(ClassChooseActivity.this, R.layout.class_choose_cell, classList);
        lv_class.setAdapter(adapter1);
        //设置item点击监听
        lv_class.setOnItemClickListener(((parent, view, position, id) -> processsListViewItemClick(adapter1, parent, view, position, id)));
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                //模拟按键
//                Instrumentation inst= new Instrumentation();
//                inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                finish();
            }
            @Override
            public void onTitleClick(View v) {}
            @Override
            public void onRightClick(View v) {}
        });
    }

    private void processsListViewItemClick(ClassItemAdapter adapter1, AdapterView<?> parent, View view, int position, long id) {
        //get value
        ClassChooseBean classChooseBean = classList.get(position);
        //设置单选
        for (ClassChooseBean item : classList) {
            item.setSelected(false);
        }
        classList.get(position).setSelected(true);
        adapter1.notifyDataSetChanged();
        //Toast.makeText(this, "" + classChooseBean.getClassName() + "," + classChooseBean.isSelected() + "," + id + "," + parent.getCount(), Toast.LENGTH_SHORT).show();
        //jumpBack-setResult
        Intent intent = new Intent();
        intent.putExtra("classChooseBean", classChooseBean);
        setResult(RESULT_CODE, intent);
        finish();
    }
}
