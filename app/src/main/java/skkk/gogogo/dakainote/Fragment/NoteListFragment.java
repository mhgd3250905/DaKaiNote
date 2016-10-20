package skkk.gogogo.dakainote.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.SaveNoteActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.MyUtils.SpacesItemDecoration;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.MyNoteView;

/*
* 
* 描    述：笔记列表界面
* 作    者：ksheng
* 时    间：
*/
public class NoteListFragment extends Fragment {
    protected View view;
    protected List<Note> myNotes;
    protected NoteListAdapter adapter;
    protected Note noteShow;
    protected RecyclerView rvNoteList;
    protected LinearLayout llBlankTip;
    protected UIHomeActivity mUiHomeActivity;
    private LinearLayoutManager mLinearLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    private SpacesItemDecoration mDecoration;
    private int layoutFlag=1;//0就是瀑布流 1就是list布局 2就是标准卡片布局

    /*
    * @方法 设置布局
    * @描述 通过修改布局管理器来修改布局样式
    * @参数 布局参数 int
    * @返回值 null
    */
    public void setLayoutFlag(int layoutFlag) {
        this.layoutFlag = layoutFlag;
        switch (layoutFlag){
            case 0:
                /* @描述 设置布局管理器 */
                mLinearLayoutManager = new LinearLayoutManager(getContext());
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                 /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mLinearLayoutManager);
                break;
            case 1:
                /* @描述 设置布局管理器 */
                mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mStaggeredGridLayoutManager);
                break;
            case 2:
                 /* @描述 设置布局管理器 */
                mGridLayoutManager=new GridLayoutManager(getContext(),2);
                 /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mGridLayoutManager);
                break;
        }
    }
    
    /* @描述 构造方法 */
    public NoteListFragment(List<Note> myNotes,int layoutFlag) {
        this.layoutFlag = layoutFlag;
        this.myNotes=myNotes;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initUI(view);
        initEvent();
        return view;
    }

    
    /*
     * @desc 这里需要实现recycler的瀑布流布局
     * @时间 2016/8/1 22:01
     */
    private void initUI(View view) {
        /* @描述 获取RecyclerView实例 */
        rvNoteList = (RecyclerView) view.findViewById(R.id.rv_note_list);
        /* @描述 获取提示布局 */
        llBlankTip = (LinearLayout) view.findViewById(R.id.ll_blank_tip);
        /* @描述 设置Adapter */
        adapter = new NoteListAdapter(getContext(), myNotes);
        switch (layoutFlag){
            case 0:
                /* @描述 设置布局管理器 */
                mLinearLayoutManager = new LinearLayoutManager(getContext());
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                 /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mLinearLayoutManager);
                break;
            case 1:
                /* @描述 设置布局管理器 */
                mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mStaggeredGridLayoutManager);
                break;
            case 2:
                 /* @描述 设置布局管理器 */
                mGridLayoutManager=new GridLayoutManager(getContext(),2);
                 /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mGridLayoutManager);
                break;
        }

        /* @描述 设置间距 */
        mDecoration = new SpacesItemDecoration(0);
        /* @描述 添加间距 */
        rvNoteList.addItemDecoration(mDecoration);
        /* @描述 设置基本动画 */
        rvNoteList.setItemAnimator(new DefaultItemAnimator());
        /* @描述 rvNoteList */
        rvNoteList.setAdapter(adapter);
        rvNoteList.setHasFixedSize(true);

    }

    /* @描述 设置所有的触发事件 */
    private void initEvent() {
        /* @描述 item单击事件 */
        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                /* @描述 如果是正常显示状态那么点击fab进入note编辑界面 */
                if (!adapter.getShowCheckbox()) {
                    //从数据库中查询第position行数据
                    noteShow = myNotes.get(position);
                    Log.d("SKKKKKKKK---------", noteShow.toString());
                    //将查询之note类传给NOTE展示页面
                    Intent intent = new Intent();
                    intent.putExtra("note", noteShow);
                    intent.putExtra("pos", position);
                    intent.setClass(getContext(), SaveNoteActivity.class);


                    LogUtils.Log("这里是点击事件定位的note，id为" + noteShow.getId());
                    LogUtils.Log("note: " + noteShow.toString());
                    getActivity().startActivity(intent);

                } else {
                    /* @描述 如果是编辑状态那么点击fab执行删除操作 */
                    View position1 = null;
                    switch (layoutFlag) {
                        case 0:
                            position1 = mLinearLayoutManager.findViewByPosition(position);
                            break;
                        case 1:
                            position1 = mStaggeredGridLayoutManager.findViewByPosition(position);
                            break;
                        case 2:
                            position1 = mGridLayoutManager.findViewByPosition(position);
                            break;
                    }
                    MyNoteView myNoteViewPos = (MyNoteView) ((CardView) position1).getChildAt(0);
                    if (myNoteViewPos.getCheckboxStatus()) {
                        myNoteViewPos.setCheckboxStatus(false);
                    } else {
                        myNoteViewPos.setCheckboxStatus(true);
                    }
                }
            }

            /* @描述 item长按事件 */
            @Override
            public void onItemLongClick(View view, final int position) {
                adapter.showCheckBox(true);
                //修改主页面的fab样式
                mUiHomeActivity = (UIHomeActivity) getActivity();
                mUiHomeActivity.changeFabSrc(2);
            }
        });
    }

    /*
    * @方法 判断recyclerView中每一个View是否需要删除
    *
    */
    public void deleteSelectedItem() {
        for (int i = myNotes.size() - 1; i >= 0; i--) {
            View positionView=null;
            switch (layoutFlag){
                case 0:
                    positionView = mLinearLayoutManager.findViewByPosition(i);
                    break;
                case 1:
                    positionView = mStaggeredGridLayoutManager.findViewByPosition(i);
                    break;
                case 2:
                    positionView=mGridLayoutManager.findViewByPosition(i);
                    break;
            }
            if (positionView instanceof CardView) {
                DataSupport.delete(Note.class, myNotes.get(i).getId());
                MyNoteView myNoteViewPos = (MyNoteView) ((CardView) positionView).getChildAt(0);
                if (myNoteViewPos.isDeleteChecked()) {
                    deleteItemPos(i);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    /*
    * @方法 改变所有的item的checkbox状态
    *
    */
    public void hideCheckBox() {
        adapter.showCheckBox(false);
    }


    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<Note> noteList) {
        myNotes=noteList;
        adapter.setmItemDataList(myNotes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        myNotes = SQLUtils.getNoteList();
        updateAll(myNotes);
        showBlankTip();
    }

    /*
    * @方法 显示空白提示背景
    * @描述 显示空白提示背景
    * @参数 null
    * @返回值 null
    */
    public void showBlankTip() {
        myNotes = SQLUtils.getNoteList();
        if (myNotes.size() == 0) {
            llBlankTip.setVisibility(View.VISIBLE);
        } else {
            llBlankTip.setVisibility(View.GONE);
        }
    }

//    public void smoothScrollToTop() {
//        rvNoteList.smoothScrollToPosition(0);
//    }

    /* @描述 删除指定pos的item */
    public void deleteItemPos(int pos) {
        //首先获取这个位置的note
        Note noteDelete = myNotes.get(pos);
        //然后remove这个position的内容
        adapter.remove(pos);
        Log.d("SKKK_____", noteDelete.toString());
        noteDelete.delete();
        showBlankTip();
    }


}


