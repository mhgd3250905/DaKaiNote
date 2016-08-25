package skkk.gogogo.dakainote.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.BaseHomeActivity;
import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.UINoteEditActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.MyUtils.SpacesItemDecoration;
import skkk.gogogo.dakainote.R;

/*
* 
* 描    述：笔记列表界面
* 作    者：ksheng
* 时    间：
*/
public class NoteListFragment extends Fragment {
    protected int REQUEST_CODE_2=2;
    View view;
    protected List<Note> notes;
    protected List<Note> myNotes;
    protected NoteListAdapter adapter;
    protected Note noteShow;
    protected RecyclerView rvNoteList;

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

        myNotes=new ArrayList<Note>();

        myNotes= SQLUtils.getNoteList();

        //获取RecyclerView实例
        rvNoteList = (RecyclerView) view.findViewById(R.id.rv_note_list);
        //设置Adapter
        adapter = new NoteListAdapter(getContext(),myNotes);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置间距
        SpacesItemDecoration decoration=new SpacesItemDecoration(5);
        //添加间距
        rvNoteList.addItemDecoration(decoration);
        //添加布局
        rvNoteList.setLayoutManager(layoutManager);
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
                //从数据库中查询第position行数据
                noteShow = myNotes.get(position);
                Log.d("SKKKKKKKK---------", noteShow.toString());
                //将查询之note类传给NOTE展示页面
                Intent intent = new Intent();
                intent.putExtra("note", noteShow);
                intent.putExtra("pos", position);
                intent.setClass(getContext(), UINoteEditActivity.class);
                //getActivity().startActivityForResult(intent, REQUEST_CODE_2);
                getActivity().startActivity(intent);
            }

            /*
            * @方法 item长按事件
            *
            */
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("提醒");
                builder.setIcon(R.drawable.item_recycle);
                builder.setMessage("您将删除这条Note...");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItemPos(position);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }


    /*
    * @方法 设置所有的触发事件
    *
    */
    private void initEvent() {
        rvNoteList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            BaseHomeActivity activity = (UIHomeActivity) getActivity();

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (activity.getArcMenuStatus()) {
                    activity.useArcMenuToggle(200);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        myNotes=SQLUtils.getNoteList();
        updateAll(myNotes);
    }

    /*
        * @方法 更新指定位置list
        *
        */
    public void updatePos(int position,Note note){
        adapter.append(position, note);
        Log.d("SKKK_____", "updateOK here is fragment");
    }

    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<Note> noteList){
        adapter.setmItemDataList(noteList);
        adapter.notifyDataSetChanged();
    }



    public void smoothScrollToTop(){
        rvNoteList.smoothScrollToPosition(0);
    }

    public void deleteItemPos(int pos){
        //首先获取这个位置的note
        Note noteDelete = myNotes.get(pos);
        //然后remove这个position的内容
        adapter.remove(pos);
        Log.d("SKKK_____", noteDelete.toString());
        noteDelete.delete();
    }

    public void reGetNoteList(){
        myNotes=SQLUtils.getNoteList();
    }

}


