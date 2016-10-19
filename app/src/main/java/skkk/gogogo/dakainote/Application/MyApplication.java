package skkk.gogogo.dakainote.Application;


import org.litepal.LitePalApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class MyApplication extends LitePalApplication{
    private final static int ITEM_ENTER_FLAG=30801;
    private final static int ITEM_DEL_FLAG=30802;
    private int childCountInScheduleItem=2;
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 调用统计SDK
         *
         * @param appKey
         *            Bmob平台的Application ID
         * @param channel
         *            当前包所在渠道，可以为空
         * @return 是否成功，如果失败请看logcat，可能是混淆或so文件未正确配置
         */

        cn.bmob.statistics.AppStat.i("b0bff13900cd010d5145da59e88f213f","");
        //第一：默认初始化
        Bmob.initialize(this, "b0bff13900cd010d5145da59e88f213f");
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
