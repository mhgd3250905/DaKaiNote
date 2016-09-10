package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;

import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.Fragment.ImageNewNoteFragment;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/9/10.
 */
/*
* 
* 描    述：包含图片显示逻辑的Activity
* 作    者：ksheng
* 时    间：2016/9/10$ 13:17$.
*/
public class ImageNewNoteActivity extends VoiceNewNoteActivity {
    ImageNewNoteFragment mImageNewNoteFragment;
    protected long noteKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDefaultFragment();
        initNote();
    }

    /*
  * @方法 初始化note
  *
  */
    private void initNote() {
        note = new NoteNew();
        /* @描述 保存唯一标识码 */
        noteKey=System.currentTimeMillis();
        note.setKeyNum(noteKey);//保存标识
    }


    /*
   * @desc 加入默认的Fragment界面
   * @时间 2016/8/1 21:44
   */
    private void addDefaultFragment() {
        mImageNewNoteFragment = new ImageNewNoteFragment(noteKey);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_image,mImageNewNoteFragment).commit();
    }

}
