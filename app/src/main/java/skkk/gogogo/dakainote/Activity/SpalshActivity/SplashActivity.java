package skkk.gogogo.dakainote.Activity.SpalshActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;

public class SplashActivity extends AppCompatActivity {
    private MyApplication mApplication;
    private SharedPreferences sPref;

    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, UIHomeActivity.class));
            finish();
        }
    };
    private List<Note> myNotes = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplicationContext();
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                //缓存sPref
                sPref=getSharedPreferences("note",MODE_PRIVATE);
                mApplication.setsPref(sPref);
                //缓存Notes
                myNotes = SQLUtils.getNoteList();
                mApplication.setNotes(myNotes);

                long gap = System.currentTimeMillis() - start;
                if (1500 - gap > 0) {
                    try {
                        Thread.sleep(1500 - gap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                splashHandler.sendEmptyMessage(0);
            }
        }).start();
    }
}
