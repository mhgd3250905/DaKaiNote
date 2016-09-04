package skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;

public class NoteImageActivity extends AppCompatActivity {
    protected ImageView ivNoteItemImage;
    protected Button btnNoteImageDelete,btnNoteImageSave;
    private String image_click_path;
    private Intent intentGet;

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
        ivNoteItemImage= (ImageView) findViewById(R.id.iv_note_item_image);
        btnNoteImageDelete= (Button) findViewById(R.id.btn_note_image_delete);
        btnNoteImageSave= (Button) findViewById(R.id.btn_note_image_save);
        ivNoteItemImage.setImageBitmap(CameraImageUtils.getPreciselyBitmap(image_click_path, 800));
    }
}
