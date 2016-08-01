package skkk.gogogo.dakainote.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skkk.gogogo.dakainote.R;

/*
* 
* 描    述：笔记列表界面
* 作    者：ksheng
* 时    间：
*/
public class NoteListFragment extends Fragment{
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_note_list,container,false);
        initUI(view);
        return view;
    }

    /*
    * @desc 这里需要实现recycler的瀑布流布局
    * @时间 2016/8/1 22:01
    */
    private void initUI(View view) {
        //获取RecyclerView实例
        RecyclerView rvNoteList = (RecyclerView) view.findViewById(R.id.rv_note_list);
        //初始化Grid布局
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,2);
        //设置布局管理器
        rvNoteList.setLayoutManager(staggeredGridLayoutManager);

    }
}
