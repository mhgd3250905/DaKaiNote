package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
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
public class BaseNewNoteActivity extends AppCompatActivity{

    protected static int REQUEST_NOTE_IMAGE_DELETE = 13;

    protected boolean isStore=false;//是否有存储
    protected boolean isShow=false;//是否是展示页面
    protected LinearLayout llNoteDetail;
    protected Toolbar tbNoteDetail;
    protected String imagePath;//照片路径

    protected Boolean isImageExist=false;//照片是否存在
    protected boolean isPin=false;//是否pin
    protected boolean isVoiceExist=false;//是否有录音
    protected boolean isScheduleExist=false;//是否有Schedule

    protected NoteNew note;//笔记类
    protected static final int REQUEST_IMAGE_CAPTURE=111;//拍照请求码
    protected TextView tvNoteDetailTime;
    protected EditText etNoteDetailTitle;
    protected ImageView ivPin;//pin图标
    protected NestedScrollView nsvNoteDetail;//note滑动控件
    protected RecordButton rbVoice;//录音按钮
    protected CoordinatorLayout clNoteDetails;//编辑界面的框架
    protected EditText etFirstSchedule;//单一的内容显示Edit

    protected FrameLayout fl_note_iamge;
    protected FrameLayout fl_note_voice;
    protected FrameLayout fl_note_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        llNoteDetail= (LinearLayout) findViewById(R.id.ll_note_detail);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_new_detail);
        setSupportActionBar(tbNoteDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.note_edit_menu);

        //设置时间提示
        tvNoteDetailTime= (TextView) findViewById(R.id.tv_note_detail_time);
        //设置标题
        etNoteDetailTitle= (EditText) findViewById(R.id.et_note_detail_title);
        //设置pin图标
        ivPin= (ImageView) findViewById(R.id.iv_note_detail_pin);
        ivPin.setVisibility(View.INVISIBLE);

        clNoteDetails= (CoordinatorLayout) findViewById(R.id.cl_note_detail);
        //设置单一的内容显示EditText
        etFirstSchedule = (EditText) findViewById(R.id.et_first_schedule);//基础的et以及第一个Item中的et
        //设置note中用来显示image的fl
        fl_note_iamge= (FrameLayout) findViewById(R.id.fl_note_image);
        //设置note中用来显示Voice的fl
        fl_note_voice= (FrameLayout) findViewById(R.id.fl_note_voice);
        //设置note中用来显示Schedule的fl
        fl_note_schedule= (FrameLayout) findViewById(R.id.fl_note_schedule);
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
    }

    /*
    * @方法 使用相机应用进行拍照
    *
    */
    protected void takePicture(Activity activity) {
        try {
            imagePath= CameraImageUtils.getImagePath();
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
