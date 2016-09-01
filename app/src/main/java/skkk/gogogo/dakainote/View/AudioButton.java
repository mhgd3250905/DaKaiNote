package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import skkk.gogogo.dakainote.MyUtils.LogUtils;

/**
 * Created by admin on 2016/8/30.
 */
/*
* 
* 描    述：播放音频按钮
* 作    者：ksheng
* 时    间：2016/8/30$ 21:22$.
*/
public class AudioButton extends Button {
    private int durationTime;
    private Thread mVoiceThread;//播放音频之线程
    private static String voicePath;//播放音频之路径
    private MediaPlayer mediaPlayer;//音频播放器
    private Object newPlayer=null;

    public AudioButton(Context context) {
        this(context, null);
    }
    public AudioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public AudioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*
    * @方法 初始化
    *
    */
    private void init() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setText("点击播放录音...");
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
   * @方法 获取音频路径
   *
   */
    public String getVoicePath() {
        return voicePath;
    }

    /*
    * @方法 设置音频路径
    *
    */
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
        try {
            mediaPlayer.setDataSource(voicePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取duration时间
        durationTime = mediaPlayer.getDuration();
    }



    /*
    * @方法 开启音频播放线程
    *
    */
    public void startVoiceThead() {
        mVoiceThread = new Thread(voiceThead);
        mVoiceThread.start();
    }

    /*
    * @方法 音频播放线程中执行操作
    *
    */
    private Runnable voiceThead = new Runnable() {
        @Override
        public void run() {
            try {
                while (durationTime>0) {
                    Thread.sleep(100);
                    durationTime-=100;
                    if (durationTime%3==0){
                        mHandler.sendEmptyMessage(111);
                    }else if(durationTime%3==1){
                        mHandler.sendEmptyMessage(222);
                    }else if(durationTime%3==2){
                        mHandler.sendEmptyMessage(333);
                    }
                    LogUtils.Log(durationTime + "");

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 111:
                    if (durationTime<0){
                        durationTime=0;
                        setText("点击播放录音...");
                    }else {
                        setText("点击播放录音.");
                    }
                    break;
                case 222:
                    if (durationTime<0){
                        durationTime=0;
                        setText("点击播放录音..");
                    }else {
                        setText("点击播放录音..");
                    }
                    break;
                case 333:
                    if (durationTime<0){
                        durationTime=0;
                        setText("点击播放录音...");
                    }else {
                        setText("点击播放录音...");
                    }
                    break;
            }
        }
    };

    /*
    * @方法 按钮触摸事件
    *
    */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
               if(mediaPlayer==null){
                   setNewPlayer();
               }
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    startVoiceThead();
                }else {
                    mediaPlayer.pause();
                }
                break;
        }
        return true;
    }


    /*
    * @方法 销毁MP
    *
    */
    public void releaseMP(){
        mediaPlayer.release();
    }

    /*
    * @方法 初始化MP
    *
    */
    public void setNewPlayer() {

        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setText("点击播放录音...");
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
        try {
            mediaPlayer.setDataSource(voicePath);
            mediaPlayer.prepare();
            //获取duration时间
            durationTime = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE){
            LogUtils.Log("可见");
            //开始某些任务
        }
        else if(visibility == INVISIBLE || visibility == GONE){
            mediaPlayer.release();
            LogUtils.Log("MP销毁");
            //停止某些任务
        }
    }
}
