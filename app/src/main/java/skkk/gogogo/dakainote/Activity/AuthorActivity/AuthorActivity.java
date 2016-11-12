package skkk.gogogo.dakainote.Activity.AuthorActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import skkk.gogogo.dakainote.Presenter.AuthorPresenter;
import skkk.gogogo.dakainote.R;

public class AuthorActivity extends AppCompatActivity {
    private AuthorPresenter authorPresenter;
    private Toolbar tbAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authorPresenter = new AuthorPresenter(this);


        setMyTheme();
        initUI();


    }

    /*
    * @方法 设置主题
    * @参数
    * @返回值
    */
    public void setMyTheme() {
        //设置主题
        if (authorPresenter.getThemeNight()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
        }
    }


    /*
    * @方法 初始化UI
    * @参数
    * @返回值
    */
    private void initUI() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_author);

        tbAuthor = (Toolbar) findViewById(R.id.tb_author);
        setSupportActionBar(tbAuthor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbAuthor.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void github(View view) {
        Uri uri = Uri.parse ("https://github.com/mhgd3250905/DaKaiNote");
        Intent intent = new Intent (Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }

    public void mail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"294851575@qq.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "白开水笔记反馈");
        try {
            if (!(this instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "未安装邮箱应用！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
