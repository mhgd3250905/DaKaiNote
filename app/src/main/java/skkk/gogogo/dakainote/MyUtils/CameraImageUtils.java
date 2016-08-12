package skkk.gogogo.dakainote.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

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


    public Bitmap decodeSampleBitmapFromResource(Context context, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(mCurrentPhotePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(mCurrentPhotePath, options);
    }


    /*
    * @方法 获得图片的缩放比例
    *
    */
    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        //获取原始bitmap的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;//缩放比例

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = (int) (height /2);
            final int halfWidth = (int) (width / 2);

            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d("SKKK_____", "缩放比例为" + inSampleSize);
        return inSampleSize;
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
        mCurrentPhotePath = image.getAbsolutePath();
        return image;
    }

    /*
    * @方法 打开相机并把保存到指定目录
    *
    */
    public void dispatchTakePictureIntent(Activity activity, int REQUEST_CODE) {
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
