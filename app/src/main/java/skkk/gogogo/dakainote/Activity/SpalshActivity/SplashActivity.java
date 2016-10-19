package skkk.gogogo.dakainote.Activity.SpalshActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.R;

public class SplashActivity extends AppCompatActivity {
    private Handler splashHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, UIHomeActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                splashHandler.sendEmptyMessage(0);
            }
        }).start();
    }
}
