package org.bugjlu.ots_trafficaid_client.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.fragment.RescueFragment;
import org.bugjlu.ots_trafficaid_client.fragment.SettingsFragment;
import org.bugjlu.ots_trafficaid_client.fragment.SuppliesFragment;
import org.bugjlu.ots_trafficaid_client.fragment.TalksFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.tab_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_view, new RescueFragment());
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId())
        {
            case R.id.tab_item1:
                fragment = new RescueFragment();
                break;
            case R.id.tab_item2:
                fragment = new SuppliesFragment();
                break;
            case R.id.tab_item3:
                fragment = new TalksFragment();
                break;
            case R.id.tab_item4:
                fragment = new SettingsFragment();
                break;
                default:
                    break;
        }
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_view, fragment);
        transaction.commit();
        return true;
    }

}
