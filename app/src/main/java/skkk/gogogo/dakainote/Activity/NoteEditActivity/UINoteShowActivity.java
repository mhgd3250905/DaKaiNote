package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.NoteEditView;


/*
* 
* 描    述：包含所有与显示note内容相关的UI的初始化
* 作    者：ksheng
* 时    间：2016/8/11$ 21:31$.
*/
public class UINoteShowActivity  extends BaseNoteActivity{

    private SpannableString spanString;
    private ImageSpan span;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBeforeStart();
        initUI();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_note_detail);
        noteEditView = (NoteEditView) findViewById(R.id.nev_edit);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);
        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.menu_note_detail_show);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        tbNoteDetail.setOnMenuItemClickListener(onMenuItemClick);
        //为toolbar 添加返回按钮
        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

            imagePath=note.getImagePath();
//            Bitmap showImageBitmap= CameraImageUtils.decodeSampleBitmapFromResource(
//                    UINoteShowActivity.this, imagePath, dm.heightPixels - 100, dm.widthPixels - 180);
//            start=note.getStart();
            //displayBitmapOnText(showImageBitmap,start);
        noteEditView.setText(note.getContent());

        if(!TextUtils.isEmpty(imagePath)){

            spanString = new SpannableString(" ");

            //获取一个压缩过的指定大小的的bitmap并加入到SpannableString中

            span = new ImageSpan(this,CameraImageUtils.getPreciselyBitmap(imagePath));

            spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            noteEditView.addImageSpan(spanString);

            noteEditView.setFocusable(false);
        }
        }

    /*
    * @方法 toolbar菜单点击事件
    *
    */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_delete:

                    break;
                case R.id.action_share:

                    break;
            }
            return true;
        }
    };


    /*
    * @方法 获取传递来的intent
    *
    */
    public void getDataBeforeStart() {
        note = (Note) getIntent().getSerializableExtra("note");
    }
}
