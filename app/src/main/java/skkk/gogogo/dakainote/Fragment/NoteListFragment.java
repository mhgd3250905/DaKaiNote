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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.ArcNewNoteActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.NoteNew;
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
    protected List<NoteNew> myNotes;
    protected NoteListAdapter adapter;
    protected NoteNew noteShow;
    protected RecyclerView rvNoteList;
    protected LinearLayout llBlankTip;
    protected UIHomeActivity mUiHomeActivity;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private SpacesItemDecoration mDecoration;
    private static int NOTE_STYLE_LINEAR = 1;
    private static int NOTE_STYLE_CARD = 2;
    private int noteStyle = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_list, container, false);

        beforeStart();
        initUI(view);
        initEvent();
        return view;
    }

    /*
    * @方法 从数据库中获取数据
    *
    */
    private void beforeStart() {
        myNotes = new ArrayList<NoteNew>();
        myNotes = SQLUtils.getNoteList();
        mUiHomeActivity = (UIHomeActivity) getActivity();
        //List<ContentText> contentTextList = myNotes.get(0).getContentTextList();
    }


    /*
        * @desc 这里需要实现recycler的瀑布流布局
        * @时间 2016/8/1 22:01
        */
    private void initUI(View view) {
        //获取RecyclerView实例
        rvNoteList = (RecyclerView) view.findViewById(R.id.rv_note_list);
        //获取提示布局
        llBlankTip = (LinearLayout) view.findViewById(R.id.ll_blank_tip);

        //设置Adapter
        adapter = new NoteListAdapter(getContext(), myNotes);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mGridLayoutManager = new GridLayoutManager(getContext(), 2);

        //设置间距
        mDecoration = new SpacesItemDecoration(5);
        //添加间距
        rvNoteList.addItemDecoration(mDecoration);
        //添加布局
        if (noteStyle == NOTE_STYLE_CARD) {
            rvNoteList.setLayoutManager(mGridLayoutManager);
        } else if (noteStyle == NOTE_STYLE_LINEAR) {
            rvNoteList.setLayoutManager(mLayoutManager);
        }
        //设置基本动画
        rvNoteList.setItemAnimator(new DefaultItemAnimator());
        //rvNoteList
        rvNoteList.setAdapter(adapter);
        rvNoteList.setHasFixedSize(true);
        /*
        * @方法 item单击事件
        *
        */
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
                    intent.setClass(getContext(), ArcNewNoteActivity.class);

                    LogUtils.Log("这里是点击事件定位的note，id为" + noteShow.getId());
                    LogUtils.Log("note: " + noteShow.toString());
                    getActivity().startActivity(intent);
                } else {
                    /* @描述 如果是编辑状态那么点击fab执行删除操作 */
                    View position1 = mGridLayoutManager.findViewByPosition(position);
                    MyNoteView myNoteViewPos = (MyNoteView) ((CardView) position1).getChildAt(0);
                    if (myNoteViewPos.getCheckboxStatus()) {
                        myNoteViewPos.setCheckboxStatus(false);
                    } else {
                        myNoteViewPos.setCheckboxStatus(true);
                    }
                }
            }

            /*
            * @方法 item长按事件
            *
            */
            @Override
            public void onItemLongClick(View view, final int position) {
                adapter.showCheckBox(true);
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
            View positionView = mGridLayoutManager.findViewByPosition(i);
            if (positionView instanceof CardView) {
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
    * @方法 设置所有的触发事件
    *
    */
    private void initEvent() {
    }


    /*
        * @方法 更新指定位置list
        *
        */
    public void updatePos(int position, NoteNew note) {
        adapter.append(position, note);
        Log.d("SKKK_____", "updateOK here is fragment");
    }

    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<NoteNew> noteList) {
        adapter.setmItemDataList(noteList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        reGetNoteList();
        updateAll(myNotes);
        showBlankTip();
    }

    public void showBlankTip() {
        reGetNoteList();
        if (myNotes.size() == 0) {
            llBlankTip.setVisibility(View.VISIBLE);
        } else {
            llBlankTip.setVisibility(View.GONE);
        }
    }

    public void smoothScrollToTop() {
        rvNoteList.smoothScrollToPosition(0);
    }

    /*
    * @方法 删除指定pos的item
    *
    */
    public void deleteItemPos(int pos) {
        //首先获取这个位置的note
        NoteNew noteDelete = myNotes.get(pos);
        //然后remove这个position的内容
        adapter.remove(pos);
        Log.d("SKKK_____", noteDelete.toString());
        noteDelete.delete();
        showBlankTip();
    }

    /*
    * @方法 重新获取NoteList
    *
    */
    public void reGetNoteList() {
        myNotes = SQLUtils.getNoteList();
    }


    /*
    * @方法 设置note flag
    *
    */
    public void setNoteStyle(int noteStyle) {
        this.noteStyle = noteStyle;
    }

}


