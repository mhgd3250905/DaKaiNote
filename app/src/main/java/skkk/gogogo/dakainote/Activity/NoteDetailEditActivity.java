package skkk.gogogo.dakainote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.R;

public class NoteDetailEditActivity extends AppCompatActivity {

    private Toolbar tbNoteDetail;
    private EditText etNoteDetail;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();
    }

    /*
    * 写在初始化UI之前
    * */
    private void beforeStart() {

    }

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

    @Override
    protected void onPause() {
        super.onPause();
        //mSaveData();

//        //数据是使用Intent返回
//        Intent intent = new Intent();
//        //把返回数据存入Intent
//        intent.putExtra("note", note);
//        //设置返回数据
//        NoteDetailEditActivity.this.setResult(RESULT_OK, intent);
//
//        Log.d("SKKK_____", "保存数据条目成功");
    }



    /*
    * @desc 保存数据
    * @时间 2016/8/7 23:09
    */
    private void mSaveData() {

        if(TextUtils.isEmpty(etNoteDetail.getText().toString())){
            return;
        }
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

    //toolBar的右侧菜单的点击事件
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_share:
                    msg += "Click edit";
                    break;
                case R.id.action_save:

                //这里在关闭的时候对应前面的startActivityForResult()
                // 返回一个note数据
                    if(!TextUtils.isEmpty(etNoteDetail.getText().toString())){
                        mSaveData();
                        Intent intent=new Intent();
                        intent.putExtra("note_form_edit",note);
                        NoteDetailEditActivity.this.setResult(RESULT_OK, intent);
                        finish();
                    }

                    break;
            }
            return true;
        }
    };

}
