package skkk.gogogo.dakainote.MyUtils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

/**
 * Created by admin on 2016/9/27.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/9/27$ 23:22$.
*/
public class EditUtils {

    /**
     * 超链接
     */
    public static void addUrlSpan(EditText tv) {
        SpannableString spanString = new SpannableString("超链接");
        URLSpan span = new URLSpan("tel:0123456789");
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 文字背景颜色
     */
    public static void addBackColorSpan(EditText tv) {
        SpannableString spanString = new SpannableString("颜色2");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 文字颜色
     */
    public static void addForeColorSpan(EditText tv) {
        SpannableString spanString = new SpannableString("颜色1");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 字体大小
     */
    public static void addFontSpan(EditText tv) {
        SpannableString spanString = new SpannableString("36号字体");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(36);
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 粗体，斜体
     */
    public static void addStyleSpan(EditText tv) {
        SpannableString spanString = new SpannableString("BIBI");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }


    /**
     * 删除线
     */
    public static void addStrikeSpan(EditText tv) {
        SpannableString spanString = new SpannableString("删除线");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }

    /**
     * 下划线
     */
    public static void addUnderLineSpan(EditText tv) {
        SpannableString spanString = new SpannableString("下划线");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }



//    /**
//     * 图片
//     */
//    private void addImageSpan(EditText tv) {
//        SpannableString spanString = new SpannableString(" ");
//        Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
//        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv.append(spanString);
//    }
}
