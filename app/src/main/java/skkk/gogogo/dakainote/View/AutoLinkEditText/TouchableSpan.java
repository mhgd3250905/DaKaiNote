package skkk.gogogo.dakainote.View.AutoLinkEditText;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by admin on 2016/10/9.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/9$ 12:23$.
*/
abstract class TouchableSpan extends ClickableSpan {

    private boolean isPressed;
    private int normalTextColor;
    private int pressedTextColor;

    TouchableSpan(int normalTextColor, int pressedTextColor) {
        this.normalTextColor = normalTextColor;
        this.pressedTextColor = pressedTextColor;
    }

    void setPressed(boolean isSelected) {
        isPressed = isSelected;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setColor(isPressed ? pressedTextColor : normalTextColor);
        textPaint.bgColor = Color.TRANSPARENT;
        textPaint.setUnderlineText(false);
    }
}
