package org.bugjlu.ots_trafficaid_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.R;

public class MyCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_case);
        setTitle("我的病例");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView mycase = (TextView) findViewById(R.id.infor_mycase);
        Button commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //病例保存后和服务器通信的内容
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
