package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.os.Bundle;

import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.R;

/*
*
* 这里包含了加载第一个fragment以及activity回调
* @作者 admin
* @时间 2016/8/11 21:07
*
*/
public class FragmentUIHomeActivity extends UIHomeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
