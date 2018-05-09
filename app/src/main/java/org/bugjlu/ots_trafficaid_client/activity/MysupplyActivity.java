package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.adapter.ResourceAdapter;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.ArrayList;
import java.util.List;

public class MysupplyActivity extends AppCompatActivity {

    public static List<Resource> resourceList = new ArrayList<>();
    public static ResourceAdapter adapter = null;
    private TextView text = null;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysupply);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("我的物资");

        text = (TextView) findViewById(R.id.resource_purpose);
        initResources();
        recyclerView = (RecyclerView) findViewById(R.id.list_resource);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ResourceAdapter(resourceList);
        recyclerView.setAdapter(adapter);

        Button addResource = (Button) findViewById(R.id.add_resource);
        addResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MysupplyActivity.this, AddResourceActivity.class);
                startActivity(intent);
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true)
//                {
//                    resourceList = MyService.resourceService.getResourcesFrom(MyService.userName);
//                    adapter = new ResourceAdapter(resourceList);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerView.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }
//        }).start();
    }

    public static void setResourceList(List<Resource> list)
    {
        resourceList = list;
        adapter = new ResourceAdapter(resourceList);
        recyclerView.setAdapter(adapter);
    }

    private void initResources()
    {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    resourceList = MyService.resourceService.getResourcesFrom(MyService.userName);
                    adapter = new ResourceAdapter(resourceList);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();

//        for (int i = 0; i < 10; i++)
//        {
//            Resource resource = new Resource();
//            resource.setName("ceshu" + i);
//            resourceList.add(resource);
//        }

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

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
