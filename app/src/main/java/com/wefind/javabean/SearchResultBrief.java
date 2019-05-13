package com.wefind.javabean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

//{"name":"guizi","bmobItemId":"e413e420d5","time":"05.12 02:42"}
public class SearchResultBrief {
    private String name;
    private String bmobItemId;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBmobItemId() {
        return bmobItemId;
    }

    public void setBmobItemId(String bmobItemId) {
        this.bmobItemId = bmobItemId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
