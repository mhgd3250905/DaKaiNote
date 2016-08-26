package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：可以进行动态增加控件的note编辑界面
* 作    者：ksheng
* 时    间：2016/8/26$ 22:18$.
*/
public class UINewNoteActivity extends BaseNewNoteActivity{
    ViewGroup.LayoutParams lp;
    private static int NUM=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addEditTextItem(){
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater li = LayoutInflater.from(this);
        View view= li.inflate(R.layout.item_note_text,null);
        llNoteDetail.addView(view,NUM,lp);
        NUM++;
    }

    protected void addImageItem(){
        LayoutInflater li = LayoutInflater.from(this);
        View view= li.inflate(R.layout.item_note_image, null);
        ImageView ivInsert= (ImageView) view.findViewById(R.id.iv_note_image);
        ivInsert.setImageResource(R.drawable.item_camera);
        llNoteDetail.addView(view,NUM);
        NUM++;
    }
}
