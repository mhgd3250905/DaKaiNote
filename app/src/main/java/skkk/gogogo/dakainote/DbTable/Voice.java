package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/30.
 */
/*
* 
* 描    述：录音data
* 作    者：ksheng
* 时    间：2016/8/30$ 22:41$.
*/
public class Voice extends DataSupport implements Serializable {
    private int id;
    private Note note;
    private String voicePath;
    private int num;
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getVoicePath() {
        return voicePath;
    }
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
}

