package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
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
public class NoteEditView extends EditText{
    

    public NoteEditView(Context context) {
        this(context, null);
    }

    public NoteEditView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public NoteEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 图片
     */
    public void addImageSpan(SpannableString spanString) {
        append(spanString);
        append("\n");
    }


}
