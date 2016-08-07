package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：自定义noteview
* 作    者：ksheng
* 时    间：
*/
public class MyNoteView extends RelativeLayout{


    private TextView tvViewTitleDate;
    private TextView tvViewTitleTime;
    private TextView tvViewContent;
    private ImageView ivViewNote;
    private boolean imageIsExist;


    public MyNoteView(Context context) {
        super(context, null);
        initView();
    }

    public MyNoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyNoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //将定义好的布局文件设置给当前的view
        View.inflate(getContext(), R.layout.view_note_design,this);
        tvViewTitleDate = (TextView) findViewById(R.id.tv_view_note_title_date);
        tvViewTitleTime = (TextView) findViewById(R.id.tv_view_note_title_time);
        tvViewContent = (TextView) findViewById(R.id.tv_view_note_content);
        ivViewNote = (ImageView) findViewById(R.id.iv_view_note_image);
        setViewTitleDate("几天前");
        setViewTitleTime("具体时间");
        setViewContent("具体内容");
    }


    public void setViewTitleDate(String date){
        tvViewTitleDate.setText(date);
    }


    public void setViewTitleTime(String time){
        tvViewTitleTime.setText(time);
    }

    public void setViewContent(String content){
        tvViewContent.setText(content);
    }

    public boolean getImageIsExist(){
        return imageIsExist;
    }

    public void setImageIsExist(boolean exist){
        if (exist){
            imageIsExist=exist;
            ivViewNote.setVisibility(VISIBLE);
        }else {
            imageIsExist=exist;
            ivViewNote.setVisibility(INVISIBLE);
        }
    }

}
