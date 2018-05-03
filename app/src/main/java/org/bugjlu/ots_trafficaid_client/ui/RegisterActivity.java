package org.bugjlu.ots_trafficaid_client.ui;

import android.os.Bundle;
import android.os.Debug;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.bugjlu.ots_trafficaid_client.BaseActivity;
import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

public class RegisterActivity extends BaseActivity{
    private Button bReg, bCancel;
    private EditText txPreName,txUid,txPwd;
    private final String TAG = "REGISTER";
    private final boolean DEBUG = true;

    private UserService userService = new UserServiceImpl();
    //private String uid, pwd, pre_name;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bReg = (Button)findViewById(R.id.register);
        bCancel = (Button)findViewById(R.id.cancel);
        txPreName = (EditText)findViewById(R.id.prefer_name);
        txUid = (EditText)findViewById(R.id.reg_userid);
        txPwd = (EditText)findViewById(R.id.reg_password);

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           String prefer_name = txPreName.getText().toString();
                           String uid = txUid.getText().toString();
                           String pwd = txPwd.getText().toString();
                           if(DEBUG){
                               Log.d(TAG,"userid:"+uid+" "+"password:"+pwd);
                           }
                           Looper.prepare();
                           try {
                               EMClient.getInstance().createAccount(uid,pwd);
                               if(DEBUG)
                                   Log.d(TAG,"注册成功 !");
                                   //Toast.makeText(getApplicationContext(),"注册成功 !",Toast.LENGTH_SHORT);
                               User user = new User();
                               user.setType(0);
                               user.setId(uid);
                               user.setName(prefer_name);
                               user.setIdCode(uid);
                               if(userService.addUser(user)){
                                   if(DEBUG)
                                       Log.d(TAG,"add to server successfully !");
                               }else{
                                   if(DEBUG){
                                       Log.d(TAG,"add to server unsuccessfully !");
                                   }
                               }

                           } catch (HyphenateException e) {
                               int erroCode = e.getErrorCode();
                               if(erroCode == EMError.NETWORK_ERROR){
                                   Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                               }else if(erroCode == EMError.USER_ALREADY_EXIST){
                                   Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
                               }else if(erroCode == EMError.USER_AUTHENTICATION_FAILED){
                                   Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                               }else{
                                   Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                           Looper.loop();
                       }
                   }).start();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
