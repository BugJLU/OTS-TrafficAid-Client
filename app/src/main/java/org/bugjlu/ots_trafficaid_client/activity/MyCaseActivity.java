package org.bugjlu.ots_trafficaid_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.User;

public class MyCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_case);
        setTitle("我的病例");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView mycase = (TextView) findViewById(R.id.infor_mycase);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final User user = MyService.userService.getUser(MyService.userName);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String t = user.getMediHist();
                        mycase.setText(user.getMediHist());
                    }
                });
            }
        }).start();
        Button commit = (Button) findViewById(R.id.medi_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String casetext = mycase.getText().toString();
                //病例保存后和服务器通信的内容
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            User user = MyService.userService.getUser(MyService.userName);
                            if (user == null) throw new Exception("err getting user");
                            user.setMediHist(casetext);
                            user = MyService.userService.updateUserInfo(user);
                            if (user == null) throw new Exception("err updating user");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            final Exception e1 = e;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
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
