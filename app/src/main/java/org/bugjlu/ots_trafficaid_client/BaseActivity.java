package org.bugjlu.ots_trafficaid_client;

import android.app.Activity;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false); // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAutoLogin(false);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true); //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
    }
}
