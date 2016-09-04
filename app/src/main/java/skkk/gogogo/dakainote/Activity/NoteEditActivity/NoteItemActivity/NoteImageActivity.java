package skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;

public class NoteImageActivity extends AppCompatActivity {
    protected SimpleDraweeView ivNoteItemImage;
    protected Button btnNoteImageDelete,btnNoteImageSave;
    protected String image_click_path;
    protected Intent intentGet;
    protected PointF focusPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();
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
        btnNoteImageSave= (Button) findViewById(R.id.btn_note_image_save);
        Uri uri = Uri.parse("file://" + image_click_path);
        LogUtils.Log("file://" + image_click_path);
        ivNoteItemImage.setImageURI(uri);
    }
}
