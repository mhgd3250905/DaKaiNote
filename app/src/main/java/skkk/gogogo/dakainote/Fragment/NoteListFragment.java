package skkk.gogogo.dakainote.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import skkk.gogogo.dakainote.Interface.NoteListInterface;
import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.SpacesItemDecoration;
import skkk.gogogo.dakainote.Presenter.NoteListPresenter;
import skkk.gogogo.dakainote.R;

/*
* 
* 描    述：笔记列表界面
* 作    者：ksheng
* 时    间：
*/
public class NoteListFragment extends Fragment implements NoteListInterface{
    protected View view;
    protected NoteListAdapter adapter;
    protected RecyclerView rvNoteList;
    protected LinearLayout llBlankTip;
    protected UIHomeActivity mUiHomeActivity;
    private NoteListPresenter mNoteListPresenter=new NoteListPresenter(this);
    private SpacesItemDecoration mDecoration;
    private int layoutFlag=1;//0就是瀑布流 1就是list布局 2就是标准卡片布局



    /* @描述 构造方法 */
    @SuppressLint("ValidFragment")
    public NoteListFragment(List<Note> myNotes,int layoutFlag) {
        this.layoutFlag = layoutFlag;
        mNoteListPresenter.setMyNotes(myNotes);
    }

    public NoteListFragment() {
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
        adapter = new NoteListAdapter(getContext(),mNoteListPresenter.getMyNotes());
        /* @描述 布局 */
        mNoteListPresenter.setLayoutFlag();
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
                //如果是正常显示状态那么点击fab进入note编辑界面
                if (!adapter.getShowCheckbox()) {
                    /* @描述 打开Note编辑界面 */
                    mNoteListPresenter.startNote(position);
                } else {
                    //如果是删除编辑状态那么点击为勾选
                    /* @描述 选中的Notes */
                    mNoteListPresenter.selectNotes(position);
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



    @Override
    public void onResume() {
        super.onResume();

        updateAll(mNoteListPresenter.reGetMyNotes());
        showBlankTip();

    }

    /*
    * @方法 判断recyclerView中每一个View是否需要删除
    *
    */
    public void deleteSelectedItem() {
        mNoteListPresenter.deleteNotes();
        showBlankTip();
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
        mNoteListPresenter.updateNotes(noteList);
    }

    /*
    * @方法 显示空白提示背景
    * @描述 显示空白提示背景
    * @参数 null
    * @返回值 null
    */
    public void showBlankTip() {
        if (mNoteListPresenter.isShowBlankTip()) {
            llBlankTip.setVisibility(View.VISIBLE);
        } else {
            llBlankTip.setVisibility(View.GONE);
        }
    }



    /*
   * @方法 设置布局
   * @描述 通过修改布局管理器来修改布局样式
   * @参数 布局参数 int
   * @返回值 null
   */
    public void setLayoutFlag(int layoutFlag) {
        this.layoutFlag = layoutFlag;
        mNoteListPresenter.setLayoutFlag();
    }

    /*
    * @方法 获取数据适配器
    * @参数
    * @返回值 NoteListAdapter
    */
    @Override
    public NoteListAdapter getAdapter() {
        return adapter;
    }


    /*
    * @方法 获取布局flag
    * @参数
    * @返回值 layoutFlag
    */
    @Override
    public int getLayoutFlag() {
        return layoutFlag;
    }

    /*
    * @方法 获取recyclerView
    * @参数 null
    * @返回值 RecyclerView
    */
    @Override
    public RecyclerView getRecyclerView() {
        return rvNoteList;
    };



}


