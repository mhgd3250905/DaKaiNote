package skkk.gogogo.dakainote.Presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import skkk.gogogo.dakainote.Activity.SettingActivity.SettingActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.MyUtils.LogUtils;

/**
 * Created by admin on 2016/10/23.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/23$ 19:49$.
*/
public class SettingPresenter {

    private SettingActivity mSettingActivity;
    private SharedPreferences sPref;
    private MyApplication mApplication;

    public SettingPresenter(SettingActivity settingActivity) {
        mSettingActivity = settingActivity;
        mApplication= (MyApplication) settingActivity.getApplicationContext();
        sPref=mApplication.getsPref();
    }

    /*
    * @方法 获得保存的notestyle
    * @参数 null
    * @返回值 int
    */
    public int getNoteStyle(){
        return sPref.getInt("note_style",1);
    }

    /*
    * @方法 是否第一次上锁
    * @参数 null
    * @返回值 boolean
    */
    public boolean isFirstLock(){
        return sPref.getBoolean("lock_first", true);
    }

    /*
    * @方法 获得密码
    * @参数 null
    * @返回值 String
    */
    public String getPassword(){
        return sPref.getString("password", "");
    }

    /*
    * @方法 获得密码
    * @参数 null
    * @返回值 String
    */
    public boolean isLocked(){
        return sPref.getBoolean("lock", false);
    }

    /*
    * @方法 判断是否为夜间模式
    * @参数 null
    * @返回值 boolean
    */
    public boolean isNight(){
        return sPref.getBoolean("night", false);
    }

    /*
    * @方法 填写flag到sPref
    * @参数 flag int
    * @返回值 null
    */
    public void setCheckStyle(int flag){
        sPref.edit().putInt("note_style", flag).commit();
    }

    /*
    * @方法 填写boolean到sPref
    * @参数 flag boolean
    * @返回值 null
    */
    public void setSomeThingInsPerf(String name,boolean isNight){
        sPref.edit().putBoolean(name,isNight).commit();
    }
    public void setSomeThingInsPerf(String name,String s){
        sPref.edit().putString(name,s).commit();
    }

    /*
    * @方法 获取主题
    * @参数 null
    * @返回值 boolean
    */
    public boolean getThemeNight() {
        return sPref.getBoolean("night",false);
    }

    /*
    * @方法 解锁逻辑
    * @参数 List<Integer> passList
    * @返回值 boolean
    */
    public boolean deblocking(List<Integer> passList){
        if (passList.size() < 4) {
            return false;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Integer i : passList) {
                sb.append(i);
            }
            setSomeThingInsPerf("password",sb.toString());
            setSomeThingInsPerf("lock_first",false);
            setSomeThingInsPerf("lock",true);
            return true;
        }
    }

    public void resetPassword(){
        if (!sPref.getBoolean("lock_first",true)){
            sPref.edit().putBoolean("lock_first",true).commit();
            sPref.edit().putString("password", "").commit();
            sPref.edit().putBoolean("lock",false).commit();
        }
    }





    public boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) mSettingActivity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            LogUtils.Log(service.service.getClassName().toString());
            if ("skkk.gogogo.dakainote.Service.CopyService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
