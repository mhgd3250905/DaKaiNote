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


    private TextView tvViewTitle;
    private TextView tvViewTitleTime;
    private TextView tvViewContent;
    private ImageView ivViewNote;
    private ImageView ivViewPin;
    private ImageView ivViewVoice;
    private ImageView ivViewVideo;




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
        tvViewTitle = (TextView) findViewById(R.id.tv_view_note_title);
        tvViewTitleTime = (TextView) findViewById(R.id.tv_view_note_title_time);
        tvViewContent = (TextView) findViewById(R.id.tv_view_note_content);
        ivViewNote = (ImageView) findViewById(R.id.iv_view_note_image);
        ivViewPin= (ImageView) findViewById(R.id.iv_view_note_pin);
        ivViewVoice= (ImageView) findViewById(R.id.iv_view_note_voice);
        //setViewTitleDate("几天前");
        setViewTitleTime("具体时间");
        setViewContent("具体内容");
    }

    //修改标题天数
//    public void setViewTitleDate(String date){
//        tvViewTitleDate.setText(date);
//    }

    //修改标题
    public void setViewTitle(String time){
        tvViewTitle.setText(time);
    }

    //修改标题日期时间
    public void setViewTitleTime(String time){
        tvViewTitleTime.setText(time);
    }

    //修改内容
    public void setViewContent(String content){
        tvViewContent.setText(content);
    }


    //设置pin是否存在
    public void setPinIsExist(boolean pinIsExist) {
        if (pinIsExist){
            ivViewPin.setVisibility(VISIBLE);
        }else {
            ivViewPin.setVisibility(GONE);
        }
    }

    //设置图片标签是否显示
    public void setImageIsExist(boolean exist){
        if (exist){
            ivViewNote.setVisibility(VISIBLE);
        }else {
            ivViewNote.setVisibility(GONE);
        }
    }

    //设置录音标签是否显示
    public void setVoiceIsExist(boolean exist){
        if (exist){
            ivViewVoice.setVisibility(VISIBLE);
        }else {
            ivViewVoice.setVisibility(GONE);
        }
    }

}
