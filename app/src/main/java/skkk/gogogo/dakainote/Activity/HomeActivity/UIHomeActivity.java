package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import skkk.gogogo.dakainote.Activity.AuthorActivity.AuthorActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.SaveNoteActivity;
import skkk.gogogo.dakainote.Activity.SettingActivity.SettingActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.Interface.UIHomeInterface;
import skkk.gogogo.dakainote.Presenter.UIHomePresenter;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.Service.CopyService;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

/*
*
* 这里包含了所有的UI初始化
* @作者 admin
* @时间 2016/8/11 21:08
*
*/
public class UIHomeActivity extends BaseHomeActivity
        implements NavigationView.OnNavigationItemSelectedListener, UIHomeInterface {
    private NoteListFragment noteListFragment;
    private FloatingActionButton fab;
    private FrameLayout flHome;
    private int fabFlagInActivity = 1;
    private Toolbar mToolbar;
    private LinearLayout llMenuTitle;
    private final static int PHOTO_REQUEST_GALLERY = 914;
    private String imagePath;
    private boolean canBack = true;
    private boolean noNeedLock = true;
    private MyApplication mApplication;
    private UIHomePresenter mUIHomePresenter;
    private Dialog mDialog;
    private boolean mResult;
    private boolean isNight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Application实例
        mApplication = (MyApplication) getApplicationContext();
        mUIHomePresenter = new UIHomePresenter(this);

        //设置主题
        setMyTheme();
        initUI();
        initEvent();
        setService();

    }



    /*
    * @方法 设置主题
    * @参数
    * @返回值
    */
    public void setMyTheme() {
        //设置主题
        isNight=mUIHomePresenter.getThemeNight();
        if (isNight) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    /* @描述 初始化UI */
    private void initUI() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }


        setContentView(R.layout.activity_main);

        //加入默认的Fragment界面
        noteListFragment = new NoteListFragment(mUIHomePresenter.getNoteList(), mUIHomePresenter.getNoteStyle(),mApplication);

        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_home, noteListFragment).commit();

        flHome = (FrameLayout) findViewById(R.id.fl_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        llMenuTitle = (LinearLayout) navigationView.getHeaderView(0);

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
                if (fabFlagInActivity == 1) {
                    //进入到编辑页面
                    Intent intent = new Intent();
                    intent.setClass(UIHomeActivity.this, SaveNoteActivity.class);
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
    * @方法 设置Notify服务
    * @参数 null
    * @返回值 null
    */
    private void setService() {
        if (mUIHomePresenter.isServiceNeedOpen()){
            startService(new Intent(this, CopyService.class));
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
        switch (item.getItemId()) {
            /* @描述 点击切换到全部笔记展示页面 */
            case R.id.nav_list:
                mToolbar.setTitle("大开笔记");

                mResult = mUIHomePresenter.checkNavigationItem(noteListFragment,item.getItemId());
                if (mResult) {
                    Snackbar.make(flHome, "点击按钮新增笔记...", Snackbar.LENGTH_SHORT).show();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, noteListFragment)
                        .commit();
                break;

            /* @描述 点击切换到pin笔记列表 */
            case R.id.nav_pin:

                mResult = mUIHomePresenter.checkNavigationItem(noteListFragment,item.getItemId());
                if (mResult) {
                    Snackbar.make(flHome, "没有pin笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("PIN笔记");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到图片展示页面
             * 点击图片应该跳转到响应的图片Note中 */
            case R.id.nav_image:

                mResult = mUIHomePresenter.checkNavigationItem(noteListFragment, item.getItemId());
                if (mResult) {
                    Snackbar.make(flHome, "没有图片笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("图片笔记");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;

            /* @描述 点击切换到声音笔记列表 */
            case R.id.nav_voice:

                mResult = mUIHomePresenter.checkNavigationItem(noteListFragment, item.getItemId());
                if (mResult) {
                    Snackbar.make(flHome, "没有录音笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("录音笔记");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到行事历笔记列表 */
            case R.id.nav_schedule:

                mResult = mUIHomePresenter.checkNavigationItem(noteListFragment, item.getItemId());
                if (mResult) {
                    Snackbar.make(flHome, "没有行事历...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("录音笔记");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;

            /* @描述 点击切换到设置界面 */
            case R.id.nav_setting:
                startActivity(new Intent(UIHomeActivity.this, SettingActivity.class));
                break;

            case R.id.nav_author:
                /* @描述 隐藏fab */
                startActivity(new Intent(UIHomeActivity.this, AuthorActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /* @描述 在onResume周期进行是否上锁判定 */
    @Override
    protected void onResume() {
        super.onResume();

        if (isNight!=mUIHomePresenter.getThemeNight()){
            recreate();
        }

        /* @描述 如果开启上锁 */
        if (mUIHomePresenter.getLockedFlag() && noNeedLock) {
            canBack = false;
            noNeedLock = false;

            //如果是第一次点击
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final TouchDeblockingView tdvLock = new TouchDeblockingView(this);

            builder.setTitle("请输入密码");
            builder.setView(tdvLock);

            tdvLock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {
                @Override
                public boolean OnDrawFinished(List<Integer> passList) {
                    return mUIHomePresenter.deblocking(passList,
                            tdvLock, mDialog);
                }
            });

            //强制Back失效
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        return true;
                    }
                    return false;
                }
            });

            mDialog = builder.show();
        }
    }

    /*
    * @方法 变更Fab背景图片
    *       fab编辑状态和删除状态的切换
    *
    */
    public void changeFabSrc(int flag) {
        fabFlagInActivity = flag;
        if (flag == 1) {
            fab.setImageResource(R.drawable.vector_drawable_pen);
        } else {
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
        } else if (fabFlagInActivity == 2) {
            changeFabSrc(1);
            noteListFragment.hideCheckBox();
        } else {
            exitBy2Click();
        }
    }

    /*
    * @方法 双击退出函数
    *
    */
    protected static Boolean isExit = false;

    protected void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Snackbar.make(flHome, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
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


}

