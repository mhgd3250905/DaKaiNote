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
import android.widget.TextView;

import skkk.gogogo.dakainote.R;

/**
 * 主页横向RecyclerView的viewholder
 * @author Robin
 * time 2015-04-10 12:53:02
 *
 */
public class AuthorHolderViewHolder extends RecyclerViewHolderBase {
    public TextView tvItemAuthorContnet;
    public TextView tvItemAuthorTime;
    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public AuthorHolderViewHolder(View itemView) {
        super(itemView);
        tvItemAuthorContnet= (TextView) itemView.findViewById(R.id.tv_item_author_content);
        tvItemAuthorTime= (TextView) itemView.findViewById(R.id.tv_item_author_time);
    }
}
