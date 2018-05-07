package org.bugjlu.ots_trafficaid_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.chatuidemo.ui.ChatActivity;

import java.util.Iterator;
import java.util.List;

public class RescueActivity extends PermissionBaseActivity {

    private static int REQUEST_VOICE = 1;
    private static int REQUEST_VIDEO = 2;
    private Bitmap picture;
    private Uri uri;

    private CheckBox[] etiology = new CheckBox[3];
    private EditText textInput;
    private Button[] otherInput = new Button[2];
    private Button send;

    public String groupName;

    Runnable callThread = new Runnable() {
        @Override
        public void run() {
            EMGroupOptions option = new EMGroupOptions();
            option.maxUsers = 200;
            option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
            String allMembers[] = {"police","hosptical"};
            try {
                EMClient.getInstance().groupManager().createGroup(groupName, groupName, allMembers, "no reason", option);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    };

    class SendOnClickListener implements View.OnClickListener {

        Bitmap bitmap;
        Uri uri;


        public SendOnClickListener(Bitmap b, Uri u){
            bitmap = b; uri = u;
        }

        @Override
        public void onClick(View v) {

            String objGroup = null;

            List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();
            Iterator<EMGroup> it1 = grouplist.iterator();
            while (it1.hasNext()){
                EMGroup group = it1.next();
                if(group.getGroupName().equals(groupName))
                    objGroup = group.getGroupId();
            }
            if (!groupName.equals(objGroup)) return;

            EMMessage messageInfo = EMMessage.createTxtSendMessage("",objGroup);
            EMMessage messageLocation = EMMessage.createLocationSendMessage(x , y, country, objGroup);
            messageLocation.setChatType(EMMessage.ChatType.GroupChat);
            messageInfo.setChatType(EMMessage.ChatType.GroupChat);
            EMClient.getInstance().chatManager().sendMessage(messageInfo);
            EMClient.getInstance().chatManager().sendMessage(messageLocation);
//            sendImageMessage(path);
            //TODO
            EMMessage message = EMMessage.createImageSendMessage("bitmap", false, objGroup);
            message.setChatType(EMMessage.ChatType.GroupChat);
            EMClient.getInstance().chatManager().sendMessage(message);
            startActivity(new Intent(RescueActivity.this, ChatActivity.class).putExtra("userId", objGroup));
            RescueActivity.this.finish();

        }
    }

    LocationClient locationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        SDKInitializer.initialize(getApplicationContext());
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.start();

        groupName = String.valueOf(System.currentTimeMillis());
        new Thread(callThread).start();
        Boolean isRescue = intent.getBooleanExtra("isRescue", true);
        if (isRescue)
        {
            setTitle("当事人求救");
        }
        else
        {
            setTitle("非当事人求救");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etiology[0] = (CheckBox) findViewById(R.id.etiology1);
        etiology[1] = (CheckBox) findViewById(R.id.etiology2);
        etiology[2] = (CheckBox) findViewById(R.id.etiology3);
        textInput = (EditText) findViewById(R.id.rescue_text_in);
        otherInput[0] = (Button) findViewById(R.id.rescue_voice_in);
        otherInput[1] = (Button) findViewById(R.id.rescue_video_in);
        send = (Button) findViewById(R.id.commit);

        if(!isRescue)
        {
            etiology[2].setVisibility(View.GONE);
        }
        otherInput[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent,REQUEST_VOICE);
            }
        });

        otherInput[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_VIDEO);
            }
        });
        //TODO
        send.setOnClickListener(new SendOnClickListener(picture, uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_VOICE)
            {
                uri = data.getData();
            }
            else if (requestCode == REQUEST_VIDEO)
            {
                Bundle bundle = data.getExtras();
                picture = (Bitmap) bundle.get("data");
            }
        }
        else
        {
            if (requestCode == REQUEST_VOICE)
            {
                //otherInput[0].setText("");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public LatLng cenpt = null;
    public Double x,y;
    public String country,street;

    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            location.getTime();    //获取定位时间
            //location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
            location.getLocType();    //获取定位类型
            x = location.getLatitude();    //获取纬度信息
            y = location.getLongitude();    //获取经度信息
            location.getRadius();    //获取定位精准度
            street = location.getAddrStr();    //获取地址信息
            location.getCountry();    //获取国家信息
            location.getCountryCode();    //获取国家码
            location.getCity();    //获取城市信息
            location.getCityCode();    //获取城市码
            location.getDistrict();    //获取区县信息
            location.getStreet();    //获取街道信息
            location.getStreetNumber();    //获取街道码
            country = location.getLocationDescribe();    //获取当前位置描述信息
            location.getPoiList();    //获取当前位置周边POI信息

            location.getBuildingID();    //室内精准定位下，获取楼宇ID
            location.getBuildingName();    //室内精准定位下，获取楼宇名称
            location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息

            //Toast.makeText(MapActivity.this, x.toString(), Toast.LENGTH_SHORT).show();
            cenpt = new LatLng(x,y);
//            if(MapActivity.this.boolMarker){
//                setMarker(cenpt);
//            }
//            if(MapActivity.this.isNeedCenter){
//                MapStatus mMapStatus = new MapStatus.Builder()
//                        .target(cenpt)
//                        .zoom(16)
//                        .build();
//                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//                MapActivity.this.mBaiduMap.setMapStatus(mMapStatusUpdate);
//                MapActivity.this.isNeedCenter = false;
//            }
//            Button locBut = (Button) findViewById(R.id.locBut);
//            locBut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MapStatus mMapStatus = new MapStatus.Builder()
//                            .target(cenpt)
//                            .zoom(16)
//                            .build();
//                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//                    MapActivity.this.mBaiduMap.setMapStatus(mMapStatusUpdate);
//                }
//            });

            if (location.getLocType() == BDLocation.TypeGpsLocation){
                //当前为GPS定位结果，可获取以下信息
                location.getSpeed();    //获取当前速度，单位：公里每小时
                location.getSatelliteNumber();    //获取当前卫星数
                location.getAltitude();    //获取海拔高度信息，单位米
                location.getDirection();    //获取方向信息，单位度
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                //当前为网络定位结果，可获取以下信息
                location.getOperators();    //获取运营商信息

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                //当前为网络定位结果

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                //当前网络定位失败
                //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                //当前网络不通

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
                //可进一步参考onLocDiagnosticMessage中的错误返回码

            }
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * 自动回调，相同的diagnosticType只会回调一次
         *
         * @param locType           当前定位类型
         * @param diagnosticType    诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
//        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {

//        if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {
//
//            //建议打开GPS
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {
//
//            //建议打开wifi，不必连接，这样有助于提高网络定位精度！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_LOC_PERMISSION) {
//
//            //定位权限受限，建议提示用户授予APP定位权限！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET) {
//
//            //网络异常造成定位失败，建议用户确认网络状态是否异常！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE) {
//
//            //手机飞行模式造成定位失败，建议用户关闭飞行模式后再重试定位！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI) {
//
//            //无法获取任何定位依据，建议用户打开wifi或者插入sim卡重试！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_OPEN_PHONE_LOC_SWITCH) {
//
//            //无法获取有效定位依据，建议用户打开手机设置里的定位开关后重试！
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_SERVER_FAIL) {
//
//            //百度定位服务端定位失败
//            //建议反馈location.getLocationID()和大体定位时间到loc-bugs@baidu.com
//
//        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_FAIL_UNKNOWN) {
//
//            //无法获取有效定位依据，但无法确定具体原因
//            //建议检查是否有安全软件屏蔽相关定位权限
//            //或调用LocationClient.restart()重新启动后重试！
//
//        }
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null)  {
            locationClient.stop();
        }
    }
}
