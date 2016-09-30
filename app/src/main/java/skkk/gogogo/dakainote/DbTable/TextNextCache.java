package skkk.gogogo.dakainote.DbTable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by admin on 2016/9/29.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/9/29$ 22:53$.
*/
public class TextNextCache extends DataSupport implements Serializable {
    private String view_name;
    private String content;

    public String getView_name() {
        return view_name;
    }

    public void setView_name(String view_name) {
        this.view_name = view_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
