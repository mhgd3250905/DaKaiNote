package skkk.gogogo.dakainote.Application;


import android.content.SharedPreferences;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;

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
    private List<Note> mNotes =new ArrayList<Note>();
    private SharedPreferences sPref;

    private static final String APP_ID="wxc747ef8eb1d7c8df";
    private IWXAPI api;

    public IWXAPI getApi() {
        return api;
    }

    private void regToWx(){
        //通过WXAPIFactory工厂，获取IWXAPI实例
        api= WXAPIFactory.createWXAPI(this,APP_ID,true);

        //将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "b0bff13900cd010d5145da59e88f213f");
        regToWx();
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

   /*
   * @方法 获取当前需要的笔记列表数据
   * @参数 null
   * @返回值 List<Note>
   */
    public List<Note> getNotes() {
        return SQLUtils.getNoteList();
    }

    /*
    * @方法 设置笔记列表
    * @参数 List<Note>
    * @返回值 null
    */
    public void setNotes(List<Note> notes) {
        this.mNotes = notes;
    }

    /* @描述 获取sPref */
    public SharedPreferences getsPref() {
        return sPref;
    }

    /* @描述 设置sPref */
    public void setsPref(SharedPreferences sPref) {
        this.sPref = sPref;
    }
}
