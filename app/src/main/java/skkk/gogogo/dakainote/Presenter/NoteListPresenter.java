package skkk.gogogo.dakainote.Presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.SaveNoteActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.View.MyNoteView;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by admin on 2016/10/22.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/22$ 10:26$.
*/
public class NoteListPresenter {

    private NoteListFragment mNoteListFragment;
    private LinearLayoutManager mLinearLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private List<Note> myNotes=new ArrayList<Note>();
    private Note noteShow;
    private SharedPreferences sPref;
    private MyApplication mApplication;

    public NoteListPresenter(NoteListFragment noteListFragment) {
        mApplication= (MyApplication) getApplicationContext();
        mNoteListFragment = noteListFragment;
        sPref=mApplication.getsPref();
    }

    /*
    * @方法 设置布局
    * @描述 通过修改布局管理器来修改布局样式
    * @参数 布局参数 int
    * @返回值 null
    */
    public void setLayoutFlag() {
        RecyclerView rvNoteList=mNoteListFragment.getRecyclerView();
        switch (sPref.getInt("note_style",1)) {
            case 0:
                /* @描述 设置布局管理器 */
                mLinearLayoutManager = new LinearLayoutManager(mNoteListFragment.getContext());
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
                mGridLayoutManager = new GridLayoutManager(mNoteListFragment.getContext(), 2);
                 /* @描述 添加布局 */
                rvNoteList.setLayoutManager(mGridLayoutManager);
                break;
        }
    }


    /*
    * @方法 打开Note编辑界面
    * @参数 position
    * @返回值 null
    */
    public void startNote(int position) {
         /* @描述 如果是正常显示状态那么点击fab进入note编辑界面 */
        //从数据库中查询第position行数据
        noteShow = myNotes.get(position);
        Log.d("SKKKKKKKK---------", noteShow.toString());
        //将查询之note类传给NOTE展示页面
        Intent intent = new Intent();
        intent.putExtra("note", noteShow);
        intent.putExtra("pos", position);
        intent.setClass(mNoteListFragment.getContext(), SaveNoteActivity.class);


        LogUtils.Log("这里是点击事件定位的note，id为" + noteShow.getId());
        LogUtils.Log("note: " + noteShow.toString());
        mNoteListFragment.getActivity().startActivity(intent);
    }


    /*
    * @方法 选中所有Notes
    * @参数 position
    * @返回值 null
    */
    public void selectNotes(int position){
        /* @描述 如果是编辑状态那么点击fab执行删除操作 */
        View position1 = null;
        switch (mNoteListFragment.getLayoutFlag()) {
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

    /*
    * @方法 删除Notes
    * @参数 null
    * @返回值 null
    */
    public void deleteNotes(){
        for (int i = myNotes.size() - 1; i >= 0; i--) {
            View positionView=null;
            switch (mNoteListFragment.getLayoutFlag()){
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

                MyNoteView myNoteViewPos = (MyNoteView) ((CardView) positionView).getChildAt(0);
                if (myNoteViewPos.isDeleteChecked()) {
                    deleteItemPos(i);
                }
            }
        }
        mNoteListFragment.getAdapter().notifyDataSetChanged();
    }

    /*
    * @方法 删除指定位置的Note
    * @参数 位置 pos int
    * @返回值 null
    */
    private void deleteItemPos(int pos) {
        //首先获取这个位置的note
        Note noteDelete = myNotes.get(pos);
        //然后remove这个position的内容
        mNoteListFragment.getAdapter().remove(pos);
        Log.d("SKKK_____", noteDelete.toString());
        noteDelete.delete();
    }

    /*
    * @方法 返回是否需要显示空白提示
    * @参数 null
    * @返回值 boolean
    */
    public boolean isShowBlankTip(){
        myNotes = SQLUtils.getNoteList();
        if (myNotes.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * @方法 更新适配器中myNotes
    * @参数 List<Note>
    * @返回值 null
    */
    public void updateNotes(List<Note> newNotes){
        mNoteListFragment.getAdapter().setmItemDataList(newNotes);
        mNoteListFragment.getAdapter().notifyDataSetChanged();
    }

    /*
    * @方法 获取myNotes
    * @参数
    * @返回值 List<Note>
    */
    public List<Note> getMyNotes() {
        return myNotes;
    }

    /*
    * @方法 设置myNotes
    * @参数 List<Note>
    * @返回值
    */
    public void setMyNotes(List<Note> myNotes) {
        this.myNotes = myNotes;
    }

    /*
    * @方法 重新获取myNotes
    * @参数
    * @返回值 List<Note>
    */
    public List<Note> reGetMyNotes() {
        return SQLUtils.getNoteList();
    }
}
