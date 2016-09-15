package skkk.gogogo.dakainote.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.ShowNewNoteActivity;
import skkk.gogogo.dakainote.Adapter.NoteScheduleListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.ScheduleCache;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.MyUtils.SpacesItemDecoration;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/9/10.
 */
/*
* 
* 描    述：显示图片的fragment
* 作    者：ksheng
* 时    间：2016/9/10$ 13:19$.
*/
public class ScheduleNewNoteFragment extends Fragment {

    private View view;
    private List<ScheduleCache> mySchedules;
    private long noteKey = 0;
    private RecyclerView rvNoteImageList;
    private NoteScheduleListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private SpacesItemDecoration mDecoration;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==12345) {
                Bundle data = msg.getData();
                long myNoteKey = (long) data.get("notekey");
                insertSchedule(myNoteKey);
            }
        }
    };



    public ScheduleNewNoteFragment(long noteKey) {
        this.noteKey = noteKey;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_image, container, false);
        initData();
        initUI(view);
        return view;
    }

    /*
    * @方法 从数据库中获取数据
    *
    */
    private void initData() {
        mySchedules = new ArrayList<ScheduleCache>();
        if (noteKey != 0) {
            mySchedules = SQLUtils.getScheduleInItem(noteKey);
        }
    }


    /*
    * @方法 增加一个图片
    *
    */
    public void insertSchedule(long noteKey) {
        reGetImageList(noteKey);
        updateAll(mySchedules);
    }


    /*
        * @desc 这里需要实现recycler的瀑布流布局
        * @时间 2016/8/1 22:01
        */
    private void initUI(View view) {
        //获取RecyclerView实例
        rvNoteImageList = (RecyclerView) view.findViewById(R.id.rv_note_image_list);
        //设置Adapter
        adapter = new NoteScheduleListAdapter(getContext(), mySchedules,noteKey);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置间距
        mDecoration = new SpacesItemDecoration(5);
        //添加间距
        rvNoteImageList.addItemDecoration(mDecoration);
        //添加布局
        rvNoteImageList.setLayoutManager(mLayoutManager);
        //设置基本动画
        rvNoteImageList.setItemAnimator(new DefaultItemAnimator());
        //rvNoteList
        rvNoteImageList.setAdapter(adapter);
        rvNoteImageList.setHasFixedSize(true);
        /*
        * @方法 item单击事件
        *
        */
        adapter.setOnItemClickLitener(new RecyclerViewBaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                /* @描述 在点击进入note详情之前先清空缓存数据库 */
                ShowNewNoteActivity activity= (ShowNewNoteActivity) getActivity();
                activity.setIsDelete(false);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /*
    * @方法 重新获取ImageList
    *
    */
    public void reGetImageList(long noteKey) {
        mySchedules = SQLUtils.getScheduleInItem(noteKey);
    }


    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<ScheduleCache> noteList) {
        adapter.setmItemDataList(noteList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
