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

import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.ViewHolder.NoteVoiceViewHolder;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 主页横向RecyclerView的适配器
 * @author Robin
 * time 2015-04-10 12:50:31
 *
 */
public class NoteVoiceListAdapter extends RecyclerViewBaseAdapter<VoiceCache> {
    private Context context;
    private LayoutInflater inflater;
    private Boolean showCheckbox=false;

    /*
    * @方法 1 构造方法
    *
    */
    public NoteVoiceListAdapter(Context context, List<VoiceCache> mItemDataList) {
        super(mItemDataList);
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    /*
    * @方法 2 显示data
    *
    */
    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int position, List<VoiceCache> mItemDataList) {
        //向下转型为子类
        NoteVoiceViewHolder holder= (NoteVoiceViewHolder) viewHolder;
        holder.abNoteDetailItem.setVoicePath(mItemDataList.get(position).getVoicePath());
    }


    /*
    * @方法 3 创建item布局
    *
    */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_note_voice_list, viewGroup,false);
        return view;
    }


    /*
    * @方法 4 返回ViewHolder对象
    *
    */
    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new NoteVoiceViewHolder(view);
    }



}
