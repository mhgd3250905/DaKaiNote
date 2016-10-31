package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/10/31.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/31$ 21:24$.
*/
public class Communication extends BmobObject {
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
