package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;

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

    private Paint mPaint=new Paint();
    private int width;
    private int height;
    private Bitmap imageBitmap;
    private int textHeight;
    private Rect bounds = new Rect();;
    private SpannableString mSpan;
    private int lineIndex;

    public NoteEditView(Context context) {
        this(context, null);
    }

    public NoteEditView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public NoteEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//        invalidate();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void insertDrawable(String imagePath) {

        mSpan = new SpannableString("1");

        imageBitmap = CameraImageUtils.decodeSampleBitmapFromResource(
                 getContext(), imagePath, width * 2 / 3, height * 2 / 3);

        if(imageBitmap == null)
            return;

        //将最后一位1替换为图片

        //Log.d("SKKK_____",mSpan1.toString());

        mSpan.setSpan(new ImageSpan(imageBitmap), 0, mSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Log.d("SKKK_____", mSpan1.toString());

        int mStart=getSelectionStart();

        Editable et = getText();
        et.insert(mStart, mSpan);

        Log.d("SKKK_____", et.toString());

        setText(et);

        Log.d("SKKK_____", et.toString());

        setSelection(mStart + mSpan.length());

    }

    /**
     * 图片
     */
    public void addImageSpan(SpannableString spanString) {
        append(spanString);
        append("\n");
    }

    /*
     * @方法 绘制view方法
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("SKKK_____","onDraw");
    }

    private int calculateTextHeight(String text) {
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }


}
