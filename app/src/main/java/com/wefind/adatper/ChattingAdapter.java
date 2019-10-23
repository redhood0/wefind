package com.wefind.adatper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.wefind.R;
import com.wefind.javabean.ChattingMessage;

import java.util.List;

/**
 * @author cky
 * date 2019-07-09
 * 聊天适配器
 */
public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ViewHolder> {

    private List<ChattingMessage> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        ConstraintLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.left_layout);
            rightLayout = itemView.findViewById(R.id.right_layout);
            leftMsg = itemView.findViewById(R.id.left_msg);
            rightMsg = itemView.findViewById(R.id.right_msg);
        }
    }

    public ChattingAdapter(List<ChattingMessage> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ChattingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatting_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChattingAdapter.ViewHolder holder, int position) {
        ChattingMessage msg = msgList.get(position);
        switch (msg.getType()) {
            case RECEIVED://接收的消息
                holder.leftLayout.setVisibility(View.VISIBLE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.leftMsg.setText(msg.getContent());
                break;
            case SEND://发出的消息
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.VISIBLE);
                holder.rightMsg.setText(msg.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
