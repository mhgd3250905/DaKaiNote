package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.DbTable.Schedule;
import skkk.gogogo.dakainote.DbTable.Voice;
import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;

/**
 * Created by admin on 2016/10/20.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/20$ 22:40$.
*/
public class SaveNoteActivity extends BottomBarNoteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isDelete) {
                    saveAndCallBack();
                }
                finish();
            }
        });
    }

    /*
    * @方法 保存Note 内容
    *
    */
    private void saveNoteData() {
        isStore = false;
        /* @描述 保存对齐方式 */
        note.setGravity(sPref.getInt("edit_gravity", 0));

        /* @描述 保存标题 */
        if (!TextUtils.isEmpty(etNoteDetailTitle.getText().toString())) {
            note.setTitle(etNoteDetailTitle.getText().toString());
        } else {
            note.setTitle("无题");
        }

        /* @描述 保存et内容 */
        if (!TextUtils.isEmpty(etFirstSchedule.getText().toString()) &&
                !isScheduleExist) {
            /* @描述 如果不存在schedule而且et内容不为空 */
            note.setContent(etFirstSchedule.getText().toString());
            isStore = true;
        }

        if (isScheduleExist) {
            /* @描述 如果存在schedule */
            /* @描述 设置内容 */
            note.setContent(etFirstSchedule.getText().toString());
            /* @描述 设置schedule */
            int scheduleCount = llNoteAgain.getChildCount();
            for (int i = 0; i < scheduleCount; i++) {
                LinearLayout item = (LinearLayout) llNoteAgain.getChildAt(i);
                Schedule schedule = new Schedule();
                schedule.setScheduleChecked(false);
                if (item.getChildCount() == 2) {
                    schedule.setScheduleChecked(((CheckBox) item.getChildAt(0)).isChecked());
                    schedule.setScheduleContent(((EditText) item.getChildAt(1)).getText().toString());
                } else if (item.getChildCount() == 1) {
                    schedule.setScheduleContent(((EditText) item.getChildAt(0)).getText().toString());
                }
                schedule.save();
                note.getScheduleList().add(schedule);
                note.setScheduleIsExist(true);
            }
            isStore = true;
        }

        /* @描述 保存时间 */
        if (inetntNote == null) {
            note.setTime(DateUtils.getTime());//保存时间
        } else {
            //展示状态保留原来时间
            note.setTime(inetntNote.getTime());
        }

        /* @描述 设置noteKey */
        note.setKeyNum(noteKey);
        /* @描述 初始化无图片存在 */
        note.setImageIsExist(false);//初始化图片为不存在
        /* @描述 初始化无录音存在 */
        note.setVoiceExist(false);//初始化录音为不存在
        /* @描述 保存pin属性 */
        note.setPinIsExist(isPin);//设置pin属性
        /* @描述 保存图片 */
        //获取到缓存中的图片
        List<ImageCache> imageCaches = DataSupport
                .where("notekey=?", String.valueOf(noteKey))
                .find(ImageCache.class);
        //判断缓存图片是否存在
        if (imageCaches.size() != 0) {
            for (int i = 0; i < imageCaches.size(); i++) {
                Image image = new Image();
                image.setImagePath(imageCaches.get(i).getImagePath());
                image.save();
                note.getImageList().add(image);
                note.setImageIsExist(true);
                isStore = true;
            }
        }


        /* @描述 保存录音 */
        //获取到缓存中的图片
        List<VoiceCache> voiceCaches = DataSupport
                .where("notekey=?", String.valueOf(noteKey))
                .find(VoiceCache.class);
        //判断缓存图片是否存在
        if (voiceCaches.size() != 0) {
            for (int i = 0; i < voiceCaches.size(); i++) {
                Voice voice = new Voice();
                voice.setVoicePath(voiceCaches.get(i).getVoicePath());
                voice.save();
                note.getVoiceList().add(voice);
                note.setVoiceExist(true);
                isStore = true;
            }
        }

        LogUtils.Log("isStore");
        /* @描述 保存note */
        if (isStore) {
            note.save();
        } else {
            note = null;
            Toast.makeText(SaveNoteActivity.this, "您未保存任何内容", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        isDelete = true;
        super.onResume();
        LogUtils.Log("这里是onResume");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDelete) {
            saveAndCallBack();
        }
    }


    /*
      * @方法 针对相机非返回值处理
      *
      */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____", "requestCode:  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                /* @描述 相机返回值 */
                case REQUEST_IMAGE_CAPTURE:

                    fl_note_iamge.setVisibility(View.VISIBLE);
                    //设置图片存在
                    isImageExist = true;
                    LogUtils.Log(imagePath);
                    ImageCache imageCache = new ImageCache();
                    imageCache.setNoteKey(noteKey);
                    imageCache.setImagePath(imagePath);
                    imageCache.save();
                    //获取当前fragment

                    mImageNewNoteFragment.insertImage(noteKey);
                    LogUtils.Log("这里是onActivityResult");
                    isDelete = true;

                    break;

                /* @描述 相册返回值 */
                case PHOTO_REQUEST_GALLERY:

                    if (data != null) {
                        fl_note_iamge.setVisibility(View.VISIBLE);
                        //设置图片存在
                        isImageExist = true;
                        Uri uriImageFromGallery = data.getData();
                        LogUtils.Log(CameraImageUtils.getAbsoluteImagePath(SaveNoteActivity.this,
                                uriImageFromGallery));

                        ImageCache imageCacheGallery = new ImageCache();
                        imageCacheGallery.setNoteKey(noteKey);
                        imageCacheGallery.setImagePath(CameraImageUtils.getAbsoluteImagePath(SaveNoteActivity.this,
                                uriImageFromGallery));
                        imageCacheGallery.save();

                        //获取当前fragment
                        mImageNewNoteFragment.insertImage(noteKey);
                    }
                    break;

            }

        }

    }


    /*
        * @方法 保存数据而且返回响应的note
        *
        * 保存的时候
        * 0 判断是否是SHOW状态：
        *       如果是SHOW那么就需要保存以后刷新/如果仅仅是编辑状态就保存就可以了
        *
        * 1 判断是否有文字：有文字就需要保存在ContentText中
        *   a 如果没有文字就不用保存
        *   b 如果有文字那么就要进行保存操作
        *
        * 2 判断是否有pin：有pin就需要保存在note中
        *
        * 4 判断是否有image：有image就需要将imagePath保存在image中
        *
        * 5 判断是否有存储：有则保存/否则就提示
        *
        * 这里的操作应该是遍历ViewGroup中所有的child 判断类型然后做出对应的判断
        *
        *
        *
        */
    private void saveAndCallBack() {
        //判断是否有记录任何内容
        if (isShow) {
            //先删除
            int delete = DataSupport.delete(Note.class, inetntNote.getId());
            LogUtils.Log("这里是保存事件定位的note，id为" + inetntNote.getId());
            //再重新保存
            saveNoteData();
        } else {
            //保存内容
            saveNoteData();
        }
    }
}
