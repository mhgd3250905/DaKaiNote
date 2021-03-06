package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.DbTable.Schedule;
import skkk.gogogo.dakainote.DbTable.TextPeriousCache;
import skkk.gogogo.dakainote.DbTable.Voice;
import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.Fragment.ImageNewNoteFragment;
import skkk.gogogo.dakainote.Fragment.VoiceNewNoteFragment;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/27.
 */
/*
* 
* 描    述：继承自ArcNewNoteActivity的专门用于设计显示内容的Activity
* 作    者：ksheng
* 时    间：2016/8/27$ 21:59$.
*/
public class ShowNoteActivity extends ScheduleNoteActivity {
    protected Note inetntNote;
    protected ImageNewNoteFragment mImageNewNoteFragment;
    protected VoiceNewNoteFragment mVoiceNewNoteFragment;
    protected final static int MESSAGE_LAYOUT_KEYBOARD_SHOW = 201601;
    protected final static int MESSAGE_LAYOUT_KEYBOARD_HIDE = 201602;
    protected final static int IMAGE_DELETED=1011;
    protected final static int VOICE_DELETED=1012;
    protected boolean isShow = false;//是否是展示页面

    protected long noteKey;
    MyImageThread myImageThread;
    MyVoiceThread myVoiceThread;
    //MyScheduleThread myScheduleThread;

    protected boolean isDelete=true;

    /* @描述 用来接收fragment中传来的信息 */
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_LAYOUT_KEYBOARD_SHOW:
                    showOrHideFl(View.GONE);

                    break;
                case MESSAGE_LAYOUT_KEYBOARD_HIDE:
                    showOrHideFl(View.VISIBLE);
                    break;
                case IMAGE_DELETED:
                    /* @描述 刷新fragment */
                    List<ImageCache> imageInItem = SQLUtils.getImageInItem(noteKey);
                    mImageNewNoteFragment.updateAll(imageInItem);
                    if (imageInItem.size()==0){
                        fl_note_iamge.setVisibility(View.GONE);
                /* @描述 把图片存在flag设置为false
                 * 如果是展示页面就把展示页面的note设置图片不存在
                  * 主要是为了解决在软键盘出现消失的时候fl_image的显示问题*/
                        isImageExist=false;
                        if (inetntNote!=null){
                            inetntNote.setImageIsExist(false);
                        }
                    }
                    break;
                case VOICE_DELETED:
                    /* @描述 刷新fragment */
                    List<VoiceCache> voiceInItem = SQLUtils.getVoiceInItem(noteKey);
                    mVoiceNewNoteFragment.updateAll(voiceInItem);
                    if (voiceInItem.size()==0){
                        fl_note_voice.setVisibility(View.GONE);
                /* @描述 把图片存在flag设置为false
                 * 如果是展示页面就把展示页面的note设置图片不存在
                  * 主要是为了解决在软键盘出现消失的时候fl_image的显示问题*/
                        isVoiceExist=false;
                        if (inetntNote!=null){
                            inetntNote.setImageIsExist(false);
                        }
                    }
                    break;
            }
        }
    };

    /*
    * @方法 根据不同状态设置fl的隐藏与显示
    *
    */
    public void showOrHideFl(int visibility) {
        if (inetntNote != null) {
            /* @描述 如果是显示状态 */
            if (inetntNote.isVoiceExist() || isVoiceExist) {
                fl_note_voice.setVisibility(visibility);
            }
            if (inetntNote.isImageIsExist() || isImageExist) {
                fl_note_iamge.setVisibility(visibility);
            }

            //当软键盘显示的时候就把录音按钮隐藏起来~
            rbVoice.setVisibility(View.GONE);
        } else {
            /* @描述 如果是编辑状态 */
            if (isVoiceExist) {
                fl_note_voice.setVisibility(visibility);
            }
            if (isImageExist) {
                fl_note_iamge.setVisibility(visibility);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传入的intent中包含的note数据
        inetntNote = (Note) getIntent().getSerializableExtra("note");
        if (inetntNote != null) {
            //如果传入note不是空的那么就说明是展示页面
            isShow = true;
        }

        note = new Note();//这里必须要初始化note

        /* @描述 如果是展示页面 */
        if (isShow) {
            noteKey=inetntNote.getKeyNum();
            initShowUI();
            //使edit失去焦点
            llNoteDetail.setFocusable(true);
            llNoteDetail.setFocusableInTouchMode(true);
        } else {
            /* @描述 如果是编辑界面 */
            tvNoteDetailTime.setText(DateUtils.getTime());
            LogUtils.Log("编辑界面时间显示为" + DateUtils.getTime());
             /* @描述 保存唯一标识码 */
            noteKey=System.currentTimeMillis();
            addDefaultFragment();
        }
    }


    /*
     * @desc 加入默认的Fragment界面
     * @时间 2016/8/1 21:44
     */
    private void addDefaultFragment() {
        /* @描述 加载图片fl布局 */
        mImageNewNoteFragment = new ImageNewNoteFragment(noteKey,mHandler);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_image,mImageNewNoteFragment).commit();
        /* @描述 加载声音fl布局 */
        mVoiceNewNoteFragment=new VoiceNewNoteFragment(noteKey,mHandler);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_voice, mVoiceNewNoteFragment).commit();
    }

    /*
    * @方法 布置展示页面
    * 1 edit设置为取消光标
    * 2 将note中包含的内容显示到布局页面中
    *
    */
    private void initShowUI() {
        /* @描述 设置对齐方式 */
        etFirstSchedule.setGravity(inetntNote.getGravity());
        /* @描述 设置标题 */
        etNoteDetailTitle.setText(inetntNote.getTitle());
        /* @描述 设置时间 */
        tvNoteDetailTime.setText(inetntNote.getTime());
        /* @描述 判断并设置pin */
        isPin = inetntNote.isPinIsExist();
        if (isPin) {
            ivPin.setVisibility(View.VISIBLE);
        } else {
            ivPin.setVisibility(View.INVISIBLE);
        }
        /* @描述 设置内容 */
        String content=inetntNote.getContent();
        etFirstSchedule.setAutoLinkText(content);

        //在展示界面记录下第一步
        TextPeriousCache textPeriousCacheBase = new TextPeriousCache();
        textPeriousCacheBase.setView_name("etFirstSchedule");
        textPeriousCacheBase.setContent(inetntNote.getContent());
        textPeriousCacheBase.save();

        //初始化imageFragment & voiceFragment
        addDefaultFragment();

        /* @描述 载入图片 以及 载入录音*/
        if (inetntNote.isImageIsExist()){
            fl_note_iamge.setVisibility(View.VISIBLE);
            //说明存在图片
            //获取图片列表
            if (myImageThread!=null){
                myImageThread=null;
            }
            myImageThread=new MyImageThread();
            myImageThread.start();
        }

        if (inetntNote.isVoiceExist()){
            fl_note_voice.setVisibility(View.VISIBLE);
            //说明存在图片
            //获取图片列表
            if (myVoiceThread!=null){
                myVoiceThread=null;
            }
            myVoiceThread=new MyVoiceThread();
            myVoiceThread.start();
        }

        if (inetntNote.isScheduleIsExist()){
            List<Schedule> scheduleList=inetntNote.getMyScheduleList();
            for (int i = 0; i < scheduleList.size(); i++) {
                cbfirstSchedule.setVisibility(View.VISIBLE);
                isScheduleExist=true;
                etFirstSchedule.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1));
                etFirstSchedule.setGravity(Gravity.CENTER_VERTICAL);
                etFirstSchedule.setHint("输入待办项");
                etFirstSchedule.setSingleLine(true);
                nsvEditAgain.setFillViewport(false);
                if (i==0){
                    etFirstSchedule.setText(scheduleList.get(0).getScheduleContent());
                    cbfirstSchedule.setChecked(scheduleList.get(0).isScheduleChecked());
                }else {
                    insertFirstItem(scheduleList.get(i).isScheduleChecked(),
                            scheduleList.get(i).getScheduleContent());
                }
            }
        }
    }

    /* @描述 用来转存图片的线程 */
    class MyImageThread extends Thread{
        @Override
        public void run() {
            super.run();
            /* @描述 第一步清空缓存 */
            DataSupport.deleteAll(ImageCache.class);

            /* @描述 第二步获取对应note中包含的图片
             *     以及获取对应的note中包含的录音  */
            List<Image> imageList = inetntNote.getMyImageList();

            //第三步进行遍历把图片都保存到缓存数据库中
            for (int i = 0; i < imageList.size(); i++) {
                ImageCache imageCache=new ImageCache();
                imageCache.setImagePath(imageList.get(i).getImagePath());
                imageCache.setNoteKey(inetntNote.getKeyNum());
                imageCache.save();
            }

            //发送消息让fragment从缓存数据库刷新数据
            Message msg1=new Message();
            Bundle bundle=new Bundle();
            bundle.putLong("notekey", noteKey);
            msg1.setData(bundle);
            msg1.what=12345;
            mImageNewNoteFragment.handler.sendMessageDelayed(msg1, 100);
        }
    }

    /* @描述 用来转存图片的线程 */
    class MyVoiceThread extends Thread{
        @Override
        public void run() {
            super.run();
            /* @描述 第一步清空缓存 */
            DataSupport.deleteAll(VoiceCache.class);


            /* @描述 第二步获取对应note中包含的图片
             *     以及获取对应的note中包含的录音  */
            List<Voice> voiceList=inetntNote.getMyVoiceList();

            //第三步进行遍历把录音都保存到缓存数据库中
            for (int i = 0; i < voiceList.size(); i++) {
                VoiceCache voiceCache=new VoiceCache();
                voiceCache.setVoicePath(voiceList.get(i).getVoicePath());
                voiceCache.setNoteKey(inetntNote.getKeyNum());
                voiceCache.save();
            }

            //发送消息让fragment从缓存数据库刷新数据
            Message msg2=new Message();
            Bundle bundle=new Bundle();
            bundle.putLong("notekey", noteKey);
            msg2.setData(bundle);
            msg2.what=54321;
            mVoiceNewNoteFragment.handler.sendMessageDelayed(msg2,100);

        }
    }


    /*
    * @方法 在页面关闭之前关闭线程
    *
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myImageThread!=null) {
            myImageThread.interrupt();
            myImageThread = null;
        }
        if (myVoiceThread!=null) {
            myVoiceThread.interrupt();
            myVoiceThread = null;
        }


    }

}
