package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.R;

public class BaseNoteEditActivity extends AppCompatActivity {

    private Toolbar tbNoteDetail;
    private EditText etNoteDetail;
    private Note note;

    private SpannableString mSpan1;

    private static final int REQUEST_IMAGE_CAPTURE=1;
    //？？？
    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            int id = Integer.parseInt(source);
            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    //初始化UI
    private void initUI() {
        setContentView(R.layout.activity_note_detail);
        etNoteDetail= (EditText) findViewById(R.id.et_note_detail);
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


    //toolBar的右侧菜单的点击事件
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_image:
                    //点击调用相机添加图片
                    dispatchTakePictureIntent();

                    break;
                case R.id.action_save:
                //这里在关闭的时候对应前面的startActivityForResult()
                // 返回一个note数据
                    if(!TextUtils.isEmpty(etNoteDetail.getText().toString())){
                        //保存到数据库
                        mSaveData();
                        Intent intent=new Intent();
                        intent.putExtra("note_form_edit",note);
                        BaseNoteEditActivity.this.setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
            return true;
        }
    };

    //使用相机应用进行拍照
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //在调用startActivity()之前我们先判断一下手机中是否有可以相应相机的应用
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }


    /*
    * @desc 保存数据
    * @时间 2016/8/7 23:09
    */
    private void mSaveData() {
        //写入数据
        note = new Note();
        note.setDate("hello");
        note.setTime(skkk.gogogo.dakainote.Utils.RecyclerViewDecoration.DateUtils.getTime());
        note.setContent(etNoteDetail.getText().toString());
        note.setImageIsExist(false);
        note.setStar(false);
        if(note.save()){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }


    /*
     *onActivityResult
     *获取返回数据
     *这里是返回相机缩略图
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap imageBitmap= (Bitmap) bundle.get("data");
            displayBitmapOnText(imageBitmap);
        }
    }

  /*

* this is add bitmap on edit text

*/



    private void displayBitmapOnText(Bitmap thumbnailBitmap) {
        if(!TextUtils.isEmpty(etNoteDetail.getText().toString())){
        mSpan1 = new SpannableString(etNoteDetail.getText().toString()+1);
        }else{
            mSpan1 = new SpannableString("1");
        }
        if(thumbnailBitmap == null)
            return;
        //获取edit开始的位置
        int start = etNoteDetail.getSelectionStart();
        //将最后一位1替换为图片

        Log.d("SKKK_____",mSpan1.toString());

        mSpan1.setSpan(new ImageSpan(thumbnailBitmap), 0, mSpan1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Log.d("SKKK_____", mSpan1.toString());

        //mSpan1.toString();
        if(etNoteDetail != null) {
            Editable et = etNoteDetail.getText();
            et.insert(start, mSpan1);

            Log.d("SKKK_____", et.toString());

            etNoteDetail.setText(et);
            etNoteDetail.setSelection(start + mSpan1.length());
        }
        etNoteDetail.setLineSpacing(10f, 1f);
    }
}
