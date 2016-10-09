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

    MODE_HASHTAG("Hashtag"),
    MODE_MENTION("Mention"),
    MODE_URL("Url"),
    MODE_PHONE("Phone"),
    MODE_EMAIL("Email"),
    MODE_CUSTOM("Custom");

    private String name;

    AutoLinkMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
