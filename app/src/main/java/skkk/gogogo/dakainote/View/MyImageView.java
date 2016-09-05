package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

import skkk.gogogo.dakainote.MyUtils.LogUtils;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：自定义ImageView可以保存地址信息
* 作    者：ksheng
* 时    间：2016/8/27$ 21:31$.
*/
public class MyImageView extends SimpleDraweeView {

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
        Uri uri = Uri.parse("file://" + imagePath);
        LogUtils.Log("setBitmaoFromPath的路径为 file://" + imagePath);
        setImageURI(uri);
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
