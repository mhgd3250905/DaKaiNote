package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：自定义ImageView可以保存地址信息
* 作    者：ksheng
* 时    间：2016/8/27$ 21:31$.
*/
public class MyImageView extends ImageView {

    private String imagePath;
    private int imageId;




    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setBitmapFromPath(String imagePath) {
        this.imagePath = imagePath;
        setBitmapFromPath(imagePath);
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
