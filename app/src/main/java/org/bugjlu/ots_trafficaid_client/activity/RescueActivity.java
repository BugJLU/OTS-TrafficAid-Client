package org.bugjlu.ots_trafficaid_client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

    private static int REQUEST_VOICE = 1;
    private static int REQUEST_VIDEO = 2;
    private Bitmap picture;
    private Uri uri;

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
        send = (Button) findViewById(R.id.commit);

        if(!isRescue)
        {
            etiology[2].setVisibility(View.GONE);
        }
        otherInput[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent,REQUEST_VOICE);
            }
        });

        otherInput[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_VIDEO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_VOICE)
            {
                uri = data.getData();
            }
            else if (requestCode == REQUEST_VIDEO)
            {
                Bundle bundle = data.getExtras();
                picture = (Bitmap) bundle.get("data");
            }
        }
        else
        {
            if (requestCode == REQUEST_VOICE)
            {
                //otherInput[0].setText("");
            }
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
}
