package com.wefind.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.wefind.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PersonFragment extends Fragment {
    private MapView mMapView;
    private Button btn_setting;
    private EditText editText;
    private Button btn_getLocation;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    AMap aMap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_fragmentpage,container,false);
        mMapView = v.findViewById(R.id.map);
        btn_setting = v.findViewById(R.id.btn_setting);
        editText = v.findViewById(R.id.edit);

        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        btn_getLocation = v.findViewById(R.id.btn_getLocation);
        //测试坐标32.121457,118.934678，绘制点
        LatLng latLng = new LatLng(32.121457,118.934678);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("title-java").snippet("Default-snippet"));
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        btn_getLocation.setOnClickListener(n -> {
            Log.d("Location", "onCreateView: ");
        });
        aMap.setOnMapClickListener(n ->{
            Log.d("Location", "setOnMapClickListener: ");
            aMap.clear();
            LatLng latLng2 = n;
            Log.d("Location", latLng2.toString());

            MarkerOptions otMarkerOptions = new MarkerOptions();
            //otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.weizhi));
            otMarkerOptions.position(latLng2);
            final Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2).title("title-java").snippet("Default-snippet"));

            //getAddressByLatlng(latLng);
            aMap.addMarker(otMarkerOptions);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng2));

        });
        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}
