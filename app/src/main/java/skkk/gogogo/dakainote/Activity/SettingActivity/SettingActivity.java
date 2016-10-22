package skkk.gogogo.dakainote.Activity.SettingActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.R;

public class SettingActivity extends AppCompatActivity {


    CoordinatorLayout mClSetting;
    private SharedPreferences sPref;
    private MyApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplicationContext();
        sPref = mApplication.getsPref();

        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



}
