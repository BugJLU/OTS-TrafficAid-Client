package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.adapter.ResourceAdapter;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.ArrayList;
import java.util.List;

public class MysupplyActivity extends AppCompatActivity {

    private List<Resource> resourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysupply);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        initResources();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_resource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ResourceAdapter adapter = new ResourceAdapter(resourceList);
        recyclerView.setAdapter(adapter);

        Button addResource = (Button) findViewById(R.id.add_resource);
        addResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MysupplyActivity.this, AddResourceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initResources()
    {
        for(int i = 0; i < 30; i++)
        {
            ////以后要改成和服务端进行通信
            Resource resource = new Resource();
            resource.setName("测试" + i);
            resourceList.add(resource);
        }

    }
}
