package com.wefind.adatper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wefind.R;
import com.wefind.activity.ClassChooseActivity;
import com.wefind.javabean.ClassChooseBean;


import java.util.List;

import androidx.core.content.ContextCompat;

public class ClassItemAdapter extends ArrayAdapter<ClassChooseBean> {
    private int resourceId;
    /**
     * Constructor
     *
     * @param context  listView所在的上下文，也就是ListView所在的Activity
     * @param resource Cell的布局资源文件
     * @param objects  Cell上要显示的数据list，也就是实体类集合
     */
    public ClassItemAdapter(Context context, int resource, List<ClassChooseBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    /**
     * @param position 当前设置的Cell行数，类似于iOS开发中的indexPath.row
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassChooseBean item = getItem(position);

        View classView = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView tv_classChoose = (TextView) classView.findViewById(R.id.tv_className);
        tv_classChoose.setText(item.getClassName().toString());
        //set selector color
        if(item.isSelected()){
            tv_classChoose.setTextColor(ContextCompat.getColor(getContext(),R.color.item_selector_orange));
        }
        return classView;
    }

}
