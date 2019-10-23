package com.wefind.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.wefind.R;
import com.wefind.adatper.ChattingAdapter;
import com.wefind.javabean.ChattingMessage;
import com.wefind.utils.ImmersiveStatusBarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-07-09
 * 聊天界面
 */
public class ChattingActivity extends Activity{
    List<ChattingMessage> chattingMessageList = new ArrayList<>();

    private EditText et_chatting_content;
    private RecyclerView rv_chatting;
    private Button btn_chatting_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_chatting);
        init();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        et_chatting_content = findViewById(R.id.et_chatting_content);
        rv_chatting = findViewById(R.id.rv_chatting);
        btn_chatting_send = findViewById(R.id.btn_chatting_send);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_chatting.setLayoutManager(linearLayoutManager);
        ChattingAdapter adapter = new ChattingAdapter(chattingMessageList);
        rv_chatting.setAdapter(adapter);

        btn_chatting_send.setOnClickListener(v -> {
            sendMessage(adapter);
        });

        rv_chatting.setOnTouchListener((v, event) -> {
            hideSoftKeyboard(ChattingActivity.this);
            return false;
        });
        initChatting();
    }

    private void initChatting() {
        chattingMessageList.add(new ChattingMessage("[链接]新功能上线啦，立刻体验！", ChattingMessage.TYPE.RECEIVED));
        chattingMessageList.add(new ChattingMessage("好的，我知道了", ChattingMessage.TYPE.SEND));
//        chattingMessageList.add(new ChattingMessage("我是 deniro，很高兴认识你^_^,我是 deniro，很高兴认识你^_^,我是 deniro，很高兴认识你^_^,我是 deniro，很高兴认识你^_^,我是 deniro，很高兴认识你^_^", ChattingMessage.TYPE.RECEIVED));

    }


    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void sendMessage(ChattingAdapter adapter) {
        String content = et_chatting_content.getText().toString();
        if ("".equals(content))
            return;
        chattingMessageList.add(new ChattingMessage(content, ChattingMessage.TYPE.SEND));

        int newSize = chattingMessageList.size() - 1;
        adapter.notifyItemInserted(newSize);
        et_chatting_content.setText("");
    }
}
