package skkk.gogogo.dakainote.Utils.RecyclerViewDecoration;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by admin on 2016/8/10.
 */
/*
* 
* 描    述：判断是否有权限
* 作    者：ksheng
* 时    间：
*/
public class PermissionUtils {

    public static boolean requestPermission(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.CAMERA", "skkk.gogogo.dakainote"));
        return permission;
    }


}
