package com.wefind.adatper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wefind.R;
import com.wefind.activity.ChattingActivity;
import com.wefind.activity.FindDetailActivity;
import com.wefind.javabean.FindDetailBean;

import java.util.List;

/**
 * @author cky
 * date 2019-10-23
 */
public class FindDetailAdapter extends RecyclerView.Adapter<FindDetailAdapter.ViewHolder> {
    List<FindDetailBean> list;
    Context context;

    public FindDetailAdapter(List<FindDetailBean> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FindDetailBean findDetailBean = list.get(position);
        holder.iv_find.setImageResource(findDetailBean.getImgId());
        holder.tv_find_title.setText(findDetailBean.getTitle());
        holder.tv_find_subtitle.setText(findDetailBean.getContent());
        holder.tv_find_money.setText("赏金："+findDetailBean.getMoney()+"¥");

        holder.iv_find.setOnClickListener(v->{
            context.startActivity(new Intent(context, FindDetailActivity.class));
        });
        holder.btn_contact_owner.setOnClickListener(v->{
            context.startActivity(new Intent(context, ChattingActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_find;
        TextView tv_find_title,tv_find_subtitle,tv_find_money;
        Button btn_contact_owner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_find = itemView.findViewById(R.id.iv_find);
            tv_find_title = itemView.findViewById(R.id.tv_find_title);
            tv_find_subtitle = itemView.findViewById(R.id.tv_find_subtitle);
            tv_find_money = itemView.findViewById(R.id.tv_find_money);
            btn_contact_owner = itemView.findViewById(R.id.btn_contact_owner);
        }
    }
}
