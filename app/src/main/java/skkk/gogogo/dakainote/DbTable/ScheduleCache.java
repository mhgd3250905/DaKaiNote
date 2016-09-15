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
public class ScheduleCache extends DataSupport implements Serializable {
    private int id;
    private boolean scheduleChecked;
    private String scheduleContent;
    private long noteKey;

    public long getNoteKey() {
        return noteKey;
    }

    public void setNoteKey(long noteKey) {
        this.noteKey = noteKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
