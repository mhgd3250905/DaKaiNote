package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.litepal.crud.DataSupport;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.NoteNew;
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
public class ShowNewNoteActivity extends UINewNoteActivity {
    protected NoteNew inetntNote;
    protected ImageNewNoteFragment mImageNewNoteFragment;
    protected VoiceNewNoteFragment mVoiceNewNoteFragment;
    protected long noteKey;
    MyImageThread myImageThread;

    protected boolean isDelete=true;
    /* @描述 用来设置isDelete */
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();//获取传入的数据

        /* @描述 如果是展示页面 */
        note = new NoteNew();//这里必须要初始化note
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
            initNote();
            addDefaultFragment();
        }
    }


    /*
     * @方法 获取传入的
     *
     */
    private void beforeStart() {
        //获取传入的intent中包含的note数据
        inetntNote = (NoteNew) getIntent().getSerializableExtra("note");
        if (inetntNote != null) {
            //如果传入note不是空的那么就说明是展示页面
            isShow = true;
        }
    }

    /*
     * @方法 初始化note
     *
     */
    private void initNote() {
        /* @描述 保存唯一标识码 */
        noteKey=System.currentTimeMillis();
        note.setKeyNum(noteKey);//保存标识
    }

    /*
     * @desc 加入默认的Fragment界面
     * @时间 2016/8/1 21:44
     */
    private void addDefaultFragment() {
        /* @描述 加载图片fl布局 */
        mImageNewNoteFragment = new ImageNewNoteFragment(noteKey);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_image,mImageNewNoteFragment).commit();
        /* @描述 加载声音fl布局 */
        mVoiceNewNoteFragment=new VoiceNewNoteFragment(noteKey);
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
        etNewNoteDetail.setText(inetntNote.getContent());

        /* @描述 先把fragment搁好 */
        mImageNewNoteFragment = new ImageNewNoteFragment(noteKey);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_image,mImageNewNoteFragment).commit();

        /* @描述 先把fragment搁好 */
        mVoiceNewNoteFragment = new VoiceNewNoteFragment(noteKey);
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_note_voice,mVoiceNewNoteFragment).commit();

        /* @描述 载入图片 以及 载入录音 */
        if (inetntNote.isImageIsExist()){
            fl_note_iamge.setVisibility(View.VISIBLE);
            //说明存在图片
            //获取图片列表
            if (myImageThread!=null){
                myImageThread=null;
            }
            myImageThread=new MyImageThread();
            myImageThread.start();

        }else if (inetntNote.isVoiceExist()){
            fl_note_voice.setVisibility(View.VISIBLE);
            //说明存在图片
            //获取图片列表
        }
    }

    /* @描述 用来转存图片的线程 */
    class MyImageThread extends Thread{
        @Override
        public void run() {
            super.run();
            /* @描述 第一步清空缓存 */
            DataSupport.deleteAll(ImageCache.class);
            DataSupport.deleteAll(VoiceCache.class);

            /* @描述 第二步获取对应note中包含的图片
             *     以及获取对应的note中包含的录音  */
            List<Image> imageList = inetntNote.getMyImageList();
            List<Voice> voiceList=inetntNote.getMyVoiceList();

            //第三步进行遍历把图片都保存到缓存数据库中
            for (int i = 0; i < imageList.size(); i++) {
                ImageCache imageCache=new ImageCache();
                imageCache.setImagePath(imageList.get(i).getImagePath());
                imageCache.setNoteKey(inetntNote.getKeyNum());
                imageCache.save();
            }

            //第三步进行遍历把图片都保存到缓存数据库中
            for (int i = 0; i < voiceList.size(); i++) {
                VoiceCache voiceCache=new VoiceCache();
                voiceCache.setVoicePath(voiceList.get(i).getVoicePath());
                voiceCache.setNoteKey(inetntNote.getKeyNum());
                voiceCache.save();
            }

            //发送消息让fragment从缓存数据库刷新数据
            Message msg1=new Message();
            Message msg2=new Message();
            Bundle bundle=new Bundle();
            bundle.putLong("notekey", noteKey);
            msg1.setData(bundle);
            msg1.what=12345;
            msg2.setData(bundle);
            msg2.what=54321;
            mImageNewNoteFragment.handler.sendMessageDelayed(msg1,100);
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

    }


    /*
     * @方法 针对删除note中图片进行返回值处理
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____", "requestCode:  " + requestCode);
        if(requestCode==REQUEST_NOTE_IMAGE_DELETE&&resultCode==RESULT_OK){
            LogUtils.Log("你选择了删除图片");
            /* @描述 在图片详情界面选择了删除图片*/

            /* @描述 获取图片的路径以及notekey */
            String image_detail_path = (String) data.getExtras().get("image_detail_path");
            //long image_detail_notekey = (long) data.getExtras().get("image_detail_notekey");

            /* @描述 删除缓存数据库中的对应图片 */
            if(isShow){
                int deleteImage= DataSupport.deleteAll(ImageCache.class, "imagepath=?", image_detail_path);
                DataSupport.deleteAll(Image.class,"imagepath=?",image_detail_path);

                LogUtils.Log("image表中删除行数为 "+deleteImage);
            }else {
                int deleteImage= DataSupport.deleteAll(ImageCache.class, "imagepath=?", image_detail_path);
                LogUtils.Log("image表中删除行数为 "+deleteImage);
            }
            /* @描述 刷新fragment */
            List<ImageCache> imageInItem = SQLUtils.getImageInItem(noteKey);
            mImageNewNoteFragment.updateAll(imageInItem);
            if (imageInItem.size()==0){
                fl_note_iamge.setVisibility(View.GONE);
            }
        }
    }
}
