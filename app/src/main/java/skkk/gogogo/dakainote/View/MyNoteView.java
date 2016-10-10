package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;

/*
* 
* 描    述：自定义noteview
* 作    者：ksheng
* 时    间：
*/
public class MyNoteView extends RelativeLayout {

    private TextView tvViewTitle;
    private TextView tvViewTitleTime;
    private TextView tvViewContent;
    private ImageView ivViewNote;
    private ImageView ivViewPin;
    private ImageView ivViewVoice;
    private ImageView ivViewSchedule;
    private CheckBox cbNoteDeleteCheck;
    private LinearLayout llNoteListFlag;
    private boolean deleteChecked = false;

    public MyNoteView(Context context) {
        super(context);
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
        View.inflate(getContext(), R.layout.view_note_design_2, this);
        tvViewTitle = (TextView) findViewById(R.id.tv_view_note_title);
        tvViewTitleTime = (TextView) findViewById(R.id.tv_view_note_title_time);
        tvViewContent = (TextView) findViewById(R.id.tv_view_note_content);
        ivViewNote = (ImageView) findViewById(R.id.iv_view_note_image);
        ivViewPin = (ImageView) findViewById(R.id.iv_view_note_pin);
        ivViewVoice = (ImageView) findViewById(R.id.iv_view_note_voice);
        ivViewSchedule = (ImageView) findViewById(R.id.iv_view_note_schedule);
        cbNoteDeleteCheck = (CheckBox) findViewById(R.id.cb_note_delete_check);
        llNoteListFlag = (LinearLayout) findViewById(R.id.ll_note_list_flag);

        /* @描述 cbNoteDeleteCheck 的check监听事件 */
        cbNoteDeleteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果被选中控件删除flag设置为true
                    deleteChecked = true;
                    LogUtils.Log("item删除flag设置为true");
                } else {
                    //如果取消选中控件删除flag设置为false
                    deleteChecked = false;
                    LogUtils.Log("item删除flag设置为flag");
                }
            }
        });

        //setViewTitleDate("几天前");
        setViewTitleTime("具体时间");
        setViewContent("具体内容");

    }


    /* @描述 设置Card中下栏图标的显示 */
    public void setImageFlagShow(boolean show) {
        if (show) {
            llNoteListFlag.setVisibility(VISIBLE);
        } else {
            llNoteListFlag.setVisibility(GONE);
        }
    }

    //修改标题
    public void setViewTitle(String time) {
        tvViewTitle.setText(time);
    }

    //修改标题日期时间
    public void setViewTitleTime(String time) {
        tvViewTitleTime.setText(time);
    }

    //修改内容
    public void setViewContent(String content) {
        tvViewContent.setText(content);
    }


    //设置pin是否存在
    public void setPinIsExist(boolean pinIsExist) {
        if (pinIsExist) {
            ivViewPin.setVisibility(VISIBLE);
        } else {
            ivViewPin.setVisibility(GONE);
        }
    }

    //设置图片标签是否显示
    public void setImageIsExist(boolean exist) {
        if (exist) {
            ivViewNote.setVisibility(VISIBLE);
        } else {
            ivViewNote.setVisibility(GONE);
        }
    }

    //设置录音标签是否显示
    public void setVoiceIsExist(boolean exist) {
        if (exist) {
            ivViewVoice.setVisibility(VISIBLE);
        } else {
            ivViewVoice.setVisibility(GONE);
        }
    }

    //设置录音标签是否显示
    public void setScheduleIsExist(boolean exist) {
        if (exist) {
            ivViewSchedule.setVisibility(VISIBLE);
        } else {
            ivViewSchedule.setVisibility(GONE);
        }
    }

    //获取控件的deleteChecked flag
    public boolean isDeleteChecked() {
        return deleteChecked;
    }

    //显示checkbox的勾选状态
    public void setCheckBoxShow(boolean show) {
        if (show) {
            //显示
            cbNoteDeleteCheck.setVisibility(VISIBLE);
            cbNoteDeleteCheck.setChecked(false);
        } else {
            //隐藏
            cbNoteDeleteCheck.setVisibility(GONE);
        }
    }

    /*
    * @方法 设置checkbox勾选
    *
    */
    public void setCheckboxStatus(boolean check) {
        if (check) {
            cbNoteDeleteCheck.setChecked(true);
        } else {
            cbNoteDeleteCheck.setChecked(false);
        }
    }

    /*
    * @方法 获取checkbox的勾选状态
    *
    */
    public boolean getCheckboxStatus() {
        return cbNoteDeleteCheck.isChecked();
    }

}
