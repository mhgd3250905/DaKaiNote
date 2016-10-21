package skkk.gogogo.dakainote.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Adapter.NoteImageListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by admin on 2016/9/10.
 */
/*
* 
* 描    述：显示图片的fragment
* 作    者：ksheng
* 时    间：2016/9/10$ 13:19$.
*/
public class ImageNewNoteFragment extends Fragment {

    private View view;
    private List<ImageCache> myImages;
    private long noteKey = 0;
    private RecyclerView rvNoteImageList;
    private NoteImageListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int REQUEST_NOTE_IMAGE_DELETE = 13;
    private int IMAGE_DELETED=1011;
    private Handler mHandler;
    PhotoViewAttacher mAttacher;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==12345) {
                Bundle data = msg.getData();
                long myNoteKey = (long) data.get("notekey");
                insertImage(myNoteKey);
            }
        }
    };

    @SuppressLint("ValidFragment")
    public ImageNewNoteFragment(long noteKey,Handler mHandler) {
        this.noteKey = noteKey;
        this.mHandler=mHandler;
    }

    public ImageNewNoteFragment() {
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
        myImages = new ArrayList<ImageCache>();
        if (noteKey != 0) {
            myImages = SQLUtils.getImageInItem(noteKey);
        }

    }


    /*
    * @方法 增加一个图片
    *
    */
    public void insertImage(long noteKey) {
        reGetImageList(noteKey);
        updateAll(myImages);
    }


    /*
        * @desc 这里需要实现recycler的瀑布流布局
        * @时间 2016/8/1 22:01
        */
    private void initUI(View view) {
        //获取RecyclerView实例
        rvNoteImageList = (RecyclerView) view.findViewById(R.id.rv_note_image_list);
        //设置Adapter
        adapter = new NoteImageListAdapter(getContext(), myImages);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置间距
        //mDecoration = new SpacesItemDecoration(5);
        //添加间距
        //rvNoteImageList.addItemDecoration(mDecoration);
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

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                View imageShowView=View.inflate(getContext(), R.layout.dialog_note_image_show, null);
                ImageView imageView= (ImageView) imageShowView.findViewById(R.id.iv_note_item_image);
                Bitmap bitmap= BitmapFactory.decodeFile(myImages.get(position).getImagePath());
                imageView.setImageBitmap(bitmap);
                builder.setView(imageShowView);
                mAttacher=new PhotoViewAttacher(imageView);
                AlertDialog dialog = builder.create();
                dialog.show();

                DisplayMetrics dm = new DisplayMetrics();
                //获取屏幕信息
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

                int screenWidth = dm.widthPixels;

                int screenHeigh = dm.heightPixels;

                int bitmapWidth = bitmap.getWidth();

                int bitmapHeight = bitmap.getHeight();
                WindowManager.LayoutParams params =

                        dialog.getWindow().getAttributes();//获取dialog信息

                if (bitmapWidth>screenWidth||bitmapHeight>screenHeigh){
                    params.width = screenWidth-20;

                    params.height =screenHeigh*2/3;

                }else {
                    params.width = bitmapWidth;

                    params.height =bitmapHeight;
                }

                dialog.getWindow().setAttributes(params);//设置大小

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("提醒");
                builder.setIcon(R.drawable.item_recycle);
                builder.setMessage("您将删除图片...");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myImages.get(position).delete();
                        reGetImageList(noteKey);
                        updateAll(myImages);
                        mHandler.sendEmptyMessage(IMAGE_DELETED);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();

            }
        });
    }

    /*
    * @方法 重新获取ImageList
    *
    */
    public void reGetImageList(long noteKey) {
        myImages = SQLUtils.getImageInItem(noteKey);
    }


    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<ImageCache> noteList) {
        adapter.setmItemDataList(noteList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
