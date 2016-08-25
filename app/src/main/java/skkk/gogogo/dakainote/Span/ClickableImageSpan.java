package skkk.gogogo.dakainote.Span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;


/*
* 
* 描    述：拥有onClick()方法的iamgeSpan
* 作    者：ksheng
* 时    间：2016/8/25$ 20:24$.
*/
public abstract class ClickableImageSpan extends ImageSpan {
    public ClickableImageSpan(Drawable b) {
        super(b);
    }

    public ClickableImageSpan(Context context, Bitmap b) {
        super(context, b);
    }
    public abstract void onClick(View view);
}
