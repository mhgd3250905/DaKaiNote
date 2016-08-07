package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.R;

public class NoteDetailActivity extends AppCompatActivity {

    private Toolbar tbNoteDetail;
    private EditText etNoteDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_note_detail);
        etNoteDetail= (EditText) findViewById(R.id.et_note_detail);

        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);
        setSupportActionBar(tbNoteDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        mSaveData();
    }

    /*
    * @desc 保存数据
    * @时间 2016/8/7 23:09
    */
    private void mSaveData() {
        if(TextUtils.isEmpty(etNoteDetail.getText())){
            return;
        }
        //写入数据
        Note note=new Note();
        note.setDate("未设置");
        note.setTime(skkk.gogogo.dakainote.Utils.RecyclerViewDecoration.DateUtils.getTime());
        note.setContent("微博密码");
        note.setImageIsExist(false);
        note.setStar(false);
        if(note.save()){
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }


}
