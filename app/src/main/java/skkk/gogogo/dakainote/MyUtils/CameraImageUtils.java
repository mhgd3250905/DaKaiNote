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

    /*
    * @方法获得image绝对路径
    *
    */
    public static String getImagePath() {
        String mCurrentImagePath=null;
        try {
            mCurrentImagePath = createImageFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mCurrentImagePath;
    }

    /*
     * @方法 返回保存图片的位置文件
     *
     */
    public static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }


    //返回按比例缩放之后的bitmap
    public static Bitmap decodeSampleBitmapFromResource(Context context, String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }


    /*
    * @方法 获得图片的缩放比例
    *
    */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        //获取原始bitmap的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        //Log.d("SKKK_____", "原始宽高为" + width + "x" + height);

        int inSampleSize = 1;//缩放比例

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = (int) (height / 2);
            final int halfWidth = (int) (width / 2);

            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        //Log.d("SKKK_____", "缩放比例为" + inSampleSize);
        return inSampleSize;
    }


    /*
    * @方法 打开相机并把保存到指定目录
    *
    */
    public static void dispatchTakePictureIntent(Activity activity,String path, int REQUEST_CODE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photeFile = null;
            photeFile = new File(path);
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
    public static void galleryAddPic(Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(getImagePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
