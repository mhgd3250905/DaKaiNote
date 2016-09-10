package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/9/10.
 */
/*
* 
* 描    述：图片的缓存数据库
* 作    者：ksheng
* 时    间：2016/9/10$ 13:53$.
*/
public class ImageCache extends DataSupport implements Serializable {
    private int id;
    private String imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
