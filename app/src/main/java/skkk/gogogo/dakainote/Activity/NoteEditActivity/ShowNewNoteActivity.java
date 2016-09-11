package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.Fragment.ImageNewNoteFragment;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：继承自ArcNewNoteActivity的专门用于设计显示内容的Activity
* 作    者：ksheng
* 时    间：2016/8/27$ 21:59$.
*/
public class ShowNewNoteActivity extends UINewNoteActivity {
    protected NoteNew inetntNote;
    protected ImageNewNoteFragment mImageNewNoteFragment;
    protected long noteKey;
    MyImageThread myImageThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();

        if (isShow) {
            //如果是展示页面
            noteKey=inetntNote.getKeyNum();
            initShowUI();
            llNoteDetail.setFocusable(true);
            llNoteDetail.setFocusableInTouchMode(true);

        } else {
            //如果是编辑界面
            tvNoteDetailTime.setText(DateUtils.getTime());
            LogUtils.Log("编辑界面时间显示为" + DateUtils.getTime());
            initNote();
            addDefaultFragment();
        }

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

    /*
    * @方法 布置展示页面
    * 1 edit设置为取消光标
    * 2 将note中包含的内容显示到布局页面中
    *
    */
    private void initShowUI() {
        /* @描述 设置标题 */
        etNoteDetailTitle.setText(inetntNote.getTitle());
        /* @描述 设置editView失去焦点 */
        llNoteDetail.setFocusable(true);
        /* @描述 设置时间 */
        tvNoteDetailTime.setText(inetntNote.getTime());
        /* @描述 判断并设置pin */
        isPin = inetntNote.isPinIsExist();
        if (isPin) {
            ivPin.setVisibility(View.VISIBLE);
        } else {
            ivPin.setVisibility(View.INVISIBLE);
        }
        /* @描述 设置内容 */
        etNewNoteDetail.setText(inetntNote.getContent());
        /* @描述 载入图片 */
        if (inetntNote.isImageIsExist()){
            //说明存在图片
            //获取图片列表
            myImageThread=new MyImageThread();
            myImageThread.start();
            mImageNewNoteFragment = new ImageNewNoteFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fl_note_image,mImageNewNoteFragment).commit();
        }
    }

    /* @描述 用来转存图片的线程 */
    class MyImageThread extends Thread{
        @Override
        public void run() {
            super.run();
            List<Image> imageList = inetntNote.getMyImageList();
            for (int i = 0; i < imageList.size(); i++) {
                ImageCache imageCache=new ImageCache();
                imageCache.setImagePath(imageList.get(i).getImagePath());
                imageCache.setNoteKey(inetntNote.getKeyNum());
                imageCache.save();
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putLong("notekey",noteKey);
                msg.setData(bundle);
                mImageNewNoteFragment.handler.sendMessage(msg);
            }
        }
    }


    /*
    * @方法 获取传入的
    *
    */
    private void beforeStart() {
        //获取传入的intent中包含的note数据
        inetntNote = (NoteNew) getIntent().getSerializableExtra("note");
        if (inetntNote != null) {
            //如果传入note不是空的那么就说明是展示页面
            isShow = true;
        }
    }

    /*
    * @方法 在页面关闭之前重置NUM
    *
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
