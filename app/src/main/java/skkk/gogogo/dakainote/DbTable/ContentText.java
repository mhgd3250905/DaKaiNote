package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：note中的内容text
* 作    者：ksheng
* 时    间：2016/8/27$ 14:46$.
*/
public class ContentText extends DataSupport implements Serializable {
    private NoteNew noteNew;
    private String contentText;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }


    public NoteNew getNoteNew() {
        return noteNew;
    }

    public void setNoteNew(NoteNew noteNew) {
        this.noteNew = noteNew;
    }
}
