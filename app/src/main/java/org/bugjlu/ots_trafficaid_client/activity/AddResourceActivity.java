package org.bugjlu.ots_trafficaid_client.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.adapter.ResourceAdapter;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

public class AddResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);
        setTitle("添加物资");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = (Spinner) findViewById(R.id.add_resource_type);
        final EditText editText = (EditText) findViewById(R.id.add_resource_name);
        Button button = (Button) findViewById(R.id.add_resource_photo);
        Button commit = (Button) findViewById(R.id.add_resource_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = (String) editText.getText().toString();
                Integer type = (int) spinner.getSelectedItemId();

                final Resource resource = new Resource();
                resource.setName(name);
                resource.setType(type);
                resource.setPossessorId(MyService.userName);



                if(name.length() == 0)
                {
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("添加提示")
                            .setMessage("名称一栏不能为空")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }else
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyService.resourceService.addResource(resource);
                            MysupplyActivity.resourceList = MyService.resourceService.getResourcesFrom(MyService.userName);
                            MysupplyActivity.adapter = new ResourceAdapter(MysupplyActivity.resourceList);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MysupplyActivity.recyclerView.setAdapter(MysupplyActivity.adapter);
                                    MysupplyActivity.adapter.notifyDataSetChanged();
                                }
                            });


                        }
                    }).start();
                    finish();
                    //进行传输，和服务端的通信操作
                }
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
