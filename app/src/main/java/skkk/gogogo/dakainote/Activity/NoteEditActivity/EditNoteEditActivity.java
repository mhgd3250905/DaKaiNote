package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by admin on 2016/8/11.
 */
/*
* 
* 描    述：包含所有与EditView相关的逻辑操作
* 作    者：ksheng
* 时    间：2016/8/11$ 21:28$.
*/
public class EditNoteEditActivity extends UINoteEditActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     *onActivityResult
     *获取返回数据
     *这里是返回相机缩略图
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____","requestCode:  "+requestCode);
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap imageBitmap= cameraImageUtils.getStorageBitmap();
            //在editview中插入bitmap
            displayBitmapOnText(imageBitmap);
        }
    }
}
