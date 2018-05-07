package org.bugjlu.ots_trafficaid_client.activity;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.chatuidemo.ui.ContactListFragment;

public class ContactActivity extends BaseActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ContactListFragment content = new ContactListFragment();
        FragmentTransaction ft = fm.beginTransaction();
        android.support.v7.app.ActionBar actBar =  getSupportActionBar();
        actBar.hide();
//        setSupportActionBar(null);
        ft.add(R.id.contact_fragment_container, content);
        ft.show(content);
        ft.commit();
    }
}
