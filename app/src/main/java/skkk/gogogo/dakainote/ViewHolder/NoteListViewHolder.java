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
import android.widget.LinearLayout;
import android.widget.TextView;

import skkk.gogogo.dakainote.R;

/**
 * 主页横向RecyclerView的viewholder
 * @author Robin
 * time 2015-04-10 12:53:02
 *
 */
public class NoteListViewHolder extends RecyclerViewHolderBase {

    public TextView tvTest;
    public LinearLayout llTest;

    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public NoteListViewHolder(View itemView) {
        super(itemView);
        tvTest= (TextView) itemView.findViewById(R.id.tv_test);
        llTest= (LinearLayout) itemView.findViewById(R.id.ll_test);

    }

}
