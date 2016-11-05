package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/8/27.
 */
/*
*
* 描    述：
* 作    者：ksheng
* 时    间：2016/8/27$ 14:27$.
*/
public class Note extends BmobObject {
    private String title;
    private String time;
    private String content;
    private boolean imageisexist;
    private boolean pinisexist;
    private boolean voiceexist;
    private boolean scheduleisexist;
    private boolean specialisexist;
    private long keynum;
    private int gravity;

    public boolean isSpecialisexist() {
        return specialisexist;
    }

    public void setSpecialisexist(boolean specialisexist) {
        this.specialisexist = specialisexist;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public boolean isImageisexist() {
        return imageisexist;
    }

    public void setImageisexist(boolean imageisexist) {
        this.imageisexist = imageisexist;
    }

    public long getKeynum() {
        return keynum;
    }

    public void setKeynum(long keynum) {
        this.keynum = keynum;
    }

    public boolean isPinisexist() {
        return pinisexist;
    }

    public void setPinisexist(boolean pinisexist) {
        this.pinisexist = pinisexist;
    }

    public boolean isScheduleisexist() {
        return scheduleisexist;
    }

    public void setScheduleisexist(boolean scheduleisexist) {
        this.scheduleisexist = scheduleisexist;
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

    public boolean isVoiceexist() {
        return voiceexist;
    }

    public void setVoiceexist(boolean voiceexist) {
        this.voiceexist = voiceexist;
    }
}
