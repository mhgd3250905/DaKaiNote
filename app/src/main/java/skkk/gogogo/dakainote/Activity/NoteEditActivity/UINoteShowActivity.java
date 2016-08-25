package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.litepal.crud.DataSupport;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;
import skkk.gogogo.dakainote.View.NoteEditView;


/*
* 
* 描    述：包含所有与显示note内容相关的UI的初始化
* 作    者：ksheng
* 时    间：2016/8/11$ 21:31$.
*/
public class UINoteShowActivity extends BaseNoteActivity {

    private SpannableString spanString;
    private ImageSpan span;
    private TextInputLayout tilTitle;
    private TextInputLayout tilContent;
    private ArcMenuView arcMenuView;
    private int notePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBeforeStart();
        initUI();
        initEvent();
    }

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
                        Log.d("SKKK_____","PIN:  "+note.isPinIsExist());
                        if (note.isPinIsExist()) {
                            note.setPinIsExist(false);
                            ContentValues values=new ContentValues();
                            values.put("pinisexist",false);
                            DataSupport.updateAll(Note.class,values,
                                    "time=? and title=?",
                                    note.getTime(),
                                    note.getTitle());
                            Log.d("SKKK_____", "set pin false");

                        } else {
                            note.setPinIsExist(true);
                            Note updateNote=new Note();
                            updateNote.setPinIsExist(true);
                            updateNote.updateAll("time=? and content=?",
                                    note.getTime(), note.getContent());
                            Log.d("SKKK_____", "set pin true");
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_note_detail);
        //标题et
        etNoteTitle = (EditText) findViewById(R.id.et_note_title);
        //标题TextInputLayout
        tilTitle = (TextInputLayout) findViewById(R.id.til_title);
        //内容et
        noteEditView = (NoteEditView) findViewById(R.id.nev_edit);

        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);

        arcMenuView = (ArcMenuView) findViewById(R.id.arc_menu_view_note);

        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.menu_note_detail_show);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        tbNoteDetail.setOnMenuItemClickListener(onMenuItemClick);



        imagePath = note.getImagePath();
        //设置标题
        if (!TextUtils.isEmpty(note.getTitle())) {
            tilTitle.setHintEnabled(false);
        }
        etNoteTitle.setFocusable(false);
        etNoteTitle.setText(note.getTitle());

        noteEditView.setFocusable(false);
        noteEditView.setText(note.getContent());

        if (!TextUtils.isEmpty(imagePath)) {
            //如果图片路径不为空那么加载图片
            spanString = new SpannableString(" ");
            //获取一个压缩过的指定大小的的bitmap并加入到SpannableString中
            span = new ImageSpan(this, CameraImageUtils.getPreciselyBitmap(imagePath, 500));
            spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //noteEditView.addImageSpan(spanString);
            noteEditView.setFocusable(false);
            etNoteTitle.setFocusable(false);
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
        notePos = getIntent().getIntExtra("pos",1);
    }
}
