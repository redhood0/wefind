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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wefind.R;
import com.wefind.activity.ChattingActivity;
import com.wefind.activity.FindDetailActivity;
import com.wefind.activity.MyFindActivity;
import com.wefind.javabean.FindDetailBean;
import com.wefind.javabean.ThingItem;

import java.util.List;

/**
 * @author cky
 * date 2019-10-23
 */
public class MyFindAdapter extends RecyclerView.Adapter<MyFindAdapter.ViewHolder> {
    List<ThingItem> list;
    Context context;

    public MyFindAdapter(List<ThingItem> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_find,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThingItem thingItem = list.get(position);
        Glide.with(context).load(thingItem.getPicurl()).placeholder(R.mipmap.dog).into(holder.iv_my_find);
        holder.tv_title.setText(thingItem.getThingName());
        holder.tv_describe.setText(thingItem.getDescribe());
        holder.tv_time.setText(thingItem.getAddTime());
        holder.tv_place.setText(thingItem.getPlace());

        holder.cv_my_find.setOnClickListener(v->context.startActivity(new Intent(context,FindDetailActivity.class)));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_my_find;
        TextView tv_title,tv_describe,tv_time,tv_place;
        CardView cv_my_find;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_my_find = itemView.findViewById(R.id.iv_my_find);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_describe = itemView.findViewById(R.id.tv_describe);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_place = itemView.findViewById(R.id.tv_place);
            cv_my_find = itemView.findViewById(R.id.cv_my_find);


        }
    }
}
