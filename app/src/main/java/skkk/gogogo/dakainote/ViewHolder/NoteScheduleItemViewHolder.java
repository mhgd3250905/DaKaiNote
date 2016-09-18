package skkk.gogogo.dakainote.ViewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/9/15.
 */
/*
* 
* 描    述：note中schedule的item布局
* 作    者：ksheng
* 时    间：2016/9/15$ 21:27$.
*/
public class NoteScheduleItemViewHolder  extends RecyclerViewHolderBase {

    public CheckBox cbSchedule;
    public EditText etSchedule;
    public ImageView ivSchedule;
    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public NoteScheduleItemViewHolder(View itemView) {
        super(itemView);
        cbSchedule= (CheckBox) itemView.findViewById(R.id.cb_item_schedule);
        etSchedule= (EditText) itemView.findViewById(R.id.et_item_schedule);
        ivSchedule= (ImageView) itemView.findViewById(R.id.iv_item_schedule);
    }
}