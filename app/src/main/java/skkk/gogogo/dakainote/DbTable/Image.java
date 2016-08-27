package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：存储图片内容的table
* 作    者：ksheng
* 时    间：2016/8/27$ 13:56$.
*/
public class Image extends DataSupport implements Serializable {
    private int id;
    private NoteNew noteNew;
    private String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public NoteNew getNoteNew() {
        return noteNew;
    }

    public void setNoteNew(NoteNew noteNew) {
        this.noteNew = noteNew;
    }
}
