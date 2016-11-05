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

import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;

public class ShareActivity extends AppCompatActivity {
    private ImageView ivShare;
    private Toolbar tbShare;
    private Button btnShare;
    private String mContent;

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
        //String title = bundle.getString("title");
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

            }
        });
        ivShare.setImageBitmap(drawShareBitmap());
    }

    /* @描述 绘制分享图片 */
    private Bitmap drawShareBitmap() {
        Bitmap bitmapBG = CameraImageUtils.resizeImage(
                BitmapFactory.decodeResource(getResources(), R.drawable.share_bg)
                        .copy(Bitmap.Config.ARGB_8888, true),800,600);
        Canvas canvas = new Canvas(bitmapBG);
        TextPaint textPaint = new TextPaint();
        textPaint.setARGB(255, 81, 80, 80);
        textPaint.setTextSize(30);
        StaticLayout currentLayout = new StaticLayout(mContent, textPaint, 500,
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        canvas.translate(150, 150);
        currentLayout.draw(canvas);
        return bitmapBG;
    }
}
