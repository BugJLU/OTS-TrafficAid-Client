package org.bugjlu.ots_trafficaid_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Contact;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

import java.util.List;

public class CompleteInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_information);
        setTitle("完善个人信息");
        User user = MyService.userService.getUser(MyService.userName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name = (TextView) findViewById(R.id.infor_name);
        Spinner gender = (Spinner) findViewById(R.id.infor_gender);
        Spinner emgcontact = (Spinner) findViewById(R.id.infor_emgcontact);
        TextView idcode = (TextView) findViewById(R.id.infor_idcode);
        TextView platenum = (TextView) findViewById(R.id.infor_platenum);
        TextView cartype = (TextView) findViewById(R.id.infor_cartype);
        TextView contactinfo = (TextView) findViewById(R.id.infor_contactinfo);
        Button commit = (Button) findViewById(R.id.commit);
        name.setText(user.getName());
        gender.setSelection(user.getGender());
//        List<Contact> = MyService.userService.ge
//        emgcontact.set


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //填写点击提交按钮后个人信息和服务器的更改操作
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
