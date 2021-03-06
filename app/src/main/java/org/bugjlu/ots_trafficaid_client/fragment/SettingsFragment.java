package org.bugjlu.ots_trafficaid_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.bugjlu.ots_trafficaid_client.MainActivity;
import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.activity.CompleteInformationActivity;
import org.bugjlu.ots_trafficaid_client.activity.MyCaseActivity;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button information = (Button) view.findViewById(R.id.complete_information);
        Button cases = (Button) view.findViewById(R.id.my_cases);
        Button certification = (Button) view.findViewById(R.id.professional_certification);
        Button rescues = (Button) view.findViewById(R.id.my_rescue);

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CompleteInformationActivity.class);
                startActivity(intent);
            }
        });

        cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCaseActivity.class);
                startActivity(intent);
            }
        });

        certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启职业认证的activity
            }
        });

        rescues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启查看救援工作的activity
            }
        });
        return view;
    }
}
