package org.bugjlu.ots_trafficaid_client;

import android.*;
import android.content.Context;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.bugjlu.ots_trafficaid_client.activity.MapActivity;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/5/7.
 */

public class MyApplication extends Application {

    private static Context context;
    private static MyApplication instance;



    public static MyApplication getInstance() {return instance;}

//    @Override
//    public void onCreate() {
//        MultiDex.install(this);
//        super.onCreate();
//        applicationContext = this;
//
//
//        // 初始化华为 HMS 推送服务
//        HMSPushHelper.getInstance().initHMSAgent(instance);
//        //init demo helper
//        DemoHelper.getInstance().init(applicationContext);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false); // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAutoLogin(false);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true); //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        context = getApplicationContext();
        instance = this;



//        locationStartListen();
    }

    public static Context getContext() {
        return context;
    }
}