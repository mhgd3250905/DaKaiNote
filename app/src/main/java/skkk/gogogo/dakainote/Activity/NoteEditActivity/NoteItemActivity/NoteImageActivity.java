package skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;

public class NoteImageActivity extends AppCompatActivity implements View.OnClickListener {
    protected SimpleDraweeView ivNoteItemImage;
    protected Button btnNoteImageDelete;
    protected String image_click_path;
    protected Intent intentGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();
        initData();
        initEvent();

    }


    /*
    * @方法 初始化之前获取需要的数据
    *
    */
    private void beforeStart() {
        intentGet = getIntent();
        image_click_path = intentGet.getStringExtra("image_click");
    }

    /*
    * @方法 初始化UI
    *
    */
    private void initUI() {
        setContentView(R.layout.activity_note_image);
        ivNoteItemImage= (SimpleDraweeView) findViewById(R.id.iv_note_item_image);
        btnNoteImageDelete= (Button) findViewById(R.id.btn_note_image_delete);
    }

    /*
    * @方法 载入数据
    *
    */
    private void initData() {
        Uri uri = Uri.parse("file://" + image_click_path);
        LogUtils.Log("file://" + image_click_path);
        ivNoteItemImage.setImageURI(uri);
    }


    /*
    * @方法 初始化监听事件
    *
    */
    private void initEvent() {
        btnNoteImageDelete.setOnClickListener(this);
    }

    /*
    * @方法 根据id点击事件
    *
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_note_image_delete:
                //删除文件
                File deleteFile=new File(image_click_path);
                deleteFile.delete();
                this.setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
