package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
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
    NoteEditView noteEditView;


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
        note.setDate("hello");
        note.setTime(DateUtils.getTime());
        note.setContent(noteEditView.getText().toString());
        note.setImageIsExist(isImageExist);
        //如果图片存在就设置路径
        if(isImageExist){
            note.setImagePath(imagePath);
            note.setStart(start);
        }
        note.setStar(false);
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



  /*
  *
  * 在EditView中插入图片
  *
  * */
  /*protected void displayBitmapOnText(Bitmap thumbnailBitmap,int mStart) {
        if(!TextUtils.isEmpty(noteEditView.getText().toString())){
        mSpan1 = new SpannableString(noteEditView.getText().toString()+1);
        }else{
            mSpan1 = new SpannableString("1");
        }
        if(thumbnailBitmap == null)
            return;

        //将最后一位1替换为图片

        //Log.d("SKKK_____",mSpan1.toString());

        mSpan1.setSpan(new ImageSpan(thumbnailBitmap), 0, mSpan1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Log.d("SKKK_____", mSpan1.toString());

        if(noteEditView != null) {
            Editable et = noteEditView.getText();
            et.insert(mStart, mSpan1);

            //Log.d("SKKK_____", et.toString());

            noteEditView.setText(et);
            noteEditView.setSelection(mStart + mSpan1.length());
        }

      noteEditView.setLineSpacing(10f, 1f);
    }*/
}
