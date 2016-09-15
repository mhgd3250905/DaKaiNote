package skkk.gogogo.dakainote.Adapter;

/**
 * Created by admin on 2016/8/3.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/

import android.content.Context;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.ScheduleCache;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.ViewHolder.NoteScheduleItemViewHolder;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 主页横向RecyclerView的适配器
 * @author Robin
 * time 2015-04-10 12:50:31
 *
 */
public class NoteScheduleListAdapter extends RecyclerViewBaseAdapter<ScheduleCache> {
    private Context context;
    private LayoutInflater inflater;
    private long noteKey;

    /*
    * @方法 1 构造方法
    *
    */
    public NoteScheduleListAdapter(Context context, List<ScheduleCache> mItemDataList,long noteKey) {
        super(mItemDataList);
        this.noteKey=noteKey;
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    /*
    * @方法 2 显示data
    *
    */
    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int position,List<ScheduleCache> mItemDataList) {
        //向下转型为子类
        NoteScheduleItemViewHolder holder= (NoteScheduleItemViewHolder) viewHolder;
        holder.cbSchedule.setChecked(mItemDataList.get(position).isScheduleChecked());
        if (mItemDataList.get(position).isScheduleChecked()){
            holder.etSchedule.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.etSchedule.setText(mItemDataList.get(position).getScheduleContent());
        holder.etSchedule.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){
                    ScheduleCache scheduleCache=new ScheduleCache();
                    scheduleCache.setNoteKey(noteKey);
                    scheduleCache.setScheduleChecked(false);
                    scheduleCache.setScheduleContent("");
                    scheduleCache.save();
                    List<ScheduleCache> item = SQLUtils.getScheduleInItem(noteKey);
                    setmItemDataList(item);
                    notifyDataSetChanged();
                }
                return false;
            }
        });
        holder.etSchedule.setFocusable(true);
        holder.etSchedule.setFocusableInTouchMode(true);
        holder.etSchedule.requestFocus();
        holder.etSchedule.requestFocusFromTouch();
    }


    /*
    * @方法 3 创建item布局
    *
    */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_schedule_design, viewGroup,false);
        return view;
    }


    /*
    * @方法 4 返回ViewHolder对象
    *
    */
    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new NoteScheduleItemViewHolder(view);
    }



}
