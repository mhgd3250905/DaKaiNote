package skkk.gogogo.dakainote.MyUtils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by admin on 2016/10/29.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/29$ 22:04$.
*/
public class KeyBoardUtils {
    public static void hidekeyBoard(Context context, EditText et){
        InputMethodManager mImm;
         /* @描述 只要录音按键出现那么就强制关闭软键盘 */
        mImm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mImm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
    }
}
