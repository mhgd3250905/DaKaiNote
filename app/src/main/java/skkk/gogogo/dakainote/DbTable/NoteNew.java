package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/27.
 */
/*
*
* 描    述：
* 作    者：ksheng
* 时    间：2016/8/27$ 14:27$.
*/
public class NoteNew extends DataSupport implements Serializable {
    private int id;
    private String title;
    private String time;
    private boolean imageIsExist;
    private boolean pinIsExist;
    private long keyNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<Image> imageList=new ArrayList<Image>();
    private List<ContentText> contentTextList=new ArrayList<ContentText>();


    public List<ContentText> getContentTextList() {
        return contentTextList;
    }

    /*
    * @方法 获取我说需要的串联表ContentText中的内容
    *
    */
    public List<ContentText> getMyContentTextList() {
        return DataSupport.where("notenew_id = ?", String.valueOf(id)).find(ContentText.class);
    }


    public void setContentTextList(List<ContentText> contentTextList) {
        this.contentTextList = contentTextList;
    }

    public boolean isImageIsExist() {
        return imageIsExist;
    }

    public void setImageIsExist(boolean imageIsExist) {
        this.imageIsExist = imageIsExist;
    }

    public List<Image> getImageList() {
        return imageList ;
    }

    /*
    * @方法 获取我所需要的串联表Image中的内容
    *
    */
    public List<Image> getMyImageList() {
        return DataSupport.where("notenew_id = ?", String.valueOf(id)).find(Image.class);
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public long getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(long keyNum) {
        this.keyNum = keyNum;
    }

    public boolean isPinIsExist() {
        return pinIsExist;
    }

    public void setPinIsExist(boolean pinIsExist) {
        this.pinIsExist = pinIsExist;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}