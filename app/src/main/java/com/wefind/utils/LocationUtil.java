package com.wefind.utils;


import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.route.DistanceSearch;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class LocationUtil {
    public static AMapLocationClient mLocationClient;
    private static AppCompatActivity activityContext;


    //注册activity context
    public static void setActivityContext(AppCompatActivity activity) {
        activityContext = activity;
    }


    //监听选项(时间，方式，精度，间隔等)
    public static AMapLocationClientOption getOption() {
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        return mLocationOption;
    }

    //获取定位信息
    public static void getLocationMsg(AMapLocationListener mLocationListener) {
        AMapLocationClientOption mLocationOption = getOption();
        //初始化定位
        mLocationClient = new AMapLocationClient(activityContext.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        return;
    }

    //具体地址转坐标和坐标转地址
    public static void locationToCoordinate() {
        GeocodeSearch geocodeSearch = new GeocodeSearch(activityContext);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                Log.d("Location", "onRegeocodeSearched: " + regeocodeResult.getRegeocodeAddress().getAdCode());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Log.d("Location", "geocodeResult: " + geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint());
            }
        });
        GeocodeQuery query = new GeocodeQuery("栖霞区任之东路1号靠近南京工业职业技术学院"
                , "江苏省南京市");
        geocodeSearch.getFromLocationNameAsyn(query);
    }
    //具体坐标转地址
    //public static void CoordinateToLocation

    //距离测量
    public static void distanceSearch(LatLonPoint start, LatLonPoint end, DistanceSearch.OnDistanceSearchListener distanceSearchListener) {
        DistanceSearch distanceSearch = new DistanceSearch(activityContext);
        distanceSearch.setDistanceSearchListener(distanceSearchListener);

        DistanceSearch.DistanceQuery query = new DistanceSearch.DistanceQuery();
        List<LatLonPoint> latLonPoints = new ArrayList<LatLonPoint>();
        latLonPoints.add(start);
        query.setOrigins(latLonPoints);
        query.setDestination(end);
//设置测量方式，支持直线和驾车
        query.setType(DistanceSearch.TYPE_DISTANCE);
        distanceSearch.calculateRouteDistanceAsyn(query);
    }

    //Poi输入提示
    public static void inputPrompt(String newText, RegeocodeAddress address, Inputtips.InputtipsListener listener){
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(newText, address.getCity());
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(activityContext, inputquery);
        inputTips.setInputtipsListener(listener);
        inputTips.requestInputtipsAsyn();
    }


}
