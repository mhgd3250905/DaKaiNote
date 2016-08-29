package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.DbTable.ContentText;
import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;

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
    protected boolean isStore=false;//是否有存储
    protected boolean isShow=false;//是否是展示页面
    protected LinearLayout llNoteDetail;
    protected Toolbar tbNoteDetail;
    protected String imagePath;//照片路径
    protected Boolean isImageExist=false;//照片是否存在
    protected NoteNew note;//笔记类
    protected List<Image> imageList=new ArrayList<Image>();//笔记图片类
    protected List<ContentText> contentTextList;//笔记内容类
    protected static final int REQUEST_IMAGE_CAPTURE=111;//拍照请求码
    protected TextView tvNoteDetailTime;
    protected EditText etNoteDetailTitle;
    protected ImageView ivPin;//pin图标
    protected boolean isPin=false;//是否pin
    protected NestedScrollView nsvNoteDetail;//note滑动控件


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
        //设置时间提示
        tvNoteDetailTime= (TextView) findViewById(R.id.tv_note_detail_time);
        //设置标题
        etNoteDetailTitle= (EditText) findViewById(R.id.et_note_detail_title);
        //设置pin图标
        ivPin= (ImageView) findViewById(R.id.iv_note_detail_pin);
        ivPin.setVisibility(View.INVISIBLE);
        //设置NestedScrollView
        nsvNoteDetail= (NestedScrollView) findViewById(R.id.nsv_note_detail);
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


    //使用相机应用进行拍照
    protected void takePicture(Activity activity) {
        imagePath= CameraImageUtils.getImagePath();
        CameraImageUtils.dispatchTakePictureIntent(activity, imagePath, REQUEST_IMAGE_CAPTURE);
        CameraImageUtils.galleryAddPic(this);
    }

}