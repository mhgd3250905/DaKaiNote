package skkk.gogogo.dakainote.Presenter;

import android.content.SharedPreferences;

import skkk.gogogo.dakainote.Activity.AuthorActivity.AuthorActivity;
import skkk.gogogo.dakainote.Application.MyApplication;

/**
 * Created by admin on 2016/10/31.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/31$ 21:11$.
*/
public class AuthorPresenter {
    private AuthorActivity authorActivity;
    private MyApplication myApplication;
    private SharedPreferences sPref;

    public AuthorPresenter(AuthorActivity authorActivity) {
        this.authorActivity = authorActivity;
        myApplication= (MyApplication) authorActivity.getApplicationContext();
        sPref=myApplication.getsPref();
    }

    /*
    * @方法 获取主题
    * @参数 null
    * @返回值 boolean
    */
    public boolean getThemeNight() {
        return sPref.getBoolean("night",false);
    }
}
