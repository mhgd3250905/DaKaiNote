package skkk.gogogo.dakainote.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/8/11.
 */
/*
* 
* 描    述：用于处理从相机获取的image
* 作    者：ksheng
* 时    间：2016/8/11$ 22:48$.
*/
public class CameraImageUtils {

    String mCurrentPhotePath;


    public Bitmap getStorageBitmap(){
        Bitmap storageBitmap= BitmapFactory.decodeFile(mCurrentPhotePath);
        return storageBitmap;
    }

    /*
    * @方法 返回保存图片的位置文件
    *
    */
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotePath = "file:" + image.getAbsolutePath();
        return image;
    }

    /*
    * @方法 打开相机并把保存到指定目录
    *
    */
    public void dispatchTakePictureIntent(Activity activity,int REQUEST_CODE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photeFile = null;
            try {
                photeFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photeFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photeFile));
                activity.startActivityForResult(takePictureIntent, REQUEST_CODE);
            }
        }
    }

    /*
    * @方法 将照片添加到相机之中
    *       触发系统MediaScanner
    */
    public void galleryAddPic(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
