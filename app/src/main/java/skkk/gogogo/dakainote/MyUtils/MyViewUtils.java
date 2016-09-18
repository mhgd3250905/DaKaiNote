package skkk.gogogo.dakainote.MyUtils;

import android.view.View;

/**
 * Created by admin on 2016/9/18.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/9/18$ 20:57$.
*/
public class MyViewUtils {

    /*
    * @方法 view获取焦点
    *
    */
    public static void getFoucs(View view){
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.requestFocusFromTouch();
    }
}
