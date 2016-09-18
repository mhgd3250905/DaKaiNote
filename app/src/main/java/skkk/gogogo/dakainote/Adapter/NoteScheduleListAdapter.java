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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.ScheduleCache;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.ViewHolder.NoteScheduleItemViewHolder;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 主页横向RecyclerView的适配器
 *
 * @author Robin
 *         time 2015-04-10 12:50:31
 */
public class NoteScheduleListAdapter extends RecyclerViewBaseAdapter<ScheduleCache> {
    private Context context;
    private LayoutInflater inflater;
    private long noteKey;
    private int mPos;


    /*
    * @方法 1 构造方法
    *
    */
    public NoteScheduleListAdapter(Context context, List<ScheduleCache> mItemDataList, long noteKey) {
        super(mItemDataList);
        this.noteKey = noteKey;
        this.context = context;
        /* @描述 设置对应 */
        inflater = LayoutInflater.from(context);
    }

    /*
    * @方法 2 显示data
    *
    */
    @Override
    public void showData(RecyclerViewHolderBase viewHolder, final int position, final List<ScheduleCache> mItemDataList) {
        //向下转型为子类
        final NoteScheduleItemViewHolder holder = (NoteScheduleItemViewHolder) viewHolder;
        holder.cbSchedule.setChecked(mItemDataList.get(position).isScheduleChecked());
         /* @描述 设置cb的勾选状态 */
        if (mItemDataList.get(position).isScheduleChecked()) {
            holder.etSchedule.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        /* @描述 设置et的文本内容 */
        holder.etSchedule.setText(mItemDataList.get(position).getScheduleContent());
        /* @描述 设置焦点 */
        if (mPos==mItemDataList.size() - 1) {
            holder.etSchedule.setFocusable(true);
            holder.etSchedule.setFocusableInTouchMode(true);
            holder.etSchedule.requestFocus();
            holder.etSchedule.requestFocusFromTouch();
        }

        /* @描述 checkbox的勾选事件 */
        holder.cbSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* @描述 勾选 */
                    LogUtils.Log("第" + position + "个" + "item勾选改变" + isChecked);
                    holder.etSchedule.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mItemDataList.get(position).setScheduleChecked(true);
                    setmItemDataList(mItemDataList);
                } else {
                    /* @描述 取消勾选 */
                    LogUtils.Log("第" + position + "个" + "item勾选改变" + isChecked);
                    holder.etSchedule.getPaint().setFlags(0);
                    mItemDataList.get(position).setScheduleChecked(false);
                    setmItemDataList(mItemDataList);
                }
            }
        });

        /* @描述 设置ct的key监听事件 */
        holder.etSchedule.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mPos = position;
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && mPos == mItemDataList.size() - 1) {

                        /* @描述 新建一个空的schedule 然后插入到list中 */
                        ScheduleCache scheduleCache = new ScheduleCache();
                        mItemDataList.add(scheduleCache);
                        setmItemDataList(mItemDataList);
                        notifyItemInserted(mItemDataList.size());

                        return true;

                    } else if (TextUtils.isEmpty(holder.etSchedule.getText().toString()) &&
                            keyCode == KeyEvent.KEYCODE_DEL) {
                        mItemDataList.remove(position);
                        notifyItemRemoved(position);

                        return true;

                    }
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                }
                return false;
            }
        });


        holder.ivSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemDataList.remove(position);
                notifyItemRemoved(position);

            }
        });


        holder.etSchedule.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    ScaleAnimation scaleAnim=new ScaleAnimation(0f,1f,0f,1f,
                            Animation.RELATIVE_TO_SELF,0.5f,
                            Animation.RELATIVE_TO_SELF,0.5f);
                    scaleAnim.setDuration(500);
                    scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            holder.ivSchedule.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.ivSchedule.startAnimation(scaleAnim);
                }else {

                    if (mItemDataList.size()-1 >= position) {
                    /* @描述 先把上一个在list中保存 */
                        mItemDataList.get(position).setScheduleChecked(holder.cbSchedule.isChecked());
                        mItemDataList.get(position).setScheduleContent(holder.etSchedule.getText().toString());
                        setmItemDataList(mItemDataList);
                    }


                    ScaleAnimation scaleAnim=new ScaleAnimation(1f,0f,1f,0f,
                            Animation.RELATIVE_TO_SELF,0.5f,
                            Animation.RELATIVE_TO_SELF,0.5f);
                    scaleAnim.setDuration(500);
                    scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.ivSchedule.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.ivSchedule.startAnimation(scaleAnim);
                }
            }
        });

    }

    /*
    * @方法 3 创建item布局
    *
    */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_schedule_design, viewGroup, false);
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
