package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/9/13.
 */
/*
* 
* 描    述：显示在最下方的菜单栏
* 作    者：ksheng
* 时    间：2016/9/13$ 22:19$.
*/
public class BottomMenuView extends ViewGroup{

    public BottomMenuView(Context context) {
        this(context,null);
    }

    public BottomMenuView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
