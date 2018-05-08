package org.bugjlu.ots_trafficaid_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Contact;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.List;

public class CompleteInformationActivity extends AppCompatActivity {

    User originUser = null;
    void setOriginUser(User user) {
        originUser = user;
    }

    public User getOriginUser() {
        return originUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_information);
        setTitle("完善个人信息");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView name = (TextView) findViewById(R.id.infor_name);
        final Spinner gender = (Spinner) findViewById(R.id.infor_gender);
        final Spinner emgcontact = (Spinner) findViewById(R.id.infor_emgcontact);
        final TextView idcode = (TextView) findViewById(R.id.infor_idcode);
        final TextView platenum = (TextView) findViewById(R.id.infor_platenum);
        final TextView cartype = (TextView) findViewById(R.id.infor_cartype);
        final TextView contactinfo = (TextView) findViewById(R.id.infor_contactinfo);
        Button commit = (Button) findViewById(R.id.info_commit);

        final Thread updateUIThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final User user = MyService.userService.getUser(MyService.userName);
                setOriginUser(user);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(user.getName());
                        Integer g = user.getGender();
                        if (g == null) gender.setSelection(0);
                        else gender.setSelection(g);
                        SpinnerAdapter adapter= emgcontact.getAdapter();
                        int k= adapter.getCount();
                        for(int i=0;i<k;i++){
                            if(adapter.getItem(i).toString().equals(user.getEmgContact())){
                                emgcontact.setSelection(i,true);
                                break;
                            }
                        }
                        idcode.setText(user.getIdCode());
                        platenum.setText(user.getPlateNum());
                        cartype.setText(user.getCarType());
                        contactinfo.setText(user.getContactInfo());
                    }
                });
            }
        });

        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                final ArrayAdapter<String> list = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_select, strings);
                list.setDropDownViewResource(R.layout.item_drop);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emgcontact.setAdapter(list);
                        updateUIThread.start();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });



//        List<Contact> = MyService.userService.ge
//        emgcontact.set



        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user = getOriginUser();
                final User newInfo = new User();
                newInfo.setId(MyService.userName);
                newInfo.setName(name.getText().toString());
                newInfo.setGender((int)gender.getSelectedItemId());
                newInfo.setEmgContact(emgcontact.getSelectedItem().toString());
                newInfo.setIdCode(idcode.getText().toString());
                newInfo.setPlateNum(platenum.getText().toString());
                newInfo.setCarType(cartype.getText().toString());
                newInfo.setContactInfo(contactinfo.getText().toString());
                if (originUser != null) {
                    newInfo.setMediHist(originUser.getMediHist());
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final User u = MyService.userService.updateUserInfo(newInfo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (u != null) {
                                    Toast.makeText(getApplicationContext(), "update user info success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "update user info failed", Toast.LENGTH_SHORT).show();
                                }                            }
                        });
                    }
                }).start();
//                finish();
            }
        });
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
}
