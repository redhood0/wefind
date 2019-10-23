package com.wefind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wefind.R;
import com.wefind.activity.ChattingActivity;
import com.wefind.adatper.MessageAdapter;
import com.wefind.javabean.MessageBean;
import com.wefind.utils.ImmersiveStatusBarSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class MsgFragment extends Fragment {
    RecyclerView rv_message;
    private List<MessageBean> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_fragmentpage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view){
        rv_message = view.findViewById(R.id.rv_message);

        initMessage();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_message.setLayoutManager(manager);
        MessageAdapter messageAdapter = new MessageAdapter(list);
        messageAdapter.setOnItemClickListener((v, position) -> {
            startActivity(new Intent(getActivity(), ChattingActivity.class));
        });
        rv_message.addItemDecoration(new DividerItemDecoration(getContext(),HORIZONTAL));
        rv_message.setAdapter(messageAdapter);
    }

    public void initMessage(){
        list = new ArrayList<>();
        list.add(new MessageBean("官方客服","[链接]新功能上线啦，立刻体验！","10:21",R.mipmap.head_image));
        list.add(new MessageBean("Lisa","你好，请问这是你家走丢的猫吗","8:22",R.mipmap.head1));
        list.add(new MessageBean("Emila","谢谢你帮我找到我弄丢的身份证","10:23",R.mipmap.head2));
        list.add(new MessageBean("Cameron","明天赏脸吃个饭","昨天",R.mipmap.head3));
    }
}
