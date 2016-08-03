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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.ViewHolder.NoteListViewHolder;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 主页横向RecyclerView的适配器
 * @author Robin
 * time 2015-04-10 12:50:31
 *
 */
public class NoteListAdapter extends
        RecyclerViewBaseAdapter<String> {
    private Context context;
    private LayoutInflater inflater;
    private int[] mColors;

    public NoteListAdapter(Context context, ArrayList<String> mItemDataList) {
        super(mItemDataList);
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int i, List<String> mItemDataList) {
        //向下转型为子类
       NoteListViewHolder holder= (NoteListViewHolder) viewHolder;
        holder.tvTest.setText(mItemDataList.get(i));

        
        //动态设置高度
        int newHeight= (int) (Math.random()*500+100);
        ViewGroup.LayoutParams hereLayoutParams = holder.llTest.getLayoutParams();
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(hereLayoutParams.width,newHeight);
        holder.llTest.setLayoutParams(layoutParams);
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_note_card, viewGroup, false);
        return view;
    }

    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new NoteListViewHolder(view);
    }

}
