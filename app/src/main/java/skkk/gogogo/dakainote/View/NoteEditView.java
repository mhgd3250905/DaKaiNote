package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by admin on 2016/8/13.
 */
/*
* 
* 描    述：自定义view用来编辑或显示note信息
* 作    者：ksheng
* 时    间：2016/8/13$ 12:22$.
*/
public class NoteEditView extends EditText {


    private int x;
    private int y;
    private int line;
    private Layout layout;
    private int off;

    public NoteEditView(Context context) {
        this(context, null);
    }

    public NoteEditView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public NoteEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //设计触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当针对屏幕按下的时候
                x = (int) event.getX();
                y = (int) event.getY();
                x -= getTotalPaddingLeft();
                y -= getTotalPaddingTop();
                x += getScrollX();
                y += getScrollY();
                //获取布局文件
                layout = getLayout();
                //获取触摸点对应的行数
                line = layout.getLineForVertical(y);
                //获取触摸点对应的列数
                off = layout.getOffsetForHorizontal(line, x);
                //设置触摸点对应的文字光标
                Selection.setSelection(getText(), off);
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 图片
     */
    public void addImageSpan(SpannableString spanString) {
        append(spanString);
        append("\n");
    }


}
