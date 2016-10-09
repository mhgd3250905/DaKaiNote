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
    private String content;
    private boolean imageIsExist;
    private boolean pinIsExist;
    private boolean voiceExist;
    private boolean scheduleIsExist;
    private long keyNum;
    private int gravity;

    private List<Image> imageList = new ArrayList<Image>();
    private List<Voice> voiceList = new ArrayList<Voice>();
    private List<Schedule> scheduleList = new ArrayList<Schedule>();

    /*
     *
     * @方法 获取我说需要的串联表Voice中的内容
     *
     */
    public List<Schedule> getMyScheduleList() {
        return DataSupport.where("notenew_id = ?", String.valueOf(id)).find(Schedule.class);
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /*
    *
    * @方法 获取我说需要的串联表Voice中的内容
    *
    */
    public List<Voice> getMyVoiceList() {
        return DataSupport.where("notenew_id = ?", String.valueOf(id)).find(Voice.class);
    }

    public List<Voice> getVoiceList() {
        return voiceList;
    }

    public void setVoiceList(List<Voice> voiceList) {
        this.voiceList = voiceList;
    }


    /*
    * @方法 获取我所需要的串联表Image中的内容
    *
    */
    public List<Image> getMyImageList() {
        return DataSupport.where("notenew_id = ?", String.valueOf(id)).find(Image.class);
    }
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }




    public boolean isScheduleIsExist() {
        return scheduleIsExist;
    }

    public void setScheduleIsExist(boolean scheduleIsExist) {
        this.scheduleIsExist = scheduleIsExist;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVoiceExist() {
        return voiceExist;
    }

    public void setVoiceExist(boolean voiceExist) {
        this.voiceExist = voiceExist;
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

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    @Override
    public String toString() {
        return "NoteNew{" +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", imageIsExist=" + imageIsExist +
                ", pinIsExist=" + pinIsExist +
                ", voiceExist=" + voiceExist +
                ", keyNum=" + keyNum +
                ", imageList=" + imageList +
                ", voiceList=" + voiceList +
                '}';
    }
}
