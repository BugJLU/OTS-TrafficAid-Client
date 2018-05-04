package org.bugjlu.ots_trafficaid_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import org.bugjlu.ots_trafficaid_client.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        String string = intent.getStringExtra("image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView view = (ImageView) findViewById(R.id.all_image);
        switch (string)
        {
            case "kkk":
                //view.setImageResource();
                break;
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
