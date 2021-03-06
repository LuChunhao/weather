package com.example.tianqiyubao.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.tianqiyubao.R;
import com.example.tianqiyubao.WeatherActivity;

public class MapActivity extends AppCompatActivity {
    /*  地图控件 */
    private TextureMapView mMapView=null;
    /*  地图实例 */
    private BaiduMap mBaiduMap;
    /*  定位的客户端 */
    private LocationClient mLocationClient;
    /*  定位的监听器 */
    public MyLocationListener mMyLocationListener;
    /*  当前定位的模式 */
    private MyLocationConfiguration.LocationMode
            mCurrentMode
            = MyLocationConfiguration.LocationMode.NORMAL;
    /*  是否是第一次定位 */
    private volatile boolean isFristLocation = true;
    /*  最新一次的经纬度 */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /*  地图定位的模式 */
    private String[] mStyles
            = new String[]{" 地图模式【正常】",
            " 地图模式【跟随】",
            " 地图模 式【罗盘】"};
    /*  当前地图定位模式的 Index */
    private int mCurrentStyle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
//  初始化 BaiduMap 相关
        initBaiduMap();
//  初始化百度定位客户端
        initMyLocation();
    }
    /**
     *  初始化百度地图
     */
    private void initBaiduMap() {
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu =
                MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
    }
    /**
     *  初始化定位相关代码
     */
    private void initMyLocation() {
//  定位 SDK 初始化
        mLocationClient = new
                LocationClient(getApplicationContext());
//  设置定位的相关配置
        LocationClientOption option = new
                LocationClientOption();
        option.setOpenGps(true); //  打开 gps
        option.setCoorType("bd09ll"); //  设置坐标类型
        option.setScanSpan(1000); //  自动定位间隔
        option.setIsNeedAddress(true);//  是否需要地址
        option.setIsNeedLocationPoiList(true);
       //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//  定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//  根据配置信息对定位客户端进行设置
        mLocationClient.setLocOption(option);
//  注册定位监听
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
//  设置定位图标
        BitmapDescriptor mCurrentMarker =
                BitmapDescriptorFactory.fromResource(R.drawable.location);
        MyLocationConfiguration config = new
                MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
                mBaiduMap.setMyLocationConfigeration(config);
    }
    /**
     *  实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

// mapView  销毁后不在处理新接收的位置

            if (location == null || mMapView == null)
                return;
//  构造定位数据
            MyLocationData locData = new
                    MyLocationData.Builder()
//  此处设置开发者获取到的方向信息，顺时针 0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build
                            ();
//  设置 BaiduMap 的定位数据
            mBaiduMap.setMyLocationData(locData);
//  记录位置信息
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
//  第一次定位时，将地图位置移动到当前位置

            if (isFristLocation) {
                isFristLocation = false;
                center2myLoc();
            }
// Log 记录位置信息
            StringBuffer sb = new StringBuffer(256);
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\naddress : ");
            sb.append(location.getAddrStr());
            for(int i=0; i<location.getPoiList().size(); i++)
            {
                Poi p = location.getPoiList().get(i);
                sb.append("\nPoi NO.");
                sb.append(i);
                sb.append(" : ");
                sb.append(p.getId());
                sb.append("-");
                sb.append(p.getName());
                sb.append("-");
                sb.append(p.getRank());
            }
            Log.i("BaiduLocationInfo", sb.toString());
            //获取当前位置信息并加载当前的天气
            final Toast toast= Toast.makeText(MapActivity.this, "你所定的当前位置为"+location.getCountry()+""
                    +location.getProvince() +""+location.getCity(), Toast.LENGTH_SHORT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            },3000l);


            String weatherId=location.getCity();
            Intent intent=new Intent(MapActivity.this, WeatherActivity.class);
            intent.putExtra("weather_id",weatherId);
            startActivity(intent);
            MapActivity.this.finish();

     }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_map_myLoc: //  标注覆盖物
                center2myLoc();
                break;
            case R.id.id_menu_map_style: //  地图模式

                mCurrentStyle = (++mCurrentStyle) %
                        mStyles.length;
                item.setTitle(mStyles[mCurrentStyle]);
//  设置自定义图标
                switch (mCurrentStyle)
                {
                    case 0:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.NORMAL;
                        break;
                    case 1:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.FOLLOWING;
                        break;
                    case 2:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.COMPASS;
                        break;
                }
                BitmapDescriptor mCurrentMarker =
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.location);
                MyLocationConfiguration config = new
                        MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker);
                mBaiduMap.setMyLocationConfigeration(config);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * BaiduMap 移动到我的位置
     */
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude,
                mCurrentLongitude);
//  设置当前定位位置为 BaiduMap 的中心点，并移动到定位位置
        MapStatusUpdate u =
                MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }
    @Override
    protected void onStart()
    {
//  开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();
        }
        super.onStart();
    }
    @Override
    protected void onStop()
    {
//  关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//  在 activity 执行 onDestroy 时执行
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
//  在 activity 执行 onResume 时执行 mMapView. onResume () ，

        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
//  在 activity 执行 onPause 时执行 mMapView. onPause () ，

        mMapView.onPause();
    }

}
