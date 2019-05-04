package com.wefind.adatper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wefind.R;
import com.wefind.javabean.SearchResultBean;
import com.wefind.javabean.SearchResultBrief;
import com.wefind.javabean.SearchResultRootBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultItemAdapter extends RecyclerView.Adapter<SearchResultItemAdapter.VH> {

    //创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView tv_thing_name;
        public final TextView tv_thing_describe;
        public final TextView tv_place;
        public final TextView tv_findtime;
        public VH(View v) {
            super(v);
            tv_thing_name = v.findViewById(R.id.tv_thing_name);
            tv_thing_describe = v.findViewById(R.id.tv_thing_describe);
            tv_place = v.findViewById(R.id.tv_place);
            tv_findtime = v.findViewById(R.id.tv_findtime);
        }
    }

    private List<SearchResultBean> datas;
    public SearchResultItemAdapter(List<SearchResultBean> data) {
        this.datas = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view(Inflater 增加泵)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        VH vh = new VH(v);
        return vh;
    }

//适配渲染数据到View
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String brief = datas.get(position).getBrief();
        SearchResultBrief briefObj = JSON.parseObject(brief, SearchResultBrief.class);

        holder.tv_thing_name.setText(briefObj.getName());
        holder.tv_thing_describe.setText("描述："+briefObj.getDescribe());
        holder.tv_place.setText(briefObj.getPlace());
        holder.tv_findtime.setText(briefObj.getTime());

        //监听事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:delete later
                Log.d("RECYCLER-", "onClick: ===" + position);
                //item 点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        if(datas == null){
            return 0;
        }
        return datas.size();
    }

    /**
     * 增删动画
     */
    public void addNewItem() {
        if(datas == null) {
            datas = new ArrayList<>();
        }
        //datas.add(0, "new Item");
        ////更新数据集不是用adapter.notifyDataSetChanged()而是notifyItemInserted(position)与notifyItemRemoved(position) 否则没有动画效果。
        notifyItemInserted(0);
    }

    public void deleteItem() {
        if(datas == null || datas.isEmpty()) {
            return;
        }
        datas.remove(0);
        notifyItemRemoved(0);
    }
}
