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
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
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
    private int mPos;
    boolean isDel=false;
    private Handler handler;


    /*
    * @方法 1 构造方法
    *
    */
    public NoteScheduleListAdapter(Context context, List<ScheduleCache> mItemDataList,Handler handler) {
        super(mItemDataList);
        this.context = context;
        this.handler=handler;
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

                } else {
                    /* @描述 取消勾选 */
                    LogUtils.Log("第" + position + "个" + "item勾选改变" + isChecked);
                    holder.etSchedule.getPaint().setFlags(0);
                    mItemDataList.get(position).setScheduleChecked(false);

                }
            }
        });

        holder.etSchedule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Message msgText=new Message();
                msgText.what=30803;
                msgText.arg1=position;
                msgText.obj=s.toString();
                handler.sendMessage(msgText);
            }
        });

        /* @描述 设置ct的key监听事件 */
        holder.etSchedule.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                /* @描述 设置软键盘强制显示 */
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.etSchedule, InputMethodManager.SHOW_FORCED);
                switch (event.getAction()){
                    case KeyEvent.ACTION_DOWN:

                        if (keyCode==KeyEvent.KEYCODE_ENTER&&
                                position==mItemDataList.size()-1){
                            Message msg=new Message();
                            msg.arg1=position;
                            msg.what=30801;
                            handler.sendMessage(msg);
                            return true;
                        }else if (keyCode==KeyEvent.KEYCODE_DEL&&
                                TextUtils.isEmpty(holder.etSchedule.getText())&&
                                mItemDataList.size()!=1) {
                            Message msg=new Message();
                            msg.arg1 = position;
                            msg.what = 30802;
                            handler.sendMessage(msg);
                            return true;
                        }
                        break;
                    case KeyEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

        /* @描述 删除按钮的点击事件 */
        holder.ivSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDel = true;
                mItemDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mItemDataList.size());
            }
        });

        /* @描述 et的focus监听事件 */
        holder.etSchedule.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    isDel=false;

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

    /*
    * @方法 设置指定位置item的内容
    *
    */
    public void setItemDataListInfo(int position,String content){
        mItemDataList.get(position).setScheduleContent(content);
    }


}
