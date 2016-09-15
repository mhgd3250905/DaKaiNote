package skkk.gogogo.dakainote.ViewHolder;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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
    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public NoteScheduleItemViewHolder(View itemView) {
        super(itemView);
        cbSchedule= (CheckBox) itemView.findViewById(R.id.cb_item_schedule);
        etSchedule= (EditText) itemView.findViewById(R.id.et_item_schedule);
        initEvent();
    }

    /*
    * @方法 schedule的点击联动事件
    *       当checkbox被勾选的时候：editText会被划去
    *
    */
    private void initEvent() {
        cbSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etSchedule.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }else {
                    etSchedule.getPaint().setFlags(0);
                }
            }
        });
    }

}