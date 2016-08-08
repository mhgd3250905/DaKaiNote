package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.R;

public class NoteDetailShowActivity extends AppCompatActivity {

    private Toolbar tbNoteDetail;
    private EditText etNoteDetail;
    private Object dataBeforeStart;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataBeforeStart();
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_note_detail);
        etNoteDetail= (EditText) findViewById(R.id.et_note_detail);

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

        etNoteDetail.setText(note.getContent());
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_share:
                    msg += "Click edit";
                    break;
                case R.id.action_save:
                    msg += "Click share";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(NoteDetailShowActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    public void getDataBeforeStart() {
        note = (Note) getIntent().getSerializableExtra("note");
    }
}
