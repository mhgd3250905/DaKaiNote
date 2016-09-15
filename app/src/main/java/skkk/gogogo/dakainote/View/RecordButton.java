package skkk.gogogo.dakainote.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import skkk.gogogo.dakainote.MyUtils.AudioRecorder;
import skkk.gogogo.dakainote.R;


/*
* 
* 描    述：仿微信录音自定义按钮
* 作    者：ksheng
* 时    间：2016/8/29$ 21:21$.
*/
public class RecordButton extends Button{

    private static final int MIN_RECORD_TIME=1;//最短录音时间，单位秒
    private static final int RECORD_OFF=0;//不在录音
    private static final int RECORD_ON=1;//正在录音

    private Dialog mRecordDialog;

    private AudioRecorder mAudioRecorder;//

    private Thread mRecordThread;//录音线程

    private RecordListener listener;//录音监听事件

    /*
    * @方法 录音监听事件
    *
    */
    public interface RecordListener {
        public void recordEnd(String filePath);
    }

    private int recordStata=0;//录音状态
    private float recodeTime=0.0f;//录音时长，如果录音时间太短则录音失效
    private double voiceValue=0.0;//录音的音量值；
    private boolean isCanceled=false;//是否取消录音
    private float downY;

    private TextView dialogTextView;
    private ImageView dialogImg;
    private Context mContext;

    public RecordButton(Context context) {
        this(context, null);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化
    private void init(Context context) {
        mContext=context;
        this.setText("按住 说话");
    }

    //设置录音接口
    public void setAudioRecord(AudioRecorder record){
        this.mAudioRecorder=record;
    }

    //设置监听事件
    public void setRecordListener(RecordListener listener){
        this.listener=listener;
    }

    /*
    * @方法 显示声音Dialog
    *
    */
    private void showVoiceDialog(int flag){
        if(mRecordDialog==null){
            mRecordDialog=new Dialog(mContext,R.style.Dialogstyle);
            mRecordDialog.setContentView(R.layout.dialog_record);
            dialogImg= (ImageView) mRecordDialog.findViewById(R.id.record_dialog_img);
            dialogTextView= (TextView) mRecordDialog.findViewById(R.id.record_dialog_txt);
        }
        switch (flag){
            case 1:
                dialogImg.setImageResource(R.drawable.vector_drawable_voice);
                dialogTextView.setText("松开手指可取消录音");
                this.setText("松开手指 取消录音");
                break;
            default:
                dialogImg.setImageResource(R.drawable.vector_drawable_voice);
                dialogTextView.setText("向上滑动可取消录音");
                this.setText("松开手指 完成录音");
                break;
        }
        dialogTextView.setTextSize(14);
        mRecordDialog.show();
    }

    /*
    * @方法 录音时间太短Toast 显示
    *
    */
    private void showWarnToast(String toastText){
        Toast toast=new Toast(mContext);
        View warnView= LayoutInflater.from(mContext).inflate(R.layout.toast_warn,null);
        toast.setView(warnView);
        toast.setGravity(Gravity.CENTER, 0, 0);//起点位置为中间
        toast.show();
    }

    /*
    * @方法 开启录音计时线程
    *
    */
    private void callRecordTimeThread(){
        mRecordThread=new Thread(recordThread);
        mRecordThread.start();
    }

    /*
    * @方法 录音Dialog图片随录音音量大小切换
    *
    */
    private void setDialogImage() {
        if (voiceValue < 600.0) {
            dialogImg.setImageResource(R.drawable.vector_drawable_voice);
        }
    }

    /*
    * @方法 录音线程
    *
    */
    private Runnable recordThread=new Runnable() {
        @Override
        public void run() {
            recodeTime=0.0f;
            while (recordStata==RECORD_ON){
                try {
                    Thread.sleep(100);
                    recodeTime+=0.1;
                    //获取音量，更新dialog
                    if(!isCanceled){
                        voiceValue=mAudioRecorder.getAmplitude();
                        recordHandler.sendEmptyMessage(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //定义handler
    private Handler recordHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setDialogImage();
        }
    };

    /*
    * @方法 触摸事件监听
    *
    */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://按下按钮
                if(recordStata!=RECORD_ON){
                    showVoiceDialog(0);
                    downY=event.getY();
                    if(mAudioRecorder!=null){
                        mAudioRecorder.ready();
                        recordStata=RECORD_ON;
                        mAudioRecorder.start();
                        callRecordTimeThread();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE://滑动手指
                float moveY=event.getY();
                //滑动距离超过50那么显示
                //"松开可取消录音" 提示
                if(downY-moveY>50){
                    isCanceled=true;
                    showVoiceDialog(1);
                }
                //滑动距离小于20那么还是录音界面显示
                if(downY-moveY<20){
                    isCanceled=false;
                    showVoiceDialog(0);
                }
                break;
            case MotionEvent.ACTION_UP://松开手指
                //松开手指 关闭dialog 改变记录状态
                if(recordStata==RECORD_ON){
                    recordStata=RECORD_OFF;
                    if(mRecordDialog.isShowing()){
                        mRecordDialog.dismiss();
                    }
                    mAudioRecorder.stop();
                    mRecordThread.interrupt();
                    voiceValue=0.0;
                    if(isCanceled){
                        /*
                        * 如果松开手指之后
                        * isCancel标签是true
                        * 那么说明之前滑动距离过大
                        * 已经强制结束了
                        * ？？？
                        */
                        mAudioRecorder.deleteOldFile();
                    }else {
                        if(recodeTime<MIN_RECORD_TIME){
                            showWarnToast("时间太短，录音失败");
                            mAudioRecorder.deleteOldFile();
                        }else {
                            if (listener!=null){
                                listener.recordEnd(mAudioRecorder.getFilePath());
                            }
                        }
                    }
                    isCanceled=false;
                    this.setText("按住 说话");
                }
                break;
        }
        return true;
    }


}
