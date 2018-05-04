package org.bugjlu.ots_trafficaid_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.bugjlu.ots_trafficaid_client.R;

public class RescueActivity extends AppCompatActivity {

    private CheckBox[] etiology = new CheckBox[3];
    private EditText textInput;
    private Button[] otherInput = new Button[2];
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Boolean isRescue = intent.getBooleanExtra("isRescue", true);
        if (isRescue)
        {
            setTitle("当事人求救");
        }
        else
        {
            setTitle("非当事人求救");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etiology[0] = (CheckBox) findViewById(R.id.etiology1);
        etiology[1] = (CheckBox) findViewById(R.id.etiology2);
        etiology[2] = (CheckBox) findViewById(R.id.etiology3);
        textInput = (EditText) findViewById(R.id.rescue_text_in);
        otherInput[0] = (Button) findViewById(R.id.rescue_voice_in);
        otherInput[1] = (Button) findViewById(R.id.rescue_video_in);

        if(!isRescue)
        {
            etiology[2].setVisibility(View.GONE);
        }
        //写视频输入，语音输入的监听按钮，还有发送的监听按钮，分别跳转到各自的avtivity
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
