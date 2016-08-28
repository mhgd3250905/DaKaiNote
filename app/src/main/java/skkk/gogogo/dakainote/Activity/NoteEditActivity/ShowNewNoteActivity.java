package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.ContentText;
import skkk.gogogo.dakainote.DbTable.Image;
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
        if(isShow){
            //如果是展示页面
            initShowUI();
            llNoteDetail.setFocusable(true);
            llNoteDetail.setFocusableInTouchMode(true);
        }else {
            //如果是编辑界面
            addEditTextItem();
            tvNoteDetailTime.setText(DateUtils.getTime());
            LogUtils.Log("编辑界面时间显示为"+DateUtils.getTime());
        }
    }

    /*
    * @方法 布置展示页面
    * 1 edit设置为取消光标
    * 2 将note中包含的内容显示到布局页面中
    *
    */
    private void initShowUI() {
        //设置标题
        etNoteDetailTitle.setText(inetntNote.getTitle());
        //设置et控件失去焦点
        llNoteDetail.setFocusable(true);
        //设置时间
        tvNoteDetailTime.setText(inetntNote.getTime());
        //判断并设置pin
        isPin=inetntNote.isPinIsExist();
        if(isPin){
            ivPin.setVisibility(View.VISIBLE);
        }else {
            ivPin.setVisibility(View.INVISIBLE);
        }
        //获取内容列表
        List<ContentText> contentTextList = inetntNote.getMyContentTextList();
        //获取图片列表
        List<Image> imageList = inetntNote.getMyImageList();
        for (int i = 0; i < contentTextList.size(); i++) {
            addEditTextItem(contentTextList.get(i).getContentText());
        }
        for (int i = 0; i < imageList.size(); i++) {
            addImageItem(imageList.get(i).getImagePath());
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
            isShow=true;
        }
    }

    /*
    * @方法 在页面关闭之前重置NUM
    *
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NUM=2;
    }
}
