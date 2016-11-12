package skkk.gogogo.dakainote.Activity.ShareActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;

public class ShareActivity extends AppCompatActivity {
    private ImageView ivShare;
    private Toolbar tbShare;
    private Button btnShare;
    private String mContent;
    private Bitmap shareBitmap;
    private int mLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
    }

    /* @描述 获得传入data */
    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mLine = bundle.getInt("line");
        mContent = bundle.getString("content");
    }

    /* @描述 UI初始化 */
    private void initUI() {
        setContentView(R.layout.activity_share);
        tbShare = (Toolbar) findViewById(R.id.tb_share);
        setSupportActionBar(tbShare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbShare.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivShare = (ImageView) findViewById(R.id.iv_share);
        btnShare= (Button) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(ShareActivity.this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                /* @描述 分享图片 */
                oks.setImagePath(CameraImageUtils.saveBitmapAndReturnPath(ShareActivity.this,shareBitmap));
                // 启动分享GUI
                oks.show(ShareActivity.this);
            }
        });
        shareBitmap=drawShareBitmap();
        ivShare.setImageBitmap(shareBitmap);
    }

    /* @描述 绘制分享图片 */
    private Bitmap drawShareBitmap() {
        int height=0;
        int textHeight=0;
        TextPaint textPaint = new TextPaint();
        textPaint.setARGB(255, 81, 80, 80);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        StaticLayout currentLayout = new StaticLayout(mContent, textPaint, 700,
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        height=currentLayout.getHeight()+1000;
        textHeight=currentLayout.getHeight()/10;
        Bitmap bitmapBG =  CameraImageUtils.resizeImage(
                BitmapFactory.decodeResource(getResources(), R.drawable.share_paper)
                        .copy(Bitmap.Config.ARGB_8888, true),800,height);
        Canvas canvas = new Canvas(bitmapBG);
        canvas.drawText("分享来自白开水笔记",490,height-40-textHeight,textPaint);
        canvas.translate(100, 150+textHeight);
        currentLayout.draw(canvas);
        return bitmapBG;
    }

}
