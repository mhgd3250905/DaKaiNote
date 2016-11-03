package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/8/30.
 */
/*
* 
* 描    述：录音data
* 作    者：ksheng
* 时    间：2016/8/30$ 22:41$.
*/
public class Voice extends BmobObject {
    private String voicePath;
    private int num;
    private int note_id;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
}

