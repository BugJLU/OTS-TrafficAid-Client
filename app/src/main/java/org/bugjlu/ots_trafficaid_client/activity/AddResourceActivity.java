package org.bugjlu.ots_trafficaid_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

public class AddResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);
        final Spinner spinner = (Spinner) findViewById(R.id.add_resource_type);
        final TextView textView = (TextView) findViewById(R.id.add_resource_name);
        Button button = (Button) findViewById(R.id.add_resource_photo);
        Button commit = (Button) findViewById(R.id.add_resource_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) textView.getText();
                Integer type = (int) spinner.getSelectedItemId();
                Resource resource = new Resource();
                resource.setName(name);
                resource.setType(type);


                //进行传输，和服务端的通信操作


                finish();
            }
        });
    }
}
