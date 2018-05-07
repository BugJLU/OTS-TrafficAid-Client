package org.bugjlu.ots_trafficaid_client.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.bugjlu.ots_trafficaid_client.activity.LoginActivity;
import org.bugjlu.ots_trafficaid_client.activity.MainActivity;
import org.bugjlu.ots_trafficaid_client.chatuidemo.DemoHelper;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mac on 2018/5/7.
 */

public class LoginCallbackHelper implements EMCallBack {
    Context context;
    String uid, pass;

    public LoginCallbackHelper(Context context, String uid, String pass) {
        this.context = context;
        this.uid = uid;
        this.pass = pass;
    }

    @Override
    public void onSuccess() {
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(uid);
        if (user == null || !user.getId().equals(uid)) {
            onError(10, "error fetching user from server");
            return;
        }

//        if(DEBUG) {
//            Log.d(TAG, "登录聊天服务器成功！"+" user_name:"+user.getName());
//            //Toast.makeText(this,)
//        }


        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
        SharedPreferences preferences = context.getSharedPreferences("usr_infor",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usr_name",uid);
        editor.putString("password",pass);
        editor.commit();

        return;
    }

    @Override
    public void onError(int code, String error) {
//        if(DEBUG)
//            Log.d(TAG, "登录聊天服务器失败！"+error+code);
        Looper.prepare();
        Toast.makeText(context.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void onProgress(int progress, String status) {

    }
}
