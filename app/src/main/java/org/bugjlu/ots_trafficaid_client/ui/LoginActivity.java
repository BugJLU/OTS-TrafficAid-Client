package org.bugjlu.ots_trafficaid_client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.bugjlu.ots_trafficaid_client.BaseActivity;
import org.bugjlu.ots_trafficaid_client.R;

public class LoginActivity extends BaseActivity {
    EditText txUid,txPwd;
    Button bLog,bReg;
    private  final String TAG = "LOGIN";
    private final boolean DEBUG = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLog= (Button) findViewById(R.id.login);
        bReg = (Button)findViewById(R.id.to_register);
        txUid = (EditText)findViewById(R.id.l_userid);
        txPwd = (EditText)findViewById(R.id.l_password);

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        bLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = ((EditText) findViewById(R.id.l_userid)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.l_password)).getText().toString();
                //Log.d(TAG, "登录聊天服务器成功！");

                EMClient.getInstance().login(uid, pwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        if(DEBUG)
                            Log.d(TAG, "登录聊天服务器成功！");
                    }

                    @Override
                    public void onError(int code, String error) {
                        if(DEBUG)
                            Log.d(TAG, "登录聊天服务器失败！"+error+code);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
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
