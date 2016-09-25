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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.ViewHolder.NoteListViewHolder;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 主页横向RecyclerView的适配器
 * @author Robin
 * time 2015-04-10 12:50:31
 *
 */
public class NoteListAdapter extends RecyclerViewBaseAdapter<NoteNew> {
    private Context context;
    private LayoutInflater inflater;
    private Boolean showCheckbox=false;

    /*
    * @方法 1 构造方法
    *
    */
    public NoteListAdapter(Context context, List<NoteNew> mItemDataList) {
        super(mItemDataList);
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    /*
    * @方法 2 显示data
    *
    */
    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int position, List<NoteNew> mItemDataList) {
        //向下转型为子类
        NoteListViewHolder holder= (NoteListViewHolder) viewHolder;
        holder.myNoteView.setViewTitle(mItemDataList.get(position).getTitle());
        try {
            holder.myNoteView.setViewContent(mItemDataList.get(position).getContent());
        }catch (Exception e){
            holder.myNoteView.setViewContent("");
        }
        holder.myNoteView.setViewTitleTime(mItemDataList.get(position).getTime());

        /* @描述 是否显示下方的标签 */
        holder.myNoteView.setCheckBoxShow(showCheckbox);
        if (mItemDataList.get(position).isScheduleIsExist()
                ||mItemDataList.get(position).isImageIsExist()
                ||mItemDataList.get(position).isPinIsExist()
                ||mItemDataList.get(position).isVoiceExist()){
            holder.myNoteView.setImageFlagShow(true);
            holder.myNoteView.setImageIsExist(mItemDataList.get(position).isImageIsExist());
            holder.myNoteView.setPinIsExist(mItemDataList.get(position).isPinIsExist());
            holder.myNoteView.setVoiceIsExist(mItemDataList.get(position).isVoiceExist());
        }else {
            holder.myNoteView.setImageFlagShow(false);
        }
    }


    /*
    * @方法 3 创建item布局
    *
    */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_note_card, viewGroup,false);
        return view;
    }


    /*
    * @方法 4 返回ViewHolder对象
    *
    */
    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new NoteListViewHolder(view);
    }

    /*
    * @方法 修改showChecked flag
    *       显示或隐藏所有的item checkbox
    *
    */
    public void showCheckBox(boolean show){
        this.showCheckbox=show;
        /* @描述 重置checkbox的勾选状态 */
        notifyDataSetChanged();
    }

    /*
    * @方法 获取adapter item中checkbox的显示状态
    *
    */
    public Boolean getShowCheckbox() {
        return showCheckbox;
    }
}
