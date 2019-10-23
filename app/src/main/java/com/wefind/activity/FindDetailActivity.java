package com.wefind.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.wefind.R;

/**
 * @author cky
 * date 2019-10-23
 */
public class FindDetailActivity extends Activity {
    ImageView iv_chatting_head;
    Button btn_contact_owner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_find_detail);
        initView();
    }

    private void initView(){
        iv_chatting_head = findViewById(R.id.iv_chatting_head);
        btn_contact_owner = findViewById(R.id.btn_contact_owner);

        iv_chatting_head.setOnClickListener(v->this.finish());
        btn_contact_owner.setOnClickListener(v->{
            this.finish();
            startActivity(new Intent(FindDetailActivity.this,ChattingActivity.class));
        });
    }
}
