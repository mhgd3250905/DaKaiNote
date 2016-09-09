package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
    private List<NoteNew> myNotes;
    private FloatingActionButton fab;
    protected FrameLayout flHome;
    private int fabFlagInActivity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
        initEvent();
    }

    private void initData() {
        myNotes = new ArrayList<NoteNew>();
        //获取当前fragment
        noteListFragment = (NoteListFragment) getSupportFragmentManager().
                findFragmentById(R.id.fl_home);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);

        flHome= (FrameLayout) findViewById(R.id.fl_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        arcMenuView = (ArcMenuView) findViewById(R.id.arc_menu_view_home);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /*
    * @方法 初始化监听事件
    *
    */
    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* @描述 如果fab标签是1说明为编辑状态 */
                if (fabFlagInActivity==1) {
                    //进入到编辑页面
                    Intent intent = new Intent();
                    intent.setClass(UIHomeActivity.this, ArcNewNoteActivity.class);
                    startActivity(intent);
                }else if (fabFlagInActivity==2){
                    /* @描述 如果fab标签是2说明为删除状态 */

                    noteListFragment.deleteSelectedItem();
                    changeFabSrc(1);
                    noteListFragment.hideCheckBox();
                }
            }
        });
    }




    /*
    * @方法 变更Fab背景图片
    *       fab编辑状态和删除状态的切换
    *
    */
    public void changeFabSrc(int flag){
        fabFlagInActivity=flag;
        if (flag==1){
            fab.setImageResource(R.drawable.vector_drawable_pen);
        }else{
            fab.setImageResource(R.drawable.vector_drawable_delete);
        }
    }


    /*
    * @方法 重写返回方法双击退出
    *
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fabFlagInActivity==2){
            changeFabSrc(1);
            noteListFragment.hideCheckBox();
        }else{
            exitBy2Click();
        }
    }

    /*
    * @方法 双击退出函数
    *
    */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            //获取当前fragment
            NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager().
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


    /*
    * @方法 侧滑菜单点击事件
    *
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        myNotes = SQLUtils.getNoteList();
        if (myNotes.size()==0){
            Snackbar.make(flHome,"没有笔记...",Snackbar.LENGTH_SHORT).show();
        }else{
            noteListFragment.updateAll(myNotes);
        }

        if (id == R.id.nav_list) {
            // Handle the camera action
        } else if (id == R.id.nav_pin) {
            myNotes = SQLUtils.getPinNoteList();
            if (myNotes.size()==0){
                Snackbar.make(flHome,"没有pin笔记...",Snackbar.LENGTH_SHORT).show();
            }else {
                noteListFragment.updateAll(myNotes);
            }
        } else if (id == R.id.nav_image) {
            myNotes = SQLUtils.getImageNoteList();
            if (myNotes.size()==0){
                Snackbar.make(flHome,"没有图片笔记...",Snackbar.LENGTH_SHORT).show();
            }else {
                noteListFragment.updateAll(myNotes);
            }
        } else if (id == R.id.nav_voice) {
            myNotes = SQLUtils.getVoiceNoteList();
            if (myNotes.size()==0){
                Snackbar.make(flHome,"没有录音笔记...",Snackbar.LENGTH_SHORT).show();
            }else {
                noteListFragment.updateAll(myNotes);
            }
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_author) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

