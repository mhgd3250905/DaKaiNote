package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import skkk.gogogo.dakainote.DbTable.ContentText;
import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;
import skkk.gogogo.dakainote.View.MyImageView;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：关于弹射菜单的noteActivity
* 作    者：ksheng
* 时    间：2016/8/26$ 23:02$.
*/
public class ArcNewNoteActivity extends ShowNewNoteActivity {

    private ArcMenuView arcMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArcUI();
        initArcEvent();
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
                                                              break;
                                                          case 2:
                                                            /*
                                                            * @方法 点击调用相机拍照
                                                            *
                                                            */
                                                              takePicture(ArcNewNoteActivity.this);
                                                              break;
                                                          case 3:
                                                              if (isPin) {
                                                                  isPin = false;
                                                                  ivPin.setVisibility(View.INVISIBLE);
                                                              } else {
                                                                  isPin = true;
                                                                  ivPin.setVisibility(View.VISIBLE);
                                                              }
                                                              break;
                                                          case 4:
                                                              break;
                                                          case 5:
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
        int detailCount = llNoteDetail.getChildCount();

        LogUtils.Log("目前ll中包含控件数量为 " + detailCount);

        LogUtils.Log("NoteDetail中child数量" + detailCount);
        if (detailCount == 0) {
            return;
        }

        //将note数据保存起来
        note.setTitle(TextUtils.isEmpty(etNoteDetailTitle.getText().toString()) ?
                "无题" :
                etNoteDetailTitle.getText().toString());//保存标题

        note.setTime(DateUtils.getTime());//保存时间
        note.setKeyNum(System.currentTimeMillis());//保存标识
        note.setImageIsExist(false);//初始化图片为不存在
        note.setPinIsExist(isPin);//设置pin属性
        //遍历整个ViewGroup
        for (int i = 0; i < detailCount; i++) {
            View child = llNoteDetail.getChildAt(i);
            //如果子view为EditText或其子类
            if (child instanceof EditText) {
                //初始化一个contentText bean
                ContentText contentText = new ContentText();
                contentText.setContentText(((EditText) child).getText().toString());//保存文字内容
                //保存bean
                contentText.save();
                //加入到note的contentTextList中
                note.getContentTextList().add(contentText);
                isStore = true;
            } else if (child instanceof MyImageView) {
                //设置note中image存在
                note.setImageIsExist(true);
                //初始化ImageBean
                Image image = new Image();
                image.setImagePath(((MyImageView) child).getImagePath());
                //保存bean
                image.save();
                //添加到note的imageList中
                note.getImageList().add(image);
                isStore = true;
            }
        }
        //保存Note
        note.save();
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
            //设置图片存在
            isImageExist = true;
            addImageItem(imagePath);
            addEditTextItem();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAndCallBack();
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
        if (isShow) {
            //先删除
            DataSupport.delete(NoteNew.class, inetntNote.getId());
            //再重新保存
            saveNoteData();
        } else {
            //保存内容
            saveNoteData();
        }
        //判断是否有记录任何内容
        if (!isStore) {
            Toast.makeText(ArcNewNoteActivity.this, "您未记录任何内容...", Toast.LENGTH_SHORT).show();
        }
    }

}
