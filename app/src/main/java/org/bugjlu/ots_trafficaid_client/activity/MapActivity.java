package org.bugjlu.ots_trafficaid_client.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import org.bugjlu.ots_trafficaid_client.R;

import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.ArrayList;
import java.util.List;

import static org.bugjlu.ots_trafficaid_client.R.color.colorPrimaryDark;

public class MapActivity extends AppCompatActivity {

    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private LocationClient locationClient;
    private MapView mapView;
    //private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        //textView = (TextView) findViewById(R.id.my_positon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("周围物资");



        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty())
        {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this, permissions, 1);
        }else
        {
            requestLocation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location);
        List<OverlayOptions> list = new ArrayList<>();
        List<User> users = MyService.userService.getUserAround(MyService.userName, 5);
        switch (item.getItemId())
        {
//            case android.R.id.home:
//                finish();
//                return true;
//            case R.id.allthing:
//                for (User user : users)
//                {
//                    List<Resource> resources = MyService.resourceService.getResourcesFrom(user.getId());
//                    boolean have = false;
//                    for (Resource resource : resources)
//                    {
//                        LatLng point = new LatLng(Double.parseDouble(user.getGeoX()), Double.parseDouble(user.getGeoY()));
//                        if (resource.getType() == 1)
//                        {
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：止血" + "\n" + "名称：" + resource.getName()));
//                        }
//                        else if (resource.getType() == 2)
//                        {
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：工具" + "\n" + "名称：" + resource.getName()));
//                        }
//                        else if (resource.getType() == 3)
//                        {
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：止血" + "\n" + "名称：" + resource.getName()));
//                        }
//                    }
//                }
//                baiduMap.addOverlays(list);
//                break;
//            case R.id.medicine:
//                //点击药物之后做显示
//                for (User user : users)
//                {
//                    List<Resource> resources = MyService.resourceService.getResourcesFrom(user.getId());
//                    boolean have = false;
//                    for (Resource resource : resources)
//                    {
//                        if (resource.getType() == 2)
//                        {
//                            LatLng point = new LatLng(Double.parseDouble(user.getGeoX()), Double.parseDouble(user.getGeoY()));
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：药物" + "\n" + "名称：" + resource.getName()));
//                        }
//                    }
//                }
//                baiduMap.addOverlays(list);
//                break;
//            case R.id.tool:
//
//                for (User user : users)
//                {
//                    List<Resource> resources = MyService.resourceService.getResourcesFrom(user.getId());
//                    boolean have = false;
//                    for (Resource resource : resources)
//                    {
//                        if (resource.getType() == 3)
//                        {
//                            LatLng point = new LatLng(Double.parseDouble(user.getGeoX()), Double.parseDouble(user.getGeoY()));
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：工具" + "\n" + "名称：" + resource.getName()));
//                        }
//                    }
//                }
//                baiduMap.addOverlays(list);
//                break;
//            case R.id.stopblood:
//                for (User user : users)
//                {
//                    List<Resource> resources = MyService.resourceService.getResourcesFrom(user.getId());
//                    boolean have = false;
//                    for (Resource resource : resources)
//                    {
//                        if (resource.getType() == 1)
//                        {
//                            LatLng point = new LatLng(Double.parseDouble(user.getGeoX()), Double.parseDouble(user.getGeoY()));
//                            list.add(new MarkerOptions().position(point).icon(bitmap).title(
//                                    "用户名：" + user.getName()+ "\n" + "联系方式：" + user.getContactInfo() + "\n" + "类型：止血" + "\n" + "名称：" + resource.getName()));
//                        }
//                    }
//                }
//                baiduMap.addOverlays(list);
//                break;

            case R.id.nothing:
                baiduMap.clear();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }



    private void requestLocation()
    {
        mapView = (MapView) findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng position = marker.getPosition();
                TextView textView = new TextView(getApplicationContext());
                textView.setText(marker.getTitle());
                textView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                InfoWindow infoWindow = new InfoWindow(textView,position, -100);
                baiduMap.showInfoWindow(infoWindow);
                return true;
            }
        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                                           @Override
                                           public void onMapClick(LatLng latLng) {
                                               baiduMap.hideInfoWindow();
                                           }

                                           @Override
                                           public boolean onMapPoiClick(MapPoi mapPoi) {
                                               return false;
                                           }
                                       }
        );

        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        baiduMap.setMyLocationEnabled(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0)
                {
                    for (int result:grantResults)
                    {
                        if (result != PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(this, "必须同意所有权限才能使用本功能",Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else
                {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
                default:
                    break;
        }
    }



    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder string = new StringBuilder();
            string.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            string.append("经度：").append(bdLocation.getLongitude()).append("\n");
            string.append("定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation)
            {
                string.append("GPS\n");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation)
            {
                string.append("网络\n");
            }
            string.append("国家：").append(bdLocation.getCountry()).append("\n");
            string.append("省：").append(bdLocation.getProvince());
            string.append("城市：").append(bdLocation.getCity()).append("\n");
            string.append("区：").append(bdLocation.getDistrict()).append("\n");
            string.append("街道：").append(bdLocation.getStreet()).append("\n");
            //string.append(bdLocation.getLocType());
            //textView.setText(string);


            if (isFirstLocate)
            {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomTo(16f);
                baiduMap.animateMapStatus(update);
                isFirstLocate = false;
            }

            MyLocationData.Builder builder = new MyLocationData.Builder();
            builder.latitude(bdLocation.getLatitude());
            builder.longitude(bdLocation.getLongitude());
            MyLocationData data = builder.build();
            baiduMap.setMyLocationData(data);
        }
    }
}
