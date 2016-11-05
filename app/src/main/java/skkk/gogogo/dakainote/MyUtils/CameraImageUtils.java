package skkk.gogogo.dakainote.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public static Bitmap getPreciselyBitmap(String imagePath, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int imageWidth = options.outWidth;
        int reqHeight = options.outHeight / ((imageWidth / (reqWidth - 20)) < 1 ? 1 : (imageWidth / (reqWidth - 20)));

        Log.d("SKKK_____", "图片宽为" + reqWidth + "图片高为" + reqHeight);
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath),
                reqWidth - 20,
                reqHeight,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }



    public static BitmapDrawable getImageDrawable(Context context,String imagePath,int reqWidth){
        Bitmap bitmap=getPreciselyBitmap(imagePath,reqWidth);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(context.getResources(),bitmap);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setDither(true);
        bitmapDrawable.setFilterBitmap(true);
        return bitmapDrawable;
    }

    /*
    * @方法 图片质量压缩办法
    *
    */
    public static Bitmap compressImage(Context context, String imagePath) {
        Bitmap image = getPreciselyBitmap(imagePath, 300);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 10;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();//重置baos即清空baos
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            //options -= 10;
            if (options == 0) {
                Toast.makeText(context, "000", Toast.LENGTH_SHORT).show();
            }
        }

        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }


    /*
      * @方法获得image绝对路径
      *
      */
    public static String getImagePath() throws IOException {
        String mCurrentImagePath = null;
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentImagePath = image.getAbsolutePath();
        image.delete();
        return mCurrentImagePath;
    }


    /*
    * @方法 打开相机并把保存到指定目录
    *
    */
    public static void dispatchTakePictureIntent(Activity activity, String path, int REQUEST_CODE) {
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

    /* @描述 保存图片然后返回路径 */
    public static String saveBitmapAndReturnPath(Context context,Bitmap bitmap) {
        File f = new File(context.getFilesDir(),DateUtils.getTime()+"shareBKS");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
            out.flush();
            out.close();
            String sharePath=f.getAbsolutePath();
            return sharePath;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
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


    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    public static String getAbsoluteImagePath(Activity context,Uri uri) {
        String imagePath = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, proj, // Which
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }
        return imagePath;
    }


        /**
         * 图片转成string
         *
         * @param bitmap
         * @return
         */
        public static String convertIconToString(Bitmap bitmap)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] appicon = baos.toByteArray();// 转为byte数组
            return Base64.encodeToString(appicon, Base64.DEFAULT);

        }

        /**
         * string转成bitmap
         *
         * @param st
         */
        public static Bitmap convertStringToIcon(String st)
        {
            // OutputStream out;
            Bitmap bitmap = null;
            try
            {
                // out = new FileOutputStream("/sdcard/aa.jpg");
                byte[] bitmapArray;
                bitmapArray = Base64.decode(st, Base64.DEFAULT);
                bitmap =
                        BitmapFactory.decodeByteArray(bitmapArray, 0,
                                bitmapArray.length);
                // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                return bitmap;
            }
            catch (Exception e)
            {
                return null;
            }
        }



}
