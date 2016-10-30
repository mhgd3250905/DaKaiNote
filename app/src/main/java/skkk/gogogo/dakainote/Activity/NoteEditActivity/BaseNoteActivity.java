package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.AutoLinkEditText.AutoLinkEditText;
import skkk.gogogo.dakainote.View.AutoLinkEditText.AutoLinkMode;
import skkk.gogogo.dakainote.View.AutoLinkEditText.AutoLinkOnClickListener;
import skkk.gogogo.dakainote.View.RecordButton;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：新的最新思路的便签编辑页面
* 作    者：ksheng
* 时    间：2016/8/26$ 21:47$.
*/
public class BaseNoteActivity extends AppCompatActivity {

    protected boolean isStore = false;//是否有存储
    protected LinearLayout llNoteDetail;
    protected Toolbar tbNoteDetail;
    protected String imagePath;//照片路径

    protected Boolean isImageExist = false;//照片是否存在
    protected boolean isPin = false;//是否pin
    protected boolean isVoiceExist = false;//是否有录音
    protected boolean isScheduleExist = false;//是否有Schedule

    protected Note note;//笔记类
    protected static final int REQUEST_IMAGE_CAPTURE = 111;//拍照请求码
    protected TextView tvNoteDetailTime;
    protected EditText etNoteDetailTitle;
    protected ImageView ivPin;//pin图标
    protected RecordButton rbVoice;//录音按钮
    protected CoordinatorLayout clNoteDetails;//编辑界面的框架
    protected AutoLinkEditText etFirstSchedule;//单一的内容显示Edit

    protected FrameLayout fl_note_iamge;
    protected FrameLayout fl_note_voice;

    protected boolean checkFlag = false;
    protected String checkString;

    protected SharedPreferences sPref;

    protected boolean isNight=false;
    protected MyApplication myApplication;

    protected LinearLayout llNoteAgain;
    protected CheckBox cbfirstSchedule;
    protected NestedScrollView nsvEditAgain;
    protected int mPos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Application实例
        myApplication= (MyApplication) getApplicationContext();
        //获取缓存sPerf
        sPref=myApplication.getsPref();
        isNight=sPref.getBoolean("night",false);
        //设置主题
        if (isNight){
            setTheme(R.style.AppThemeNight);
        }else {
            setTheme(R.style.AppTheme);
        }

        initUI();
        initEvent();
    }

    /*
    * @方法 初始化UI界面
    *
    */
    private void initUI() {
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
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }


        setContentView(R.layout.activity_base_new_note);
        //设置框架
        llNoteDetail = (LinearLayout) findViewById(R.id.ll_note_detail);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_new_detail);
        setSupportActionBar(tbNoteDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.note_edit_menu);
        //设置时间提示
        tvNoteDetailTime = (TextView) findViewById(R.id.tv_note_detail_time);
        //设置标题
        etNoteDetailTitle = (EditText) findViewById(R.id.et_note_detail_title);
        //设置pin图标
        ivPin = (ImageView) findViewById(R.id.iv_note_detail_pin);
        ivPin.setVisibility(View.INVISIBLE);

        clNoteDetails = (CoordinatorLayout) findViewById(R.id.cl_note_detail);
        //设置单一的内容显示EditText
        etFirstSchedule = (AutoLinkEditText) findViewById(R.id.et_first_schedule);//基础的et以及第一个Item中的et
        etFirstSchedule.addAutoLinkMode(AutoLinkMode.MODE_PHONE, AutoLinkMode.MODE_URL);
        checkString = etFirstSchedule.getText().toString();

        //设置note中用来显示image的fl
        fl_note_iamge = (FrameLayout) findViewById(R.id.fl_note_image);
        //设置note中用来显示Voice的fl
        fl_note_voice = (FrameLayout) findViewById(R.id.fl_note_voice);


        llNoteAgain = (LinearLayout) findViewById(R.id.ll_edit_again);//包裹在item的LL
        cbfirstSchedule = (CheckBox) findViewById(R.id.cb_first_schedule);//item中的cb
        nsvEditAgain = (NestedScrollView) findViewById(R.id.nsv_edit_again);//ll外面的NSV

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etFirstSchedule, InputMethodManager.SHOW_FORCED);

    }

    /*
     * @方法 设置监听事件
     *
     */
    private void initEvent() {
        //为toolbar 添加返回按钮
        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etFirstSchedule.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, final String matchedText) {
                switch (autoLinkMode.toString()) {
                    case "PHONE":
                        Snackbar.make(llNoteDetail,
                                getResources().getString(R.string.call_phone)+matchedText,
                                Snackbar.LENGTH_SHORT).setAction("拨号", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                //url:统一资源定位符
                                //uri:统一资源标示符（更广）
                                intent.setData(Uri.parse("tel:" + matchedText));
                                //开启系统拨号器
                                startActivity(intent);
                            }
                        }).show();
                        break;
                    case "URL":
                        Snackbar.make(llNoteDetail,
                                getResources().getString(R.string.open_url)+matchedText,
                                Snackbar.LENGTH_SHORT).setAction("链接", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse("http://" + matchedText);
                                Intent urlIntent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(urlIntent);
                            }
                        }).show();
                        break;
                }
            }
        });
    }

    /*
    * @方法 使用相机应用进行拍照
    *
    */
    protected void takePicture(Activity activity) {
        try {
            imagePath = CameraImageUtils.getImagePath();
            CameraImageUtils.dispatchTakePictureIntent(activity, imagePath, REQUEST_IMAGE_CAPTURE);
            CameraImageUtils.galleryAddPic(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
* @方法 添加菜单
*
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        return true;
    }

}
