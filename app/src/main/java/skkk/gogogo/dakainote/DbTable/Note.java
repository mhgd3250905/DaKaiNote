package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class Note extends DataSupport{
    private int id;
    private String date;
    private String time;
    private String content;
    private boolean imageIsExist;
    private boolean star;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isImageIsExist() {
        return imageIsExist;
    }

    public void setImageIsExist(boolean imageIsExist) {
        this.imageIsExist = imageIsExist;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
