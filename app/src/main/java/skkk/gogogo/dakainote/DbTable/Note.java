package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class Note extends DataSupport implements Serializable {
    //private int id;
    private String title;
    private String time;
    private String content;
    private boolean imageIsExist;
    private String imagePath;
    private int start;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public boolean isImageIsExist() {
        return imageIsExist;
    }

    public void setImageIsExist(boolean imageIsExist) {
        this.imageIsExist = imageIsExist;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", imageIsExist=" + imageIsExist +
                ", imagePath='" + imagePath + '\'' +
                ", start=" + start +
                '}';
    }
}
