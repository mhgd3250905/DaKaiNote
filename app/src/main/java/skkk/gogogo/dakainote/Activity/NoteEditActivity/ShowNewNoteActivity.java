package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.view.View;

import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        if (isShow) {
            //如果是展示页面
            initShowUI();
            llNoteDetail.setFocusable(true);
            llNoteDetail.setFocusableInTouchMode(true);
        } else {
            //如果是编辑界面
            tvNoteDetailTime.setText(DateUtils.getTime());
            LogUtils.Log("编辑界面时间显示为" + DateUtils.getTime());
        }
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

//        //获取图片列表
//        List<Image> imageList = inetntNote.getMyImageList();
//        //获取录音列表
//        List<Voice> voiceList = inetntNote.getMyVoiceList();
//        for (int i = 0; i < 10; i++) {
//
//            for (int j = 0; j < contentTextList.size(); j++) {
//                if (contentTextList.get(j).getNum() == i) {
//                    addEditTextItem(contentTextList.get(j).getContentText());
//                }
//            }
//
//            for (int j = 0; j < imageList.size(); j++) {
//                if (imageList.get(j).getNum() == i) {
//                    addImageItem(imageList.get(j).getImagePath(),imageList.get(j).getId());
//                }
//            }
//
//            for (int j = 0; j < voiceList.size(); j++) {
//                if (voiceList.get(j).getNum() == i) {
//                    addVoiceItem(voiceList.get(j).getVoicePath());
//                }
//            }
//
//        }


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
