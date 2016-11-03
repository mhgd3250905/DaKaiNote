package skkk.gogogo.dakainote.Activity.SpalshActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView tvSplash;
    private ImageView ivSplash;

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

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplicationContext();
        setContentView(R.layout.activity_splash);
        tvSplash= (TextView) findViewById(R.id.tv_splash_title);
        ivSplash= (ImageView) findViewById(R.id.iv_splash);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,0f,1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(500);
        ivSplash.setAnimation(scaleAnimation);

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
