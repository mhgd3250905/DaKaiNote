package skkk.gogogo.dakainote.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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




    //返回按比例缩放之后的bitmap
    public static Bitmap decodeSampleBitmapFromResource(Context context, String path, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();

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
    * @方法 返回缩略图
    *
    */
    public static Bitmap getPreciselyBitmap(String imagePath,int reqWidth){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int imageWidth=options.outWidth;
        int reqHeight=options.outHeight/((imageWidth/(reqWidth-20))<1?1:(imageWidth/(reqWidth-20)));

        Log.d("SKKK_____", "图片宽为" + reqWidth + "图片高为" + reqHeight);
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath),
                reqWidth - 20,
                reqHeight,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }


    /*
    * @方法 图片质量压缩办法
    *
    */
    public static Bitmap compressImage(Context context,String imagePath) {
        Bitmap image=getPreciselyBitmap(imagePath,300);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 10;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while ( baos.toByteArray().length / 1024>100) {
            baos.reset();//重置baos即清空baos
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            //options -= 10;
            if (options==0){
                Toast.makeText(context, "000", Toast.LENGTH_SHORT).show();
            }
        }

        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /*
      * @方法获得image绝对路径
      *
      */
    public static String getImagePath() throws IOException {
        String mCurrentImagePath=null;
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentImagePath=image.getAbsolutePath();
        image.delete();
        return mCurrentImagePath;
    }


//    /*
//     * @方法 返回保存图片的位置文件
//     *
//     */
//    public static File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
//        return image;
//    }


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
    public static void galleryAddPic(Context context) throws IOException {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(getImagePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

}
