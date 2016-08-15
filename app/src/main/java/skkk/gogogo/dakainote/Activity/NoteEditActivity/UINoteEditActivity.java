package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.NoteEditView;

/*
*
* @描述 包含UI初始化 以及UI中包含的基础组件的点击事件
* @作者 admin
* @时间 2016/8/11 21:26
*
*/
public class UINoteEditActivity extends BaseNoteActivity {


    private int multiple;
    private ImageSpan span;
    private SpannableString spanString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }
    //初始化UI
    protected void initUI() {

        setContentView(R.layout.activity_note_detail);

        noteEditView= (NoteEditView) findViewById(R.id.nev_edit);

        //etNoteDetail= (EditText) findViewById(R.id.et_note_detail);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);
        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.menu_note_detail_edit);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        tbNoteDetail.setOnMenuItemClickListener(onMenuItemClick);
        //为toolbar 添加返回按钮
        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*
    * @方法 toolBar的右侧菜单的点击事件
    * @描述 toolBar的右侧菜单的点击事件
    *
    */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_image:
                    //点击调用相机添加图片
                    takePicture(UINoteEditActivity.this);
                    break;
                case R.id.action_save:
                    //这里在关闭的时候对应前面的startActivityForResult()
                    // 返回一个note数据
                    if(!TextUtils.isEmpty(noteEditView.getText().toString())){
                        //保存到数据库
                        mSaveData();
                        Intent intent=new Intent();
                        intent.putExtra("note_form_edit",note);
                        UINoteEditActivity.this.setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
            return true;
        }
    };

    /*
     *onActivityResult
     *获取返回数据
     *这里是返回相机缩略图
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____", "requestCode:  " + requestCode);
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK){

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            //设置图片存在
            isImageExist=true;

            if(!TextUtils.isEmpty(noteEditView.getText())){
                noteEditView.append("\n");
            }

            spanString = new SpannableString(" ");

            //获取一个压缩过的指定大小的的bitmap并加入到SpannableString中

            span = new ImageSpan(this,CameraImageUtils.getPreciselyBitmap(imagePath));

            spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            noteEditView.addImageSpan(spanString);

            //displayBitmapOnText(imageBitmap,start);
        }
    }

}
