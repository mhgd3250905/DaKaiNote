package skkk.gogogo.dakainote.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePalApplication;

/**
 * Created by admin on 2016/8/7.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
