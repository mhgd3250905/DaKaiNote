package skkk.gogogo.dakainote.Application;


import org.litepal.LitePalApplication;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class MyApplication extends LitePalApplication {
    private final static int ITEM_ENTER_FLAG=30801;
    private final static int ITEM_DEL_FLAG=30802;
    private int childCountInScheduleItem=2;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int getItemDelFlag() {
        return ITEM_DEL_FLAG;
    }

    public static int getItemEnterFlag() {
        return ITEM_ENTER_FLAG;
    }

    public int getChildCountInScheduleItem() {
        return childCountInScheduleItem;
    }

    public void setChildCountInScheduleItem(int childCountInScheduleItem) {
        this.childCountInScheduleItem = childCountInScheduleItem;
    }
}
