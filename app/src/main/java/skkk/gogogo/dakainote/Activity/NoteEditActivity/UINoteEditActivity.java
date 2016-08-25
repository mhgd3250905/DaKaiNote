package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;
import skkk.gogogo.dakainote.View.NoteBoxView;
import skkk.gogogo.dakainote.View.NoteEditView;

/*
*
* @描述 包含UI初始化 以及UI中包含的基础组件的点击事件
* @作者 admin
* @时间 2016/8/11 21:26
*
*/
public class UINoteEditActivity extends BaseNoteActivity {

    private ImageSpan span;
    private SpannableString spanString;
    private int notePos;
    private Note inetntNote;
    private TextInputLayout tilTitle;
    private ArcMenuView arcMenuView;
    private Note note = new Note();
    NoteBoxView noteBoxView;
    private CoordinatorLayout llTest;
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBeforeStart();
        initUI();
        initEvent();
    }

    /*
    * @方法 所有监听事件
    *
    */
    private void initEvent() {
        //为toolbar 添加返回按钮
        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        arcMenuView.setmMenuItemClickListener(new ArcMenuView.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        if (note == null) {
                            return;
                        }
                        Log.d("SKKK_____", "PIN:  " + note.isPinIsExist());
                        if (note.isPinIsExist()) {
                            note.setPinIsExist(false);
                            Log.d("SKKK_____", "set pin false");

                        } else {
                            note.setPinIsExist(true);

//                            Note updateNote = new Note();
//                            updateNote.setPinIsExist(true);
//                            updateNote.updateAll("time=? and content=?",
//                                    note.getTime(), note.getContent());
                            Log.d("SKKK_____", "set pin true");
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        if (inetntNote != null) {
                            if (inetntNote.isPinIsExist()) {
                                noteBoxView.removePinImage();
                                inetntNote.setPinIsExist(noteBoxView.isPinIsExist());
                            } else {
                                noteBoxView.setPinImage();
                                inetntNote.setPinIsExist(noteBoxView.isPinIsExist());
                            }
                        }

                        break;
                }
            }
        });
    }

    //初始化UI
    protected void initUI() {
        setContentView(R.layout.activity_note_detail);
        noteBoxView = (NoteBoxView) findViewById(R.id.nbv_edit);
        llTest = (CoordinatorLayout) findViewById(R.id.ll_test);
        etNoteTitle = (EditText) findViewById(R.id.et_note_title);
        noteEditView = (NoteEditView) findViewById(R.id.nev_edit);
        arcMenuView = (ArcMenuView) findViewById(R.id.arc_menu_view_note);
        //标题TextInputLayout
        tilTitle = (TextInputLayout) findViewById(R.id.til_title);

        //etNoteDetail= (EditText) findViewById(R.id.et_note_detail);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);
        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.menu_note_detail_edit);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        tbNoteDetail.setOnMenuItemClickListener(onMenuItemClick);
        if (inetntNote != null) {
            imagePath = inetntNote.getImagePath();
            //设置标题
            if (!TextUtils.isEmpty(inetntNote.getTitle())) {
                tilTitle.setHintEnabled(false);
            }
            etNoteTitle.setText(inetntNote.getTitle());
            noteEditView.setText(inetntNote.getContent());
            if (!TextUtils.isEmpty(imagePath)) {
                //如果图片路径不为空那么加载图片
                spanString = new SpannableString(" ");
                //获取一个压缩过的指定大小的的bitmap并加入到SpannableString中
                span = new ImageSpan(this, CameraImageUtils.getPreciselyBitmap(imagePath, 500));
                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                noteEditView.addImageSpan(imagePath, dm.widthPixels/2);
                noteEditView.setFocusable(false);
                etNoteTitle.setFocusable(false);
            }
            if (inetntNote.isPinIsExist()) {
                noteBoxView.setPinImage();
            }
        }
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
                    if (!TextUtils.isEmpty(noteEditView.getText().toString())) {
                        //保存到数据库
                        //写入数据
                        if (inetntNote != null) {

                            note.setTitle(TextUtils.isEmpty(etNoteTitle.getText().toString()) ? "未设置标题" : etNoteTitle.getText().toString());
                            note.setTime(DateUtils.getTime());
                            note.setContent(noteEditView.getText().toString());
                            note.setImageIsExist(isImageExist);
                            note.setKeyNum(System.currentTimeMillis());
                            note.setPinIsExist(inetntNote.isPinIsExist());
                            //如果图片存在就设置路径
                            if (isImageExist) {
                                note.setImagePath(imagePath);
                                note.setStart(start);
                            }
                            Log.d("SKKK_____", note.toString());
                            note.updateAll("keynum=?", String.valueOf(inetntNote.getKeyNum()));

                        } else {
                            note.setTitle(TextUtils.isEmpty(etNoteTitle.getText().toString()) ? "未设置标题" : etNoteTitle.getText().toString());
                            note.setTime(DateUtils.getTime());
                            note.setContent(noteEditView.getText().toString());
                            note.setImageIsExist(isImageExist);
                            note.setKeyNum(System.currentTimeMillis());

                            //如果图片存在就设置路径
                            if (isImageExist) {
                                note.setImagePath(imagePath);
                                note.setStart(start);
                            }
                            if (note.isPinIsExist()) {
                                note.setPinIsExist(true);
                            }
                            Log.d("SKKK_____", note.toString());
                            mSaveData(note);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("note_form_edit", note);
                        intent.putExtra("pos", notePos);
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //设置图片存在
            isImageExist = true;
            noteEditView.addImageSpan(imagePath, dm.widthPixels/2);
            //noteEditView.setMovementMethod(ClickableMovementMethod.getInstance());
        }
    }

    /*
     * @方法 获取传递来的intent
     *
     */
    public void getDataBeforeStart() {
        inetntNote = (Note) getIntent().getSerializableExtra("note");
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (inetntNote == null) {
            return;
        }
        notePos = getIntent().getIntExtra("pos", 1);
    }

}
