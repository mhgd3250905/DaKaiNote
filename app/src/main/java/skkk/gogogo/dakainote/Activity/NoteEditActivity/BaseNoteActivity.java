package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.View.NoteEditView;

/*
* 包含基础的数据库设置
* @作者 admin
* @时间 2016/8/11 21:15
*
*/
public abstract class BaseNoteActivity extends AppCompatActivity {

    protected Toolbar tbNoteDetail;//toolbar
    protected Note note;//笔记类
    //protected CameraImageUtils cameraImageUtils;//照片工具类
    protected static final int REQUEST_IMAGE_CAPTURE=111;//拍照请求码
    protected SpannableString mSpan1;
    protected Boolean isImageExist=false;//照片是否存在
    protected String imagePath;//照片路径
    protected int start;//图片插入的位置
    protected NoteEditView noteEditView;
    protected EditText etNoteTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initUI();

    //使用相机应用进行拍照
    protected void takePicture(Activity activity) {
        imagePath=CameraImageUtils.getImagePath();
        CameraImageUtils.dispatchTakePictureIntent(activity,imagePath,REQUEST_IMAGE_CAPTURE);
        CameraImageUtils.galleryAddPic(this);
    }


    /*
    * @desc 保存数据
    * @时间 2016/8/7 23:09
    */
    protected void mSaveData() {
        //写入数据
        note = new Note();
        note.setTitle(TextUtils.isEmpty(etNoteTitle.getText().toString())?"未设置标题":etNoteTitle.getText().toString());
        note.setTime(DateUtils.getTime());
        note.setContent(noteEditView.getText().toString());
        note.setImageIsExist(isImageExist);
        //如果图片存在就设置路径
        if(isImageExist){
            note.setImagePath(imagePath);
            note.setStart(start);
        }
        if(note.isPinIsExist()){
            note.setPinIsExist(true);
        }
        if(note.save()){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }



    /*
        * @方法 删除数据
        * @描述 删除数据库中一条数据
        *
        */
    protected void mDeleteData(){
        note.delete();
    }




}
