package org.bugjlu.ots_trafficaid_client.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ContactServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.ResourceServiceImpl;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserService;
import org.bugjlu.ots_trafficaid_client.remote.remote_service.UserServiceImpl;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

