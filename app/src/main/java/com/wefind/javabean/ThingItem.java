package com.wefind.javabean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 物品类bean，用来配合BMOB进行数据的传递
 */
public class ThingItem extends BmobObject implements Serializable {
    private String thingName;
    private String describe;
    private String picurl;
    private String userId;
    private int state;//0:lost,1:finder,2:hunting
    private String addTime;
    private String place;
    private String typeCode;

    public ThingItem() {
    }

    public ThingItem(String thingName, String describe, String picurl, String userId, int state, String addTime, String place, String typeCode) {

        this.thingName = thingName;
        this.describe = describe;
        this.picurl = picurl;
        this.userId = userId;
        this.state = state;
        this.addTime = addTime;
        this.place = place;
        this.typeCode = typeCode;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
