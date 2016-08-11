package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.MyUtils.DateUtils;

/*
* 包含基础的数据库设置
* @作者 admin
* @时间 2016/8/11 21:15
*
*/
public abstract class BaseNoteActivity extends AppCompatActivity {

    protected Toolbar tbNoteDetail;
    protected EditText etNoteDetail;
    protected Note note;
    protected CameraImageUtils cameraImageUtils;

    protected static final int REQUEST_IMAGE_CAPTURE=111;

    protected SpannableString mSpan1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initUI();

    //使用相机应用进行拍照
    protected void dispatchTakePictureIntent() {
//        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //在调用startActivity()之前我们先判断一下手机中是否有可以相应相机的应用
//        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
        cameraImageUtils=new CameraImageUtils();
        cameraImageUtils.dispatchTakePictureIntent(this,REQUEST_IMAGE_CAPTURE);
        cameraImageUtils.galleryAddPic(this);
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
        note.setContent(etNoteDetail.getText().toString());
        note.setImageIsExist(false);
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
  protected void displayBitmapOnText(Bitmap thumbnailBitmap) {
        if(!TextUtils.isEmpty(etNoteDetail.getText().toString())){
        mSpan1 = new SpannableString(etNoteDetail.getText().toString()+1);
        }else{
            mSpan1 = new SpannableString("1");
        }
        if(thumbnailBitmap == null)
            return;
        //获取edit开始的位置
        int start = etNoteDetail.getSelectionStart();
        //将最后一位1替换为图片

        Log.d("SKKK_____",mSpan1.toString());

        mSpan1.setSpan(new ImageSpan(thumbnailBitmap), 0, mSpan1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Log.d("SKKK_____", mSpan1.toString());

        //mSpan1.toString();
        if(etNoteDetail != null) {
            Editable et = etNoteDetail.getText();
            et.insert(start, mSpan1);

            Log.d("SKKK_____", et.toString());

            etNoteDetail.setText(et);
            etNoteDetail.setSelection(start + mSpan1.length());
        }
        etNoteDetail.setLineSpacing(10f, 1f);
    }
}
