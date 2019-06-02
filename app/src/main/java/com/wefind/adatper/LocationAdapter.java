package com.wefind.adatper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.wefind.R;
import com.wefind.activity.LocationActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.VH>{
   private LocationActivity locationActivity;

    //创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView tv_placename;
        public final TextView tv_street;

        public VH(View v) {
            super(v);
            tv_placename = v.findViewById(R.id.tv_placename);
            tv_street = v.findViewById(R.id.tv_street);

        }
    }

    private List<Tip> datas;
    public LocationAdapter(LocationActivity locationActivity, List<Tip> data) {
        this.locationActivity = locationActivity;
        this.datas = data;
    }

    @NonNull
    @Override
    public LocationAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view(Inflater 增加泵)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        LocationAdapter.VH vh = new LocationAdapter.VH(v);
        return vh;
    }

    //适配渲染数据到View
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.VH holder, int position) {
        String name = datas.get(position).getName();
        String street = datas.get(position).getAddress();

        holder.tv_placename.setText(name);
        holder.tv_street.setText(street);

        //item点击监听事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tip t = datas.get(position);
                locationActivity.updateMapView(t.getPoint(),t.getName(),t.getDistrict()+t.getAddress());
                Log.d("RECYCLER-", "onClick: ===" + t.getAdcode()+t.getDistrict()+t.getPoint());
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
