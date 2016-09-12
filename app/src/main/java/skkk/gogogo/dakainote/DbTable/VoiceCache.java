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
public class VoiceCache extends DataSupport implements Serializable {
    private int id;
    private String voicePath;
    private long noteKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNoteKey() {
        return noteKey;
    }

    public void setNoteKey(long noteKey) {
        this.noteKey = noteKey;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
}

