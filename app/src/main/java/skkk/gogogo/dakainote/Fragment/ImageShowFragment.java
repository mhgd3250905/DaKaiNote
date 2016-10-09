package skkk.gogogo.dakainote.Fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import skkk.gogogo.dakainote.Adapter.ImageShowAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/10/2.
 */
/*
* 
* 描    述：用来显示所有的图片的fragment
* 作    者：ksheng
* 时    间：2016/10/2$ 1:02$.
*/
public class ImageShowFragment extends Fragment {

    protected View view;
    private RecyclerView rvNoteList;
    private ImageShowAdapter adapter;
    private GridLayoutManager mGridLayoutManager;
    private List<BitmapDrawable> imageList;


    public ImageShowFragment(List<BitmapDrawable> imageList) {
        this.imageList = imageList;
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
        /* @描述 设置Adapter */
        adapter = new ImageShowAdapter(getContext(), imageList);
        /* @描述 设置布局管理器 */
        mGridLayoutManager=new GridLayoutManager(getContext(),3);
        /* @描述 设置间距 */
        // = new SpacesItemDecoration(0);
        /* @描述 添加间距 */
        //rvNoteList.addItemDecoration(mDecoration);
        /* @描述 添加布局 */
        rvNoteList.setLayoutManager(mGridLayoutManager);
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

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

}
