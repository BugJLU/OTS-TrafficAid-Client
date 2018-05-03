package org.bugjlu.ots_trafficaid_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.activity.MainActivity;
import org.bugjlu.ots_trafficaid_client.activity.RescueActivity;

public class RescueFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescue, container, false);
        Button rescueTrue = (Button) view.findViewById(R.id.button_rescue_ture);
        Button rescueFalse = (Button) view.findViewById(R.id.button_rescue_false);
        rescueTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((MainActivity)getActivity()), RescueActivity.class);
                intent.putExtra("isRescue", true);
                startActivity(intent);
            }
        });

        rescueFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((MainActivity)getActivity()), RescueActivity.class);
                intent.putExtra("isRescue", false);
                startActivity(intent);
            }
        });
        return view;
    }
}
