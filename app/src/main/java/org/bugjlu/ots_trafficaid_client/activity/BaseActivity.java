package org.bugjlu.ots_trafficaid_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

import org.bugjlu.ots_trafficaid_client.Helper.LoginCallbackHelper;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

public class BaseActivity extends PermissionBaseActivity {
    protected  static UserService userService = new UserServiceImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        if (!EMClient.getInstance().isConnected()) {
//            EMClient.getInstance().init(getApplicationContext(), new EMOptions());
//        }
        if (!EMClient.getInstance().isConnected()) {
            SharedPreferences preferences = getSharedPreferences("usr_infor",MODE_PRIVATE);
            String uid = preferences.getString("usr_name","null");
            String pass = preferences.getString("password", "null");
            if (uid == null || pass == null || uid.equals("") || pass.equals("")) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            } else {
                EMClient.getInstance().init(getApplicationContext(), new EMOptions());
                EMClient.getInstance().login(uid, pass, new LoginCallbackHelper(BaseActivity.this, uid, pass));
            }
        }
        if(!isTaskRoot()){
            Intent intent = getIntent();
            String action = intent.getAction();
            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
                finish();
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EMClient.getInstance().isConnected()) {
            SharedPreferences preferences = getSharedPreferences("usr_infor",MODE_PRIVATE);
            String uid = preferences.getString("usr_name","null");
            String pass = preferences.getString("password", "null");
            if (uid == null || pass == null || uid.equals("") || pass.equals("")) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            } else {
                EMClient.getInstance().init(getApplicationContext(), new EMOptions());
                EMClient.getInstance().login(uid, pass, new LoginCallbackHelper(BaseActivity.this, uid, pass));
            }
        }
        // cancel the no tification
        EaseUI.getInstance().getNotifier().reset();
    }

}
