package skkk.gogogo.dakainote.ViewHolder;

/**
 * Created by admin on 2016/8/3.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/

import android.view.View;
import android.widget.ImageView;

import skkk.gogogo.dakainote.R;

/**
 * 主页横向RecyclerView的viewholder
 * @author Robin
 * time 2015-04-10 12:53:02
 *
 */
public class ImageShowViewHolder extends RecyclerViewHolderBase {

    public ImageView ivImageShow;
    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public ImageShowViewHolder(View itemView) {
        super(itemView);
        ivImageShow= (ImageView) itemView.findViewById(R.id.iv_image_show);
    }

}
