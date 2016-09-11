package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：关于弹射菜单的noteActivity
* 作    者：ksheng
* 时    间：2016/8/26$ 23:02$.
*/
public class ArcNewNoteActivity extends VoiceNewNoteActivity {
    protected ArcMenuView arcMenuView;
    protected FloatingActionButton fabNoteDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArcUI();
        initArcEvent();
        //initFabUI();

    }

    /*
    * @方法 初始化弹射菜单UI
    *
    */
    protected void initArcUI() {
        //设置弹射菜单
        arcMenuView = (ArcMenuView) findViewById(R.id.arc_menu_view_note);
    }

    /*
    * @方法 设置弹射菜单点击事件
    *
    */
    protected void initArcEvent() {
        arcMenuView.setmMenuItemClickListener(new ArcMenuView.OnMenuItemClickListener() {
                                                  @Override
                                                  public void onClick(View view, int pos) {
                                                      switch (pos) {
                                                          case 1:
                                                              /*
                                                            * @方法 点击调用相机拍照
                                                            *
                                                            */
                                                              isDelete=false;
                                                              takePicture(ArcNewNoteActivity.this);
                                                              break;
                                                          case 2:
                                                              if (isPin) {
                                                                  isPin = false;
                                                                  ivPin.setVisibility(View.INVISIBLE);
                                                              } else {
                                                                  isPin = true;
                                                                  ivPin.setVisibility(View.VISIBLE);
                                                              }
                                                              break;
                                                          case 3:
                                                              if (rbVoice.getVisibility() == View.VISIBLE) {
                                                                  rbVoice.setVisibility(View.GONE);
                                                              } else {
                                                                  rbVoice.setVisibility(View.VISIBLE);
                                                              }
                                                              break;
                                                          case 4:
                                                              //直接finish()是为了触发onPause中的保存
                                                              finish();
                                                              break;
                                                      }
                                                  }
                                              }
        );
    }

    /*
    * @方法 保存Note 内容
    *
    */
    private void saveNoteData() {
        isStore=false;
        /* @描述 保存标题 */
        if (isShow) {
            if (!TextUtils.isEmpty(etNoteDetailTitle.getText().toString())) {
                note.setTitle(etNoteDetailTitle.getText().toString());
            }else {
                note.setTitle("无题");
            }
        }else {
            if (TextUtils.isEmpty(etNoteDetailTitle.getText().toString())) {
                note.setTitle("无题");
            }
        }

        /* @描述 保存et内容 */
        if (!TextUtils.isEmpty(etNewNoteDetail.getText().toString())) {
           note.setContent(etNewNoteDetail.getText().toString());
            isStore=true;
        }

        /* @描述 保存时间 */
        if (inetntNote==null) {
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
        if (imageCaches.size()!=0){
            for (int i = 0; i < imageCaches.size(); i++) {
                Image image=new Image();
                image.setImagePath(imageCaches.get(i).getImagePath());
                image.save();
                note.getImageList().add(image);
                note.setImageIsExist(true);
                isStore=true;
            }
        }
        /* @描述 保存note */
        if (isStore) {
            note.save();
        }else {
            note=null;
            Toast.makeText(ArcNewNoteActivity.this, "您未保存任何内容...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isDelete){
            saveAndCallBack();
        }
        LogUtils.Log("这里是onPause");
    }

    @Override
    protected void onResume() {
        isDelete=true;
        super.onResume();
        LogUtils.Log("这里是onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /* @描述 删除缓存中的图片内容 */
        DataSupport.deleteAll(ImageCache.class);
    }

    /*
        * @方法 针对相机非返回值处理
        *
        */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____", "requestCode:  " + requestCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            fl_note_iamge.setVisibility(View.VISIBLE);
            //设置图片存在
            ImageCache imageCache=new ImageCache();
            imageCache.setNoteKey(noteKey);
            imageCache.setImagePath(imagePath);
            imageCache.save();
            //获取当前fragment
            mImageNewNoteFragment.insertImage(noteKey);
            LogUtils.Log("这里是onActivityResult");
            isDelete=true;
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
                int delete = DataSupport.delete(NoteNew.class, inetntNote.getId());
                LogUtils.Log("这里是保存事件定位的note，id为"+inetntNote.getId());
                //再重新保存
                saveNoteData();
            } else {
                //保存内容
                saveNoteData();
            }
//        }else {
//            Toast.makeText(ArcNewNoteActivity.this,getResources().getString(R.string.not_save_anything), Toast.LENGTH_SHORT).show();
//        }
    }



}
