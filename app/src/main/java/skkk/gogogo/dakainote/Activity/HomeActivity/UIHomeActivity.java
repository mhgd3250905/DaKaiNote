package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.BottomBarNewNoteActivity;
import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.Fragment.ImageShowFragment;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
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
    protected List<NoteNew> myNotes;
    protected FloatingActionButton fab;
    protected FrameLayout flHome;
    protected int fabFlagInActivity=1;
    protected Toolbar mToolbar;


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

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        arcMenuView = (ArcMenuView) findViewById(R.id.arc_menu_view_home);

        setSupportActionBar(mToolbar);
        //添加菜单
        mToolbar.inflateMenu(R.menu.note_style);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* @描述 设置fab进入界面时的动画 */
        ScaleAnimation scaleAnimation=new ScaleAnimation(0f,1f,0f,1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setStartOffset(200);
        fab.startAnimation(scaleAnimation);
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
                if (fabFlagInActivity == 1) {
                    //进入到编辑页面
                    Intent intent = new Intent();
                    intent.setClass(UIHomeActivity.this, BottomBarNewNoteActivity.class);
                    startActivity(intent);
                } else if (fabFlagInActivity == 2) {
                    /* @描述 如果fab标签是2说明为删除状态 */
                    //这里加入一个dialog提示是否需要判断
                    AlertDialog.Builder builderDeleteTIP =
                            new AlertDialog.Builder(UIHomeActivity.this);
                    builderDeleteTIP.setTitle("提示");
                    builderDeleteTIP.setIcon(R.drawable.item_recycle);
                    builderDeleteTIP.setMessage("点击确定删除选中笔记...");
                    builderDeleteTIP.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            noteListFragment.deleteSelectedItem();
                            changeFabSrc(1);
                            noteListFragment.hideCheckBox();
                            dialog.dismiss();
                        }
                    });
                    builderDeleteTIP.setNegativeButton("取消", null);
                    AlertDialog dialogDeleteTIP = builderDeleteTIP.create();
                    dialogDeleteTIP.show();

                }
            }
        });
    }


    /*
     * @方法 添加菜单
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_style, menu);
        return true;
    }

    /*
     * @方法 添加菜单点击事件
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //获取菜单item_id
        int id = item.getItemId();
        //根据菜单判断
        switch (id){
            case R.id.menu_tb_notelist_linear:
                noteListFragment.setLayoutFlag(0);
                sPref.edit().putInt("note_style",0).commit();
                break;
            case R.id.menu_tb_notelist_stagger:
                noteListFragment.setLayoutFlag(1);
                sPref.edit().putInt("note_style",1).commit();
                break;
            case R.id.menu_tb_notelist_grid:
                noteListFragment.setLayoutFlag(2);
                sPref.edit().putInt("note_style",2).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
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
//            //获取当前fragment
//            NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager().
//                    findFragmentById(R.id.fl_home);
//            noteListFragment.smoothScrollToTop();
            Snackbar.make(flHome, "再按一次退出程序",Snackbar.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            /* @描述 点击切换到全部笔记展示页面 */
            case R.id.nav_list:
                myNotes = SQLUtils.getNoteList();
                if (myNotes.size()==0){
                    Snackbar.make(flHome,"点击按钮新增笔记...",Snackbar.LENGTH_SHORT).show();
                }else{
                    noteListFragment.updateAll(myNotes);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, noteListFragment)
                        .commit();
                break;
            /* @描述 点击切换到pin笔记列表 */
            case R.id.nav_pin:
                mToolbar.setTitle("PIN笔记");
                myNotes = SQLUtils.getPinNoteList();
                if (myNotes.size()==0){
                    Snackbar.make(flHome,"没有pin笔记...",Snackbar.LENGTH_SHORT).show();
                }else{
                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到图片展示页面
             * 点击图片应该跳转到响应的图片Note中 */
            case R.id.nav_image:
                mToolbar.setTitle("图片笔记");
                myNotes = SQLUtils.getImageNoteList();
                if (myNotes.size()==0){
                    Snackbar.make(flHome,"没有图片笔记...",Snackbar.LENGTH_SHORT).show();
                }else{
                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;

            /* @描述 点击切换到声音笔记列表 */
            case R.id.nav_voice:
                mToolbar.setTitle("录音笔记");
                myNotes = SQLUtils.getVoiceNoteList();
                if (myNotes.size()==0){
                    Snackbar.make(flHome,"没有录音笔记...",Snackbar.LENGTH_SHORT).show();
                }else{
                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到声音笔记列表 */
            case R.id.nav_schedule:
                mToolbar.setTitle("录音笔记");
                myNotes = SQLUtils.getScheduleNoteList();
                if (myNotes.size()==0){
                    Snackbar.make(flHome,"没有行事历...",Snackbar.LENGTH_SHORT).show();
                }else{
                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;

            case R.id.nav_setting:

                break;
            case R.id.nav_author:

                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /* @描述 打开展示图片线程 */
    protected void startShowImageThread(){
        Thread showImageThread=new Thread(showImageRunnable);
        showImageThread.start();

    }

    /* @描述 展示图片异步加载 */
    private Runnable showImageRunnable=new Runnable() {
        @Override
        public void run() {
            List<Image> all = DataSupport.findAll(Image.class);
            if (all.size()==0){
                Snackbar.make(flHome,"没有图片...",Snackbar.LENGTH_SHORT).show();
                return;
            }
            final List<BitmapDrawable> allBitmapDrawable=new ArrayList<BitmapDrawable>();
            for (int i = 0; i < all.size(); i++) {
                allBitmapDrawable.add(
                        CameraImageUtils
                                .getImageDrawable(UIHomeActivity.this, all.get(i).getImagePath(), 360));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageShowFragment imageShowFragment=new ImageShowFragment(allBitmapDrawable);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, imageShowFragment)
                            .commit();
                }
            });
        }
    };





}

