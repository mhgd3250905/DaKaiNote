package skkk.gogogo.dakainote.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Adapter.NoteVoiceListAdapter;
import skkk.gogogo.dakainote.Adapter.RecyclerViewBaseAdapter;
import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
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
public class VoiceNewNoteFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private View view;
    private List<VoiceCache> myVoices;
    private long noteKey = 0;
    private RecyclerView rvNoteImageList;
    private NoteVoiceListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int VOICE_DELETED=1012;
    private Handler mHandler;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==54321) {
                Bundle data = msg.getData();
                long myNoteKey = (long) data.get("notekey");
                insertVoice(myNoteKey);
            }
        }
    };


    public VoiceNewNoteFragment(long noteKey,Handler mHandler) {
        this.noteKey = noteKey;
        this.mHandler=mHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_image, container, false);
        initData();
        initUI(view);
        setNewPlayer();
        return view;
    }

    /*
    * @方法 从数据库中获取数据
    *
    */
    private void initData() {
        myVoices = new ArrayList<VoiceCache>();
        if (noteKey != 0) {
            myVoices = SQLUtils.getVoiceInItem(noteKey);
        }

    }


    /*
    * @方法 增加一个图片
    *
    */
    public void insertVoice(long noteKey) {
        reGetImageList(noteKey);
        updateAll(myVoices);
    }


    /*
        * @desc 这里需要实现recycler的瀑布流布局
        * @时间 2016/8/1 22:01
        */
    private void initUI(View view) {
        //获取RecyclerView实例
        rvNoteImageList = (RecyclerView) view.findViewById(R.id.rv_note_image_list);
        //设置Adapter
        adapter = new NoteVoiceListAdapter(getContext(), myVoices);
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
                /* @描述 在点击进入note详情之前先清空缓存数据库 */
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.release();
                    mediaPlayer=null;
                    setNewPlayer();
                }
                try {
                    mediaPlayer.setDataSource(myVoices.get(position).getVoicePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("提醒");
                builder.setIcon(R.drawable.item_recycle);
                builder.setMessage("您将删除该录音...");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myVoices.get(position).delete();
                        reGetImageList(noteKey);
                        updateAll(myVoices);
                        mHandler.sendEmptyMessage(VOICE_DELETED);
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
        myVoices = SQLUtils.getVoiceInItem(noteKey);
    }


    /*
    * @方法 原来一切都出在这个基类之上
    *
    */
    public void updateAll(List<VoiceCache> noteList) {
        adapter.setmItemDataList(noteList);
        adapter.notifyDataSetChanged();
    }


    /*
   * @方法 初始化MP
   *
   */
    public void setNewPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.reset();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaPlayer.release();
                return false;
            }
        });
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();//重置为初始状态
        }

    }

    /*
    * @方法 在fragment关闭的时候把媒体播放器关闭
    *
    */
    @Override
    public void onDestroy() {
        super.onDestroy();
        /* @描述 释放播放器 */
        mediaPlayer.release();
        mediaPlayer=null;
        LogUtils.Log("释放媒体播放器~~");
        /* @描述 清空handler */
        handler.removeCallbacksAndMessages(null);
    }
}
