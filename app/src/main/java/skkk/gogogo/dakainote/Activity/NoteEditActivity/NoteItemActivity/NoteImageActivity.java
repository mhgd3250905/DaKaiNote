package skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import skkk.gogogo.dakainote.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class NoteImageActivity extends AppCompatActivity{
    protected ImageView ivNoteItemImage;
    protected String image_click_path;
    protected Intent intentGet;
    protected Toolbar tbNoteNewDetailImage;
    private long image_notekey;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();
        initData();
        initEvent();
    }


    /*
    * @方法 初始化之前获取需要的数据
    *
    */
    private void beforeStart() {
        intentGet = getIntent();
        image_click_path = intentGet.getStringExtra("image_click");
        image_notekey = intentGet.getLongExtra("image_notekey",0);
    }

    /*
    * @方法 初始化UI
    *
    */
    private void initUI() {
        setContentView(R.layout.activity_note_image);
        ivNoteItemImage= (ImageView) findViewById(R.id.iv_note_item_image);
        tbNoteNewDetailImage= (Toolbar) findViewById(R.id.tb_note_new_detial_image);
        setSupportActionBar(tbNoteNewDetailImage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    * @方法 载入数据
    *
    */
    private void initData() {
        Bitmap bitmap= BitmapFactory.decodeFile(image_click_path);
//        Uri uri = Uri.parse("file://" + image_click_path);
//        LogUtils.Log("file://" + image_click_path);
        ivNoteItemImage.setImageBitmap(bitmap);
        mAttacher = new PhotoViewAttacher(ivNoteItemImage);

    }


    /*
    * @方法 初始化监听事件
    *
    */
    private void initEvent() {
        //为toolbar 添加返回按钮
        tbNoteNewDetailImage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*
     * @方法 添加菜单
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_detail_menu, menu);
        return true;
    }

    /*
     * @方法 添加菜单点击事件
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //获取菜单item_id
        int id = item.getItemId();
        //根据菜单判断
        switch (id){
            case R.id.menu_tb_image_detail_delete:
                /* @描述 这里取消了删除源文件的行为 */
                //删除文件
//                File deleteFile=new File(image_click_path);
//                deleteFile.delete();
                Intent resultIntent=new Intent();
                resultIntent.putExtra("image_detail_path",image_click_path);
                resultIntent.putExtra("image_detail_keynote",image_notekey);
                this.setResult(RESULT_OK,resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
