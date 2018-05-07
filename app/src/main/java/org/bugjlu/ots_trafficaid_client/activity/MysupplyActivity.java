package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.adapter.ResourceAdapter;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.ArrayList;
import java.util.List;

public class MysupplyActivity extends AppCompatActivity {

    private static List<Resource> resourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysupply);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("我的物资");


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
            resourceList = MyService.resourceService.getResourcesFrom(MyService.userName);
        }

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

    public static void updateResources()
    {
        resourceList = MyService.resourceService.getResourcesFrom(MyService.userName);
    }

}
