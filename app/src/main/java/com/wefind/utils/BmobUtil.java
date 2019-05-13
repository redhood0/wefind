package com.wefind.utils;

import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wefind.BaseActivity;
import com.wefind.activity.HomeActivity;
import com.wefind.javabean.Person;
import com.wefind.javabean.ThingItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class BmobUtil<T extends BmobObject> {
    BaseActivity activity;

    public void setActivity(BaseActivity act) {
        activity = act;
    }

    public void addMsg(T t) {
        t.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Log.d("BMOB", "添加数据成功，返回objectId为：" + objectId);

                    //Toast.makeText(HomeActivity.this, "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("BMOB", "创建数据失败：" + e.getMessage());
                    //Toast.makeText(HomeActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getPerssonMsg(String objId) {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(objId, new QueryListener<Person>() {
            @Override
            public void done(Person object, BmobException e) {
                if (e == null) {
                    Log.d("BMOB", "查询成功：" + object.getName());
                } else {
                    Log.d("BMOB", "查询失败：" + e.getMessage());
                }
            }
        });
    }

    public void updatePersonMsg(T t, String id) {
        t.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("BMOB", "更新成功：" + t.getUpdatedAt());
                } else {
                    Log.d("BMOB", "更新失败：" + e.getMessage());
                }
            }
        });
    }

    public void deletePersonMsg(T t) {
        t.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("BMOB", "查询成功：" + t.getUpdatedAt());

                } else {
                    Log.d("BMOB", "删除失败：" + e.getMessage());

                }
            }

        });
    }

//上传一个文件
    public void add1Pic(String filepath,ThingItem thingItem) {
        BmobFile bmobFile = new BmobFile(new File(filepath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    logd("上传文件成功:" + bmobFile.getFileUrl());
                    thingItem.setPicurl(bmobFile.getFileUrl());
                    addThingItem(thingItem);
                } else {
                    logd("上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                logd(">>>>:" + value + "%");
            }
        });
        return;
    }

    //传完文件，上传发现物品的数据到bmob库，然后把objId回传给ai服务器
    public void addThingItem(ThingItem thingItem){
        thingItem.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Log.d("BMOB", "添加数据成功，返回objectId为：" + objectId);
                    //todo:删掉提示，转移到下面方法中
                    activity.noticeFromBmob(objectId,thingItem);
                } else {
                    activity.noticeFromBmob("false",null);
                    Log.d("BMOB", "创建数据失败：" + e.getMessage());
                }
            }
        });
    }
    //通过objId的list，查出ThingItem
    public void getThingItem(List<String> objIds){

        BmobQuery<ThingItem> bmobQuery = new BmobQuery<>();

        bmobQuery.addWhereContainedIn("objectId",objIds);
        bmobQuery.findObjects(new FindListener<ThingItem>() {
            @Override
            public void done(List<ThingItem> items, BmobException e) {
                if (e == null) {
                    activity.noticeFromBmob((ArrayList<ThingItem>) items);
                    Log.d("BMOB", "查询成功：" + items.size());
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    private void logd(String s) {
        Log.d("BMOB", s);
    }

}
