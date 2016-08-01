package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.R;

public class HomeActivity extends AppCompatActivity {

    private NoteListFragment noteListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_home);
        //加入默认的Fragment界面
        addDefaultFragment();
    }

    /*
    * @desc 加入默认的Fragment界面
    * @时间 2016/8/1 21:44
    */
    private void addDefaultFragment() {
        noteListFragment = new NoteListFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_home, noteListFragment).commit();
    }
}
