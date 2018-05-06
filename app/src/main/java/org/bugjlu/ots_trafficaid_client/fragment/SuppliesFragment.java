package org.bugjlu.ots_trafficaid_client.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.activity.MainActivity;
import org.bugjlu.ots_trafficaid_client.activity.MapActivity;
import org.bugjlu.ots_trafficaid_client.activity.MysupplyActivity;

public class SuppliesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplies, container, false);
        ImageView me = (ImageView) view.findViewById(R.id.imageview_supply_me);
        ImageView development = (ImageView) view.findViewById(R.id.imageview_supply_development);
        Button others = (Button) view .findViewById(R.id.button_supply_others);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity)getActivity(), MysupplyActivity.class);
                startActivity(intent);
            }
        });

        development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入一个展示死图的activity
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
