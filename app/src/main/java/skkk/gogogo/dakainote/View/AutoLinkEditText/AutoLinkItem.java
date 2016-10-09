package skkk.gogogo.dakainote.View.AutoLinkEditText;

/**
 * Created by admin on 2016/10/9.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/9$ 12:21$.
*/
class AutoLinkItem {

    private AutoLinkMode autoLinkMode;

    private String matchedText;

    private int startPoint,endPoint;

    AutoLinkItem(int startPoint, int endPoint, String matchedText, AutoLinkMode autoLinkMode) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.matchedText = matchedText;
        this.autoLinkMode = autoLinkMode;
    }

    AutoLinkMode getAutoLinkMode() {
        return autoLinkMode;
    }

    String getMatchedText() {
        return matchedText;
    }

    int getStartPoint() {
        return startPoint;
    }

    int getEndPoint() {
        return endPoint;
    }
}