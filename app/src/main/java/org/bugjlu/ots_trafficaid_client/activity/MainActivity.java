package org.bugjlu.ots_trafficaid_client.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.chatuidemo.Constant;
import org.bugjlu.ots_trafficaid_client.chatuidemo.DemoHelper;
import org.bugjlu.ots_trafficaid_client.chatuidemo.db.DemoDBManager;
import org.bugjlu.ots_trafficaid_client.chatuidemo.ui.ConversationListFragment;
import org.bugjlu.ots_trafficaid_client.fragment.RescueFragment;
import org.bugjlu.ots_trafficaid_client.fragment.SettingsFragment;
import org.bugjlu.ots_trafficaid_client.fragment.SuppliesFragment;
import org.bugjlu.ots_trafficaid_client.fragment.TalksFragment;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton fab;
    LocationClient locationClient;

    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("救援");
        EMClient.getInstance().init(getApplicationContext(), new EMOptions());
        SharedPreferences preferences = getSharedPreferences("usr_infor",MODE_PRIVATE);
        String uid = preferences.getString("usr_name","null");
        String pass = preferences.getString("password", "null");
        DemoHelper.getInstance().init(getApplicationContext());

        if(uid == "null")
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        MyService.userName = uid;
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.tab_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_view, new RescueFragment());
        transaction.commit();
//        if (getIntent() != null &&
//                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
//                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
//                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
//            DemoHelper.getInstance().logout(false,null);
//            finish();
//            startActivity(new Intent(this, org.bugjlu.ots_trafficaid_client.chatuidemo.ui.LoginActivity.class));
//            return;
//        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
//            finish();
//            startActivity(new Intent(this, org.bugjlu.ots_trafficaid_client.chatuidemo.ui.LoginActivity.class));
//            return;
//        }

        SDKInitializer.initialize(getApplicationContext());
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                User me = MyService.userService.getUser(MyService.userName);
                me.setGeoX(String.valueOf(bdLocation.getLatitude()));
                me.setGeoY(String.valueOf(bdLocation.getLongitude()));
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        locationClient.setLocOption(option);
        locationClient.start();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.tab_item1:
                fragment = new RescueFragment();
                setTitle("救援");
                fab.setVisibility(View.GONE);
                break;
            case R.id.tab_item2:
                fragment = new SuppliesFragment();
                setTitle("物资");
                fab.setVisibility(View.GONE);
                break;
            case R.id.tab_item3:
//                fragment = new TalksFragment();
                fragment = new ConversationListFragment();
                setTitle("会话");
                fab.setVisibility(View.VISIBLE);
                break;
            case R.id.tab_item4:
                fragment = new SettingsFragment();
                setTitle("设置");
                fab.setVisibility(View.GONE);
                break;
                default:
                    break;
        }
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_view, fragment);
        transaction.commit();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }
}
