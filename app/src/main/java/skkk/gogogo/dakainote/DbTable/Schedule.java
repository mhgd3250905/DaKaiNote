package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：存储Schedule内容的table
* 作    者：ksheng
* 时    间：2016/8/27$ 13:56$.
*/
public class Schedule extends DataSupport implements Serializable {
    private int id;
    private Note mNote;
    private boolean scheduleChecked;
    private String scheduleContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note note) {
        this.mNote = note;
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
