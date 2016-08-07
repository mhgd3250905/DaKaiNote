package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import skkk.gogogo.dakainote.R;

public class NoteDetailActivity extends AppCompatActivity {

    private Toolbar tbNoteDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_note_detail);
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
}
