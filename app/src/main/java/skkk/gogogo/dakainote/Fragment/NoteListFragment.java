package skkk.gogogo.dakainote.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import skkk.gogogo.dakainote.Adapter.NoteListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
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
    ArrayList<String> mData=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initUI(view);
        return view;
    }

    /*
    * @desc 这里需要实现recycler的瀑布流布局
    * @时间 2016/8/1 22:01
    */
    private void initUI(View view) {
        for(int i=0;i<30;i++){
            mData.add("here is  "+i);
        }

        //获取RecyclerView实例
        RecyclerView rvNoteList= (RecyclerView) view.findViewById(R.id.rv_note_list);

        //设置Adapter
        final NoteListAdapter adapter = new NoteListAdapter(getContext(),mData);
        //设置布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2
                ,StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置间距
        SpacesItemDecoration decoration=new SpacesItemDecoration(3);
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
                adapter.append(position,"aaa");
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                adapter.remove(position);

            }
        });
    }




}
