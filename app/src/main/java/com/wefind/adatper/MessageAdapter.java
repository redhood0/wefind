package com.wefind.adatper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.wefind.R;
import com.wefind.javabean.MessageBean;
import com.wefind.utils.ItemSlideHelper;

import java.util.List;

/**
 * @author cky
 * date 2019-07-04
 * 消息适配器
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements ItemSlideHelper.Callback {
    List<MessageBean> messageAdapterList;
    private RecyclerView mRecyclerView;
    private boolean isCollection = false;
    //私有属性
    private MessageAdapter.OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(MessageAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public MessageAdapter(List<MessageBean> messageAdapterList) {
        this.messageAdapterList = messageAdapterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageBean messageBean = messageAdapterList.get(position);
        holder.iv_message_head.setImageResource(messageBean.getHeadImgId());
        holder.tv_message_name.setText(messageBean.getName());
        holder.tv_message_content.setText(messageBean.getContent());
        holder.tv_message_date.setText(messageBean.getDate());

            holder.iv_message_collect.setOnClickListener(v -> {
            if (!isCollection) {
                holder.iv_message_collection.setVisibility(View.VISIBLE);
            }else {
                holder.iv_message_collection.setVisibility(View.GONE);
            }
            isCollection = !isCollection;
            });
        holder.iv_message_delete.setOnClickListener(v -> {
            messageAdapterList.remove(position);
            notifyItemRemoved(position);
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageAdapterList.size();
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof ConstraintLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(0).getLayoutParams().width
                    + viewGroup.getChildAt(1).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_message_head, iv_message_collection, iv_message_collect, iv_message_delete;
        TextView tv_message_name, tv_message_content, tv_message_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_message_head = itemView.findViewById(R.id.iv_message_head);
            iv_message_collection = itemView.findViewById(R.id.iv_message_collection);
            tv_message_name = itemView.findViewById(R.id.tv_message_name);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
            tv_message_date = itemView.findViewById(R.id.tv_message_date);
            iv_message_collect = itemView.findViewById(R.id.iv_message_collect);
            iv_message_delete = itemView.findViewById(R.id.iv_message_delete);
        }
    }
}
