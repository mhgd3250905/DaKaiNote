package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.BottomBarNewNoteActivity;
import skkk.gogogo.dakainote.Bean.Person;
import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.Fragment.ImageShowFragment;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.Fragment.SettingFragment;
import skkk.gogogo.dakainote.MyUtils.CameraImageUtils;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

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
    protected int fabFlagInActivity = 1;
    protected Toolbar mToolbar;
    protected LinearLayout llMenuTitle;
    protected final static int PHOTO_REQUEST_GALLERY = 914;
    protected String imagePath;
    protected boolean canBack=true;
    protected boolean noNeedLock=true;



    protected Handler homeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 激活系统图库，选择一张图片
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                    startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                    break;
            }
        }
    };
    private SettingFragment mSettingFragment;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (sPref.getBoolean("night",false)){
            setTheme(R.style.AppThemeNight);
        }else {
            setTheme(R.style.AppTheme);
        }

        //第一：默认初始化
        Bmob.initialize(this, "b0bff13900cd010d5145da59e88f213f");
        //加入默认的Fragment界面
        addDefaultFragment();
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        initEvent();

    }




    /*
    * @desc 加入默认的Fragment界面
    * @时间 2016/8/1 21:44
    */
    private void addDefaultFragment() {
        noteListFragment = new NoteListFragment(sPref.getInt("note_style",1));
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_home, noteListFragment).commit();
    }



    private void initData() {
        myNotes = new ArrayList<NoteNew>();
//        //获取当前fragment
//        noteListFragment = (NoteListFragment) getSupportFragmentManager().
//                findFragmentById(R.id.fl_home);
    }

    private void initUI() {


        //llMenuTitle= (LinearLayout)findViewById(R.id.ll_menu_title);

        flHome = (FrameLayout) findViewById(R.id.fl_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
//        //添加菜单
//        mToolbar.inflateMenu(R.menu.note_style);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        llMenuTitle = (LinearLayout) navigationView.getHeaderView(0);

//        imagePath = sPref.getString("menu_title_image", "");
//        if (imagePath.equals("")) {
//            llMenuTitle.setBackground(getResources().getDrawable(R.drawable.shape_note_bg));
//        } else {
//            BitmapDrawable bitmapDrawable = getLLBgDrawable(imagePath);
//            llMenuTitle.setBackground(bitmapDrawable);
//        }

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
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
//            //获取当前fragment
//            NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager().
//                    findFragmentById(R.id.fl_home);
//            noteListFragment.smoothScrollToTop();
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
                myNotes = SQLUtils.getNoteList();
                if (myNotes.size() == 0) {
                    Snackbar.make(flHome, "点击按钮新增笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    noteListFragment.updateAll(myNotes);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, noteListFragment)
                        .commit();
                break;
            /* @描述 点击切换到pin笔记列表 */
            case R.id.nav_pin:

                myNotes = SQLUtils.getPinNoteList();
                if (myNotes.size() == 0) {
                    Snackbar.make(flHome, "没有pin笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("PIN笔记");
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

                myNotes = SQLUtils.getImageNoteList();
                if (myNotes.size() == 0) {
                    Snackbar.make(flHome, "没有图片笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("图片笔记");
                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;

            /* @描述 点击切换到声音笔记列表 */
            case R.id.nav_voice:

                myNotes = SQLUtils.getVoiceNoteList();
                if (myNotes.size() == 0) {
                    Snackbar.make(flHome, "没有录音笔记...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("录音笔记");

                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到行事历笔记列表 */
            case R.id.nav_schedule:

                myNotes = SQLUtils.getScheduleNoteList();
                if (myNotes.size() == 0) {
                    Snackbar.make(flHome, "没有行事历...", Snackbar.LENGTH_SHORT).show();
                } else {
                    mToolbar.setTitle("录音笔记");

                    noteListFragment.updateAll(myNotes);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, noteListFragment)
                            .commit();
                }
                break;
            /* @描述 点击切换到设置界面 */
            case R.id.nav_setting:
                 /* @描述 隐藏fab */

                mToolbar.setTitle("设置");
                mSettingFragment = new SettingFragment(noteListFragment, sPref, homeHandler);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_home, mSettingFragment).commit();
                break;

            case R.id.nav_author:
                /* @描述 隐藏fab */

                Person p2 = new Person();
                p2.setName("lucky");
                p2.setAddress("北京海淀");
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(UIHomeActivity.this, "添加数据成功，返回objectId为：" + objectId, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UIHomeActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();


        /* @描述 如果开启上锁 */
        if (sPref.getBoolean("lock", false)&&noNeedLock){
            canBack=false;
            noNeedLock=false;
            //如果是第一次点击
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final TouchDeblockingView tdvLock = new TouchDeblockingView(this);

            builder.setTitle("请输入密码");
            builder.setView(tdvLock);

                tdvLock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {
                    @Override
                    public boolean OnDrawFinished(List<Integer> passList) {

                        if (passList.size() < 4) {
                            Snackbar.make(tdvLock, "密码过短，请重新输入", Snackbar.LENGTH_SHORT).show();
                            tdvLock.resetPoints();
                            return false;
                        } else {

                            StringBuilder sb = new StringBuilder();

                            for (Integer i : passList) {
                                sb.append(i);
                            }

                            if (sPref.getString("password", "").equals(sb.toString())) {
                                mDialog.dismiss();
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                });
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

    @Override
    protected void onPause() {
        super.onPause();

    }

    /* @描述 打开展示图片线程 */
    protected void startShowImageThread() {
        Thread showImageThread = new Thread(showImageRunnable);
        showImageThread.start();

    }

    /* @描述 展示图片异步加载 */
    private Runnable showImageRunnable = new Runnable() {
        @Override
        public void run() {
            List<Image> all = DataSupport.findAll(Image.class);
            if (all.size() == 0) {
                Snackbar.make(flHome, "没有图片...", Snackbar.LENGTH_SHORT).show();
                return;
            }
            final List<BitmapDrawable> allBitmapDrawable = new ArrayList<BitmapDrawable>();
            for (int i = 0; i < all.size(); i++) {
                allBitmapDrawable.add(
                        CameraImageUtils
                                .getImageDrawable(UIHomeActivity.this, all.get(i).getImagePath(), 360));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageShowFragment imageShowFragment = new ImageShowFragment(allBitmapDrawable);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_home, imageShowFragment)
                            .commit();
                }
            });
        }
    };

    /*
    * @方法 针对相机非返回值处理
    *
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SKKK_____", "requestCode:  " + requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                /* @描述 相册返回值 */
                case PHOTO_REQUEST_GALLERY:
                    if (data != null) {
                        Uri uriImageFromGallery = data.getData();
                        String path = CameraImageUtils.getAbsoluteImagePath(UIHomeActivity.this,
                                uriImageFromGallery);
                        BitmapDrawable bitmapDrawable = getLLBgDrawable(path);
                        llMenuTitle.setBackground(bitmapDrawable);
                        llMenuTitle.setGravity(Gravity.CENTER);
                        sPref.edit().putString("menu_title_image", path).commit();
                        mSettingFragment.setSsvMenuImageSrc(path);
                    }
                    break;

            }

        }

    }

    public BitmapDrawable getLLBgDrawable(String path) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
                path);
        bitmapDrawable.setDither(true);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setFilterBitmap(true);
        bitmapDrawable.setGravity(Gravity.RELATIVE_LAYOUT_DIRECTION);
        bitmapDrawable.setTileModeXY(Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        return bitmapDrawable;
    }



}

