package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.Span.ClickableImageSpan;

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
        this(context, attrs, 0);

    }

    public NoteEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
    public void addImageSpan(String imagePath,int reqWidth) {
        //加入图片时候首先换行
        append("\n");
        //加入需要替换的独一无二的标识：图片绝对路径
        append(imagePath);
        Log.d("SKKK_____", getText().toString());
        //获取所有的text
        CharSequence text =getText();
        //创建Span..Builder
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        //设置需要被替换的标识字符串为imagePath
        String rexgString = imagePath;
        //正则表达式匹配标识
        Pattern pattern = Pattern.compile(rexgString);
        //正则表达式在所有text中匹配
        Matcher matcher = pattern.matcher(text);
        //对匹配到的结果进行遍历
        while (matcher.find()) {
            builder.setSpan(
                    new ClickableImageSpan(getContext(), CameraImageUtils.getPreciselyBitmap(imagePath, reqWidth)) {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "你点击了图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                    , matcher.start()
                    , matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(builder);
        append("\n");
    }

}
