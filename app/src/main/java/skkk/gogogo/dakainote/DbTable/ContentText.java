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
    private Note mNote;
    private String contentText;
    private int id;
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

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }


    public Note getNote() {
        return mNote;
    }

    public void setNote(Note note) {
        this.mNote = note;
    }
}
