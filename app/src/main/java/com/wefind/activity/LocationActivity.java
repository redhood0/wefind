package com.wefind.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.wefind.BaseActivity;
import com.wefind.R;
import com.wefind.adatper.LocationAdapter;
import com.wefind.decoration.Divider;
import com.wefind.utils.LocationUtil;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class LocationActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener, AMap.OnMapClickListener, DistanceSearch.OnDistanceSearchListener, Inputtips.InputtipsListener {
    public static final int LOCATION_CHOOSE_CODE = 256;

    private MapView mapView;
    private MyLocationStyle myLocationStyle;
    private AMap aMap;
    private TextView tv_placeName;
    private TextView tv_distance;
    private TextView tv_street;
    private EditText et_location;
    private Button btn_back;
    private Button btn_choose;
    private CardView search_list_card;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static LatLonPoint local_LatLonPoint;
    private RegeocodeAddress address;
    GeocodeSearch geocoderSearch;
    Inputtips.InputtipsListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_page);
        //设置状态栏透明和颜色亮色
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //初始化地图
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();

    }

    //初始化相关控件。
    public void init() {
        tv_placeName = findViewById(R.id.tv_placeName);
        tv_distance = findViewById(R.id.tv_distance);
        tv_street = findViewById(R.id.tv_street);
        btn_back = findViewById(R.id.btn_back);
        et_location = findViewById(R.id.et_location);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        btn_back = findViewById(R.id.btn_back);
        btn_choose = findViewById(R.id.btn_choose);
        mRecyclerView = findViewById(R.id.recyclerView);
        search_list_card = findViewById(R.id.search_list_card);
        listener = this;

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定位一次
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //todo:delete
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(n -> {
            tv_distance.setText("0km");
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            local_LatLonPoint = new LatLonPoint(n.getLatitude(), n.getLongitude());
            RegeocodeQuery query = new RegeocodeQuery(local_LatLonPoint, 100, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
            //todo:delete
            //Toast.makeText(this, n.getBearing() + "," + n.hasAltitude() + "," +n.getLatitude(), Toast.LENGTH_SHORT).show();
        });
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(16);//缩放屏幕
        aMap.animateCamera(mCameraUpdate);
        aMap.setOnMapClickListener(this);//设置地图点击监听

        btn_back.setOnClickListener(n -> finish());
        btn_choose.setOnClickListener(n -> {
            getIntent().putExtra("address", address.getFormatAddress());
            setResult(LOCATION_CHOOSE_CODE, getIntent());
            finish();
        });
        et_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LocationUtil.inputPrompt(s.toString(), address, listener);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    //坐标转地址（逆地理编码）
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        address = regeocodeResult.getRegeocodeAddress();
        String streetStr = address.getProvince() + address.getCity() + address.getDistrict() + address.getTownship();

        tv_placeName.setText(address.getFormatAddress().replaceAll(streetStr, ""));
        tv_street.setText(streetStr);
    }

    //地址转坐标
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    //地图点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //Log.d("Location", "onMapClick: " + latLng);
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        LocationUtil.distanceSearch(new LatLonPoint(latLng.latitude, latLng.longitude), local_LatLonPoint, this);//位置测量
        aMap.clear();
        drawableMarker(latLng);
    }

    //绘制点标记
    public void drawableMarker(LatLng latLng) {
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(tv_placeName.getText().toString()).snippet(""));
    }

    //距离查询回调
    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        //Log.d("Location", "onDistanceSearched: " + distanceResult.getDistanceResults().get(0).getDistance());
        float distance = distanceResult.getDistanceResults().get(0).getDistance();
        if (distance < 1000) {
            tv_distance.setText("" + distance + "m");
        } else {
            DecimalFormat decimalFormat = new DecimalFormat(".0");
            tv_distance.setText("" + decimalFormat.format(distance / 1000) + "km");
        }
    }

    //输入内容自动提示
    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (list != null) {
            search_list_card.setVisibility(View.VISIBLE);
            initRecyclerView(list);
        } else {
            search_list_card.setVisibility(View.INVISIBLE);
        }
    }

    //点击tip的item，对地图view进行更新数据
    public void updateMapView(LatLonPoint point, String name, String street) {
        search_list_card.setVisibility(View.INVISIBLE);
        aMap.clear();
        //更新ui信息
        tv_placeName.setText(name);
        tv_street.setText(street);
        LocationUtil.distanceSearch(point, local_LatLonPoint, this);
        //生成marker
        drawableMarker(new LatLng(point.getLatitude(), point.getLongitude()));
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(point.getLatitude(), point.getLongitude()),17,30,0));
        aMap.animateCamera(mCameraUpdate);

        //移动地图中心
    }

    private void initRecyclerView(List<Tip> beans) {
        //创建默认垂直布局管理器
        mLayoutManager = new LinearLayoutManager(this);
        //创建适配器
        mAdapter = new LocationAdapter(this, beans);
        //获取recyclerView组件
        //mRecyclerView = (EmptyRecyclerView) findViewById(R.id.my_recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //禁用默认的change动画，解决数据更新造成的闪屏问题
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        //设置没有数据时的显示
        //View view = findViewById(R.id.text_empty);
        //mRecyclerView.setEmptyView(view);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //set divider
        Divider divider = new Divider(new ColorDrawable(0xffE7E7E7), OrientationHelper.VERTICAL);
        //单位:px
        divider.setMargin(5, 1, 5, 1);
        divider.setHeight(2);
        mRecyclerView.addItemDecoration(divider);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
