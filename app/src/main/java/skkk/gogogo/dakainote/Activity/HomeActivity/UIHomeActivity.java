package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.ArcNewNoteActivity;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;

/*
*
* 这里包含了所有的UI初始化
* @作者 admin
* @时间 2016/8/11 21:08
*
*/
public class UIHomeActivity extends BaseHomeActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected NoteListFragment noteListFragment;
    protected static final int REQUEST_CODE_1=1;
    protected static final int REQUEST_CODE_2=2;
    private List<NoteNew> myNotes;
    private NoteListFragment noteListFragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
    }

    private void initData() {
        myNotes = new ArrayList<NoteNew>();
        //获取当前fragment
        noteListFragment = (NoteListFragment) getSupportFragmentManager().
                findFragmentById(R.id.fl_home);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        arcMenuView= (ArcMenuView) findViewById(R.id.arc_menu_view_home);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置卫星菜单点击事件
        arcMenuView.setmMenuItemClickListener(new ArcMenuView.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos) {
                    case 1:
                        //进入到编辑页面
                        Intent intent = new Intent();
                        intent.setClass(UIHomeActivity.this, ArcNewNoteActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        myNotes=SQLUtils.getImageNoteList();
                        noteListFragment.updateAll(myNotes);
                        break;
                    case 3:
                        myNotes=SQLUtils.getPinNoteList();
                        noteListFragment.updateAll(myNotes);
                        break;
                    case 4:
                        myNotes=SQLUtils.getVoiceNoteList();
                        noteListFragment.updateAll(myNotes);
                        break;
                    case 5:
                        myNotes=SQLUtils.getNoteList();
                        noteListFragment.updateAll(myNotes);
                        break;
            }
            }
        });

    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitBy2Click();
        }
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            //获取当前fragment
            NoteListFragment noteListFragment= (NoteListFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fl_home);
            noteListFragment.smoothScrollToTop();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
