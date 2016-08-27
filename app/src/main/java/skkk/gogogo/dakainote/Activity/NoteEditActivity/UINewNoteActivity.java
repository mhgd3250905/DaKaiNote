package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.R;
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
    protected static int NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = new NoteNew();
    }

    protected void addEditTextItem() {
        LayoutInflater li = LayoutInflater.from(this);
        View view_text = li.inflate(R.layout.item_note_text, null);
        EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        llNoteDetail.addView(view_text, NUM);
        etText.setFocusable(true);
        etText.setFocusableInTouchMode(true);
        etText.requestFocus();
        NUM++;
    }

    protected void addEditTextItem(String text) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_text = li.inflate(R.layout.item_note_text, null);
        EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        etText.setText(text);
        llNoteDetail.addView(view_text, NUM);
        NUM++;
    }


    protected void addImageItem(String imagePath) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_image = li.inflate(R.layout.item_note_image, null);
        MyImageView ivInsert = (MyImageView) view_image.findViewById(R.id.iv_note_image);
        ivInsert.setBitmapFromPath(imagePath);
        llNoteDetail.addView(view_image, NUM);
        NUM++;
    }

}
