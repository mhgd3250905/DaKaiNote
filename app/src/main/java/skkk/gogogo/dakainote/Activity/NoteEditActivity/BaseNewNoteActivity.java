package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import skkk.gogogo.dakainote.DbTable.NoteNew;
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
public class BaseNewNoteActivity extends AppCompatActivity {

    protected static int REQUEST_NOTE_IMAGE_DELETE = 13;

    protected boolean isStore = false;//是否有存储
    protected boolean isShow = false;//是否是展示页面
    protected LinearLayout llNoteDetail;
    protected Toolbar tbNoteDetail;
    protected String imagePath;//照片路径

    protected Boolean isImageExist = false;//照片是否存在
    protected boolean isPin = false;//是否pin
    protected boolean isVoiceExist = false;//是否有录音
    protected boolean isScheduleExist = false;//是否有Schedule

    protected NoteNew note;//笔记类
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref=getSharedPreferences("note",MODE_PRIVATE);
        isNight=sPref.getBoolean("night",false);

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
        //初始化SP

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
