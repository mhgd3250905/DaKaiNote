package skkk.gogogo.dakainote.Fragment;

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

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.BaseHomeActivity;
import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.UINoteShowActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.SpacesItemDecoration;
import skkk.gogogo.dakainote.R;

/*
* 
* 描    述：笔记列表界面
* 作    者：ksheng
* 时    间：
*/
public class NoteListFragment extends Fragment {
    View view;
    LayoutInflater inflater;
    private List<Note> notes;
    private List<Note> myNotes;
    private NoteListAdapter adapter;
    private Note noteShow;
    private RecyclerView rvNoteList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initUI(view);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SKKK_____", "here run onResume");
    }

    /*
        * @desc 这里需要实现recycler的瀑布流布局
        * @时间 2016/8/1 22:01
        */
    private void initUI(View view) {

        notes=new ArrayList<Note>();

        myNotes=new ArrayList<Note>();

        notes = DataSupport.findAll(Note.class);

        int j=0;
        for (int i=notes.size()-1;i>=0;i--){
            myNotes.add(j, notes.get(i));
            j++;
        }


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
        //设置Adapter
        rvNoteList.setAdapter(adapter);


        /*
        * @方法 item单击事件
        *
        */
        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //从数据库中查询第position行数据
                noteShow = myNotes.get(position);
                Log.d("SKKKKKKKK---------", "从数据库查询第" + position + "条信息");
                //将查询之note类传给NOTE展示页面
                Intent intent = new Intent();
                intent.putExtra("note", noteShow);
                intent.putExtra("pos",position);
                intent.setClass(getContext(), UINoteShowActivity.class);
                startActivity(intent);

            }

            /*
            * @方法 item长按事件
            *
            */
            @Override
            public void onItemLongClick(View view, int position) {

                //首先获取这个位置的note
                Note noteDelete=myNotes.get(position);
                //然后remove这个position的内容
                adapter.remove(position);
                Log.d("SKKK_____", noteDelete.toString());
                noteDelete.delete();
            }
        });
    }


    /*
    * @方法 设置所有的触发事件
    *
    */
    private void initEvent() {
        rvNoteList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            BaseHomeActivity activity= (UIHomeActivity) getActivity();
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(activity.getArcMenuStatus()){
                    activity.useArcMenuToggle(200);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    /*
    * @方法 更新list
    *
    */
    public void updateList(int position,Note note){
        adapter.append(position, note);
        Log.d("SKKK_____","updateOK here is fragment");
    }


}
