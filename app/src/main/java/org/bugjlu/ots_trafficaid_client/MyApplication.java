package org.bugjlu.ots_trafficaid_client;

import android.content.Context;
import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

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
        options.setAcceptInvitationAlways(true); // 默认添加好友时，是不需要验证的，//改成需要验证
        options.setAutoLogin(false);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true); //在做打包混淆时，关闭debug模式，避免消耗不必要的资源

        context = getApplicationContext();
        instance = this;
    }

    public static Context getContext() {
        return context;
    }
}