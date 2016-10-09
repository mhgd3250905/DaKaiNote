package skkk.gogogo.dakainote.View.AutoLinkEditText;

/**
 * Created by admin on 2016/10/9.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/9$ 12:22$.
*/
public enum AutoLinkMode {

    MODE_HASHTAG("HASHTAG"),
    MODE_MENTION("MENTION"),
    MODE_URL("URL"),
    MODE_PHONE("PHONE"),
    MODE_EMAIL("EMAIL"),
    MODE_CUSTOM("CUSTOM");

    private String name;

    AutoLinkMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
