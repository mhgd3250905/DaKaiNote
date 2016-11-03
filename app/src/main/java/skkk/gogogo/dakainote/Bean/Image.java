package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：存储图片内容的table
* 作    者：ksheng
* 时    间：2016/8/27$ 13:56$.
*/
public class Image extends BmobObject {
    private String imagePath;
    private int num;
    private int note_id;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
