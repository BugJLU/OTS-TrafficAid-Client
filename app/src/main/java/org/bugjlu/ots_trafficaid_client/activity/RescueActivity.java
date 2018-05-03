package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
        etiology[0] = (CheckBox) findViewById(R.id.etiology1);
        etiology[1] = (CheckBox) findViewById(R.id.etiology2);
        etiology[2] = (CheckBox) findViewById(R.id.etiology3);
        textInput = (EditText) findViewById(R.id.rescue_text_in);
        otherInput[0] = (Button) findViewById(R.id.rescue_voice_in);
        otherInput[1] = (Button) findViewById(R.id.rescue_video_in);
        Intent intent = getIntent();
        Boolean isRescue = intent.getBooleanExtra("isRescue", true);
        if(!isRescue)
        {
            etiology[2].setVisibility(View.GONE);
        }
        //写视频输入，语音输入的监听按钮，还有发送的监听按钮，分别跳转到各自的avtivity
    }
}
