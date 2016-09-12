package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by admin on 2016/8/30.
 */
/*
* 
* 描    述：播放音频按钮
* 作    者：ksheng
* 时    间：2016/8/30$ 21:22$.
*/
public class AudioButton extends ImageView {

    private String voicePath;//播放音频之路径

    public AudioButton(Context context) {
        this(context, null);
    }

    public AudioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /*
    * @方法 设置音频路径
    *
    */
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }


}
