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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.ViewHolder.RecyclerViewHolderBase;

/**
 * 横向RecyclerView基类adapter
 * @author Robin
 * time 2015-04-10 12:33:43
 *
 * @param <ItemDataType> 数据实体类型
 */
public abstract class RecyclerViewBaseAdapter<ItemDataType> extends
        RecyclerView.Adapter<RecyclerViewHolderBase> {

    protected List<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

    public void setmItemDataList(List<ItemDataType> mItemDataList) {
        this.mItemDataList = mItemDataList;
    }

    public RecyclerViewBaseAdapter(List<ItemDataType> mItemDataList) {
        this.mItemDataList = mItemDataList;
    }

    /**
     * 动态增加一条数据
     * @param itemDataType 数据实体类对象
     */
    public void append(int position,ItemDataType itemDataType){
        if (itemDataType!=null) {
            mItemDataList.add(position,itemDataType);
            notifyDataSetChanged();
        }
    }


    /*
     * @desc 设置点击事件
     * @时间 2016/6/18 15:53
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    protected OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }




    /**
     * 动态增加一组数据集合
     * @param itemDataTypes 数据实体类集合
     */
    public void append(List<ItemDataType> itemDataTypes){
        if (itemDataTypes.size()>0) {
            for (ItemDataType itemDataType : itemDataTypes) {
                mItemDataList.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 替换全部数据
     * @param itemDataTypes 数据实体类集合
     */
    public void replace(List<ItemDataType> itemDataTypes){
        mItemDataList.clear();
        if (itemDataTypes.size()>0) {
            mItemDataList.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除一条数据集合
     * @param position
     */
    public void remove(int position){
        mItemDataList.remove(position);
        DataSupport.delete(NoteNew.class,position);
        notifyItemRemoved(position);
    }

    /**
     * 移除所有数据
     */
    public void removeAll(){
        mItemDataList.clear();
        notifyDataSetChanged();
    }

    /*
    * @方法 获取mItemDataList
    *
    */
    public List<ItemDataType> getItemList(){
        return mItemDataList;
    }

    /*
    * @方法 remove_pos
    *
    */
    public void removePosItem(int pos){
        mItemDataList.remove(pos);
    }



    /**
     * 更新pos数据
     */
    public void update(int pos){
        notifyItemChanged(pos);
    }


    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolderBase viewHolder, int i) {
        showData(viewHolder, i, mItemDataList);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(viewHolder.itemView, pos);
                    return false;
                }
            });
        }


    }

    @Override
    public RecyclerViewHolderBase onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=createView(viewGroup, i);
        RecyclerViewHolderBase holder = createViewHolder(view);
        return holder;
    }


    /**
     * 显示数据抽象函数
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i 位置
     * @param mItemDataList 数据集合
     */
    public abstract void showData(RecyclerViewHolderBase viewHolder, int i,List<ItemDataType> mItemDataList );
    /**
     * 加载item的view,直接返回加载的view即可
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param i
     * @return item 的 view
     */
    public abstract View createView(ViewGroup viewGroup, int i) ;
    /**
     * 加载一个ViewHolder,为RecyclerViewHolderBase子类,直接返回子类的对象即可
     * @param view item 的view
     * @return  RecyclerViewHolderBase 基类ViewHolder
     */
    public abstract RecyclerViewHolderBase  createViewHolder(View view) ;
}
