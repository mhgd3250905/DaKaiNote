package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import java.io.IOException;

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
                }else {
                    mediaPlayer.stop();
                    mediaPlayer=null;
                }

                break;
        }
        return true;
    }

    public void setNewPlayer() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer=null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
