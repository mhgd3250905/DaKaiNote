package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：存储Schedule内容的table
* 作    者：ksheng
* 时    间：2016/8/27$ 13:56$.
*/
public class Schedule extends BmobObject {
    private boolean scheduleChecked;
    private String scheduleContent;
    private int note_id;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public boolean isScheduleChecked() {
        return scheduleChecked;
    }

    public void setScheduleChecked(boolean scheduleChecked) {
        this.scheduleChecked = scheduleChecked;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }
}
