package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity.NoteImageActivity;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.AudioButton;
import skkk.gogogo.dakainote.View.MyImageView;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：可以进行动态增加控件的note编辑界面
* 作    者：ksheng
* 时    间：2016/8/26$ 22:18$.
*/
public class UINewNoteActivity extends BaseNewNoteActivity {
    protected static int NUM = 2;
    protected static int REQUEST_NOTE_IMAGE_DELETE=13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = new NoteNew();
    }

    protected void addEditTextItem() {
        LayoutInflater li = LayoutInflater.from(this);
        View view_text = li.inflate(R.layout.item_note_text, null);
        EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        llNoteDetail.addView(view_text);
        etText.setFocusable(true);
        etText.setFocusableInTouchMode(true);
        etText.requestFocus();
    }

    protected void addEditTextItem(String text) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_text = li.inflate(R.layout.item_note_text, null);
        EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        etText.setText(text);
        llNoteDetail.addView(view_text);
    }


    protected void addImageItem(String imagePath,int imageId) {
        LayoutInflater li = LayoutInflater.from(this);
        final View view_image = li.inflate(R.layout.item_note_image, null);
        final MyImageView ivInsert = (MyImageView) view_image.findViewById(R.id.iv_note_image);
        ivInsert.setBitmapFromPath(imagePath);
        ivInsert.setImageId(imageId);
        LogUtils.Log("设置image位置为" + llNoteDetail.getChildCount());
        ivInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                testView = view_image;
                intent.putExtra("image_click", ivInsert.getImagePath());
                intent.setClass(UINewNoteActivity.this, NoteImageActivity.class);
                startActivityForResult(intent, REQUEST_NOTE_IMAGE_DELETE);
            }
        });
        llNoteDetail.addView(view_image);
    }


    protected void addImageItem(String imagePath) {
        LayoutInflater li = LayoutInflater.from(this);
        final View view_image = li.inflate(R.layout.item_note_image, null);
        final MyImageView ivInsert = (MyImageView) view_image.findViewById(R.id.iv_note_image);
        ivInsert.setBitmapFromPath(imagePath);
        LogUtils.Log("设置image位置为" + llNoteDetail.getChildCount());

        ivInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                testView = view_image;
                intent.putExtra("image_click", ivInsert.getImagePath());
                intent.setClass(UINewNoteActivity.this, NoteImageActivity.class);
                startActivityForResult(intent, REQUEST_NOTE_IMAGE_DELETE);
            }
        });
        llNoteDetail.addView(view_image);
    }


    protected void addVoiceItem(String voicePath) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_voice = li.inflate(R.layout.item_note_voice, null);
        AudioButton abVoice = (AudioButton) view_voice.findViewById(R.id.ab_note_voice);
        abVoice.setVoicePath(voicePath);
        llNoteDetail.addView(view_voice);
    }

}
