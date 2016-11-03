package skkk.gogogo.dakainote.MyUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

import skkk.gogogo.dakainote.R;

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
    public static void addUrlSpan(EditText tv,String name,long phone) {
        SpannableString spanString = new SpannableString(name);
        URLSpan span = new URLSpan("tel:"+phone);
        spanString.setSpan(span, 0,name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
    public static void addForeColorSpan(Context context,EditText tv,String content) {
        SpannableString spanString = new SpannableString(content);
        ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent));
        spanString.setSpan(span, 0,content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
     * 粗体
     */
    public static void addStyleBoldSpan(EditText tv) {
        SpannableString spanString = new SpannableString("[sk_bold_sk]");
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        spanString.setSpan(span, 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }

    /**
     * 粗体
     */
    public static void addStyleItalicSpan(EditText tv) {
        SpannableString spanString = new SpannableString("[sk_italic_sk]");
        StyleSpan span = new StyleSpan(Typeface.ITALIC);
        spanString.setSpan(span, 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    /**
     * 图片
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void addImageSpan(Context context, EditText tv) {
        SpannableString spanString = new SpannableString("[sk_line_sk]");
        Drawable d = context.getDrawable(R.drawable.line);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0,spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(spanString);
    }
}
