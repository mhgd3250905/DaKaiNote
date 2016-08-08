package skkk.gogogo.dakainote.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.NoteDetailShowActivity;
import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.Utils.RecyclerViewDecoration.SpacesItemDecoration;

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
    private NoteListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initDB();
        initUI(view);
        return view;
    }

    private void initDB() {
        //获取db
        SQLiteDatabase db= Connector.getDatabase();
    }

    /*
    * @desc 这里需要实现recycler的瀑布流布局
    * @时间 2016/8/1 22:01
    */
    private void initUI(View view) {

        notes=new ArrayList<Note>();
        notes = DataSupport.findAll(Note.class);

        //获取RecyclerView实例
        RecyclerView rvNoteList= (RecyclerView) view.findViewById(R.id.rv_note_list);
        //设置Adapter
        adapter = new NoteListAdapter(getContext(),notes);
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

        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //从数据库中查询第position+1行数据
                Note note = DataSupport.find(Note.class, position + 1);

                Log.d("SKKKKKKKK---------", "从数据库查询第" + position + "条信息");
                //将查询之note类传给NOTE展示页面
                Intent intent = new Intent();
                intent.putExtra("note", note);
                intent.setClass(getContext(), NoteDetailShowActivity.class);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                adapter.remove(position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}
