package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.bugjlu.ots_trafficaid_client.Helper.LoginCallbackHelper;
import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.chatuidemo.DemoHelper;
import org.bugjlu.ots_trafficaid_client.chatuidemo.db.DemoDBManager;
import org.bugjlu.ots_trafficaid_client.chatuidemo.ui.*;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

public class LoginActivity extends BaseActivity {
    EditText txUid,txPwd;
    Button bLog,bReg;
    private  final String TAG = "LOGIN";
    private final boolean DEBUG = true;
    private static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLog= (Button) findViewById(R.id.login);
        bReg = (Button)findViewById(R.id.to_register);
        txUid = (EditText)findViewById(R.id.l_userid);
        txPwd = (EditText)findViewById(R.id.l_password);

        if (DemoHelper.getInstance().isLoggedIn()) {
//            autoLogin = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            return;
        }

        SharedPreferences preferences = getSharedPreferences("usr_infor",MODE_PRIVATE);
        String uid = "";
        String pwd = "";
        String _uid = "";
        String _pwd = "";

        if(preferences!=null) {
            uid = preferences.getString("usr_name", _uid);
            pwd = preferences.getString("password", _pwd);
        }else{
            Log.d(TAG,"no user info. !");
        }

        if(!uid.equals(_uid))
            txUid.setText(uid);
        if(!pwd.equals(_pwd))
            txPwd.setText(pwd);


        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        bLog.setOnClickListener(new View.OnClickListener() {
            String uid,pwd;
            @Override
            public void onClick(View view) {
               uid = ((EditText) findViewById(R.id.l_userid)).getText().toString();
               pwd = ((EditText) findViewById(R.id.l_password)).getText().toString();
                //Log.d(TAG, "登录聊天服务器成功！");

                DemoHelper.getInstance().setCurrentUserName(uid);
                DemoDBManager.init(getApplicationContext());

//                EMClient.getInstance().login(uid, pwd, new LoginCallbackHelper(LoginActivity.this, uid, pwd));
                class LCH extends LoginCallbackHelper {

                    public LCH(Context context, String uid, String pass) {
                        super(context, uid, pass);
                    }

                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                EMClient.getInstance().login(uid, pwd, new LCH(LoginActivity.this, uid, pwd));

//                EMClient.getInstance().login(uid, pwd, new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        user = userService.getUser(uid);
//                        if(DEBUG) {
//                            Log.d(TAG, "登录聊天服务器成功！"+" user_name:"+user.getName());
//                            //Toast.makeText(this,)
//                        }
//
//
//                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                        SharedPreferences preferences = getSharedPreferences("usr_infor",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("usr_name",uid);
//                        editor.putString("password",pwd);
//                        editor.commit();
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        return;
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//                        if(DEBUG)
//                            Log.d(TAG, "登录聊天服务器失败！"+error+code);
//                        Looper.prepare();
//                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//                });
            }
        });


        //tv1 = (TextView) findViewById(R.id.tv1);
        /*Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new GetMsgRunnable()).start();
            }
        });*/
    }

    private void init(){

    }
}
