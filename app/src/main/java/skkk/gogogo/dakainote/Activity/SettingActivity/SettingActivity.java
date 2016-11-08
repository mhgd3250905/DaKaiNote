package skkk.gogogo.dakainote.Activity.SettingActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Image;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.DbTable.Schedule;
import skkk.gogogo.dakainote.DbTable.Voice;
import skkk.gogogo.dakainote.Interface.SettingInterface;
import skkk.gogogo.dakainote.Presenter.SettingPresenter;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.Service.CopyService;
import skkk.gogogo.dakainote.View.SettingCheckView;
import skkk.gogogo.dakainote.View.SettingShowView;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, SettingInterface {


    private CoordinatorLayout mClSetting;
    private SettingShowView ssvNoteStyle;
    private SettingCheckView scvNight;
    private SettingShowView ssvLock;
    private TextView tvResetPassword;
    private SettingShowView ssvBackup;
    private SettingShowView ssvResave;
    private SettingCheckView scvNotify;

    private MyApplication mApplication;
    private Toolbar tbSetting;

    private SettingPresenter mSettingPresenter;
    private String noteStyle;
    private AlertDialog mDialog;
    private boolean mBackupFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplicationContext();
        mSettingPresenter = new SettingPresenter(this);

        setMyTheme();
        initUI();
    }

    /*
    * @方法 设置主题
    * @参数
    * @返回值
    */
    public void setMyTheme() {
        //设置主题
        if (mSettingPresenter.getThemeNight()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    /* @描述 初始化UI */
    private void initUI() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

        setContentView(R.layout.activity_setting);

        tbSetting = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mClSetting = (CoordinatorLayout) findViewById(R.id.cl_setting);
        ssvNoteStyle = (SettingShowView) findViewById(R.id.ssv_setting_note_style);
        scvNight = (SettingCheckView) findViewById(R.id.scv_setting_night);
        ssvLock = (SettingShowView) findViewById(R.id.ssv_setting_lock);
        tvResetPassword = (TextView) findViewById(R.id.tv_setting_reset_pw);
        ssvBackup = (SettingShowView) findViewById(R.id.ssv_setting_backup);
        ssvResave = (SettingShowView) findViewById(R.id.ssv_setting_resave);
        scvNotify= (SettingCheckView) findViewById(R.id.scv_setting_notify);

        ssvNoteStyle.setOnClickListener(this);
        scvNight.setOnClickListener(this);
        ssvLock.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
        ssvBackup.setOnClickListener(this);
        ssvResave.setOnClickListener(this);
        scvNotify.setOnClickListener(this);

        noteStyle = "瀑布流";
        switch (mSettingPresenter.getNoteStyle()) {
            case 0:
                noteStyle = "列表";
                break;
            case 1:
                noteStyle = "瀑布流";
                break;
            case 2:
                noteStyle = "卡片";
                break;
        }
        setNoteStyle(noteStyle);

        if (mSettingPresenter.isFirstLock() && TextUtils.isEmpty(mSettingPresenter.getPassword())) {
            ssvLock.setTvShowText("点击设置密码");
        } else {
            //设置上锁文字
            if (mSettingPresenter.isLocked()) {
                setLock("已开启上锁");
            } else {
                setLock("已关闭上锁");
            }
        }


        if (mSettingPresenter.isNight()) {
            setNight(true, "夜间模式");
        } else {
            setNight(false, "日间模式");
        }


        /* @描述 显示服务开关 */
        if (mSettingPresenter.isMyServiceRunning()){
            setCopy(true,"剪切板监听功能已开启");
        }else {
            setCopy(false,"剪切板监听功能已关闭");
        }


    }


    /* @描述 点击事件 */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ssv_setting_note_style:
                //笔记风格
                 /* @描述 设置布局样式点击事件 */
                AlertDialog.Builder styleBuilder = new AlertDialog.Builder(this);
                styleBuilder.setSingleChoiceItems(new String[]{"线性布局", "瀑布流", "卡片模式"},
                        mSettingPresenter.getNoteStyle(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        ssvNoteStyle.setTvShowText("列表");
                                        mSettingPresenter.setCheckStyle(which);
                                        dialog.dismiss();
                                        Snackbar.make(mClSetting, "您选择了列表", Snackbar.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        ssvNoteStyle.setTvShowText("瀑布流");
                                        mSettingPresenter.setCheckStyle(which);
                                        dialog.dismiss();
                                        Snackbar.make(mClSetting, "您选择了瀑布流", Snackbar.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        ssvNoteStyle.setTvShowText("卡片");
                                        mSettingPresenter.setCheckStyle(which);
                                        dialog.dismiss();
                                        Snackbar.make(mClSetting, "您选择了卡片", Snackbar.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                styleBuilder.setIcon(R.drawable.item_edit);
                styleBuilder.setTitle("布局样式");
                styleBuilder.show();
                break;

            case R.id.scv_setting_night:

                //夜间模式
                if (mSettingPresenter.isNight()) {
                    mSettingPresenter.setSomeThingInsPerf("night", false);
                    setNight(false,"日间模式");
                } else {
                    mSettingPresenter.setSomeThingInsPerf("night", true);
                    setNight(true,"夜间模式");
                }
                recreate();
                break;

            case R.id.scv_setting_notify:

                if (mSettingPresenter.isMyServiceRunning()){
                    setCopy(false,"剪切板监听功能已关闭");
                    stopService(new Intent(this, CopyService.class));
                    Snackbar.make(mClSetting,"剪切板监听功能已关闭",Snackbar.LENGTH_SHORT).show();
                }else {
                    setCopy(true,"剪切板监听功能已开启");
                    startService(new Intent(SettingActivity.this, CopyService.class));
                    Snackbar.make(mClSetting,"剪切板监听功能已开启",Snackbar.LENGTH_SHORT).show();
                }

                break;

            case R.id.ssv_setting_lock:
                //上锁
                if (mSettingPresenter.isLocked()) {
                    //如果有设置上锁,就关闭上锁
                    mSettingPresenter.setSomeThingInsPerf("lock", false);
                    ssvLock.setTvShowText("已关闭上锁");
                } else {
                    //如果没有设置上锁
                    if (!mSettingPresenter.isFirstLock()) {
                        //如果不是第一次点击
                        //打开上锁
                        mSettingPresenter.setSomeThingInsPerf("lock", true);
                        ssvLock.setTvShowText("已开启上锁");

                    } else {
                        //如果是第一次点击
                        final AlertDialog.Builder lockBuilder = new AlertDialog.Builder(this);
                        final TouchDeblockingView tdvLock = new TouchDeblockingView(this);

                        if (mSettingPresenter.isFirstLock()) {

                            lockBuilder.setTitle("请设置密码");
                            tdvLock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {
                                @Override
                                public boolean OnDrawFinished(List<Integer> passList) {
                                    if (!mSettingPresenter.deblocking(passList)) {
                                        Snackbar.make(tdvLock, "密码过短，请重新输入", Snackbar.LENGTH_SHORT).show();
                                        tdvLock.resetPoints();
                                        return false;
                                    } else {
                                        ssvLock.setTvShowText("已开启上锁");
                                        mDialog.dismiss();
                                        return true;
                                    }
                                }
                            });
                            lockBuilder.setView(tdvLock);
                            mDialog = lockBuilder.show();
                        }
                    }
                }

                break;
            case R.id.tv_setting_reset_pw:
                //重置上锁密码
                mSettingPresenter.resetPassword();
                ssvLock.setTvShowText("点击设置密码");
                Snackbar.make(mClSetting, "密码已清除，请点击上锁重新设置。", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.ssv_setting_backup:
                //备份
                mBackupFlag = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.item_save);
                builder.setTitle("备份提醒");
                builder.setMessage("点击确认进行备份操作...");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Note> allNote = DataSupport.findAll(Note.class);
                        List<Image> allImage = DataSupport.findAll(Image.class);
                        List<Voice> allVoice = DataSupport.findAll(Voice.class);
                        List<Schedule> allSchedule = DataSupport.findAll(Schedule.class);
                        /* @描述 备份note */
                        for (int i = 0; i < allNote.size(); i++) {
                            mBackupFlag = false;
                            skkk.gogogo.dakainote.Bean.Note backupNote = new skkk.gogogo.dakainote.Bean.Note();
                            backupNote.setTime(allNote.get(i).getTime());
                            backupNote.setContent(allNote.get(i).getContent());
                            backupNote.setGravity(allNote.get(i).getGravity());
                            backupNote.setImageisexist(allNote.get(i).isImageIsExist());
                            backupNote.setPinisexist(allNote.get(i).isPinIsExist());
                            backupNote.setVoiceexist(allNote.get(i).isVoiceExist());
                            backupNote.setKeynum(allNote.get(i).getKeyNum());
                            backupNote.setTitle(allNote.get(i).getTitle());
                            backupNote.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        mBackupFlag = true;
                                    } else {
                                        mBackupFlag = false;
                                    }
                                }
                            });
                        }
                        /* @描述 备份image */
                        if (allImage.size()>0) {
                            for (int i = 0; i < allImage.size(); i++) {
                                mBackupFlag = false;
                                skkk.gogogo.dakainote.Bean.Image backupImage = new skkk.gogogo.dakainote.Bean.Image();
                                backupImage.setNum(allImage.get(i).getNum());
                                backupImage.setImagePath(allImage.get(i).getImagePath());
                                backupImage.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            mBackupFlag = true;
                                        } else {
                                            mBackupFlag = false;
                                        }
                                    }
                                });
                            }
                        }
                        /* @描述 备份Voice */
                        if (allVoice.size()>0) {
                            for (int i = 0; i < allVoice.size(); i++) {
                                mBackupFlag = false;
                                skkk.gogogo.dakainote.Bean.Voice backVoice = new skkk.gogogo.dakainote.Bean.Voice();
                                backVoice.setNum(allVoice.get(i).getNum());
                                backVoice.setVoicePath(allVoice.get(i).getVoicePath());
                                backVoice.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            mBackupFlag = true;
                                        } else {
                                            mBackupFlag = false;
                                        }
                                    }
                                });
                            }
                        }
                        if (allSchedule.size()>0) {
                        /* @描述 备份Schedule */
                            for (int i = 0; i < allSchedule.size(); i++) {
                                mBackupFlag = false;
                                skkk.gogogo.dakainote.Bean.Schedule backupSchedule = new skkk.gogogo.dakainote.Bean.Schedule();
                                backupSchedule.setScheduleChecked(allSchedule.get(i).isScheduleChecked());
                                backupSchedule.setScheduleContent(allSchedule.get(i).getScheduleContent());
                                backupSchedule.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            mBackupFlag = true;
                                        } else {
                                            mBackupFlag = false;
                                        }
                                    }
                                });
                            }
                        }
                        if (mBackupFlag) {
                            Toast.makeText(SettingActivity.this,"OK",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SettingActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();

                break;
            case R.id.ssv_setting_resave:
                //恢复

                break;
        }
    }

    @Override
    public void setNoteStyle(String s) {
        ssvNoteStyle.setTvShowText(s);
    }

    @Override
    public void setLock(String s) {
        ssvLock.setTvShowText(s);
    }

    /* @描述 设置夜间模式按钮 */
    public void setNight(boolean isNight, String title) {
        scvNight.setChecked(isNight);
        scvNight.setCheckTitle(title);
    }

    /* @描述 设置剪切板监听按钮 */
    public void setCopy(boolean isCopy, String title) {
        scvNotify.setChecked(isCopy);
        scvNotify.setCheckTitle(title);
    }

    @Override
    public void setBackupTime(String s) {
        ssvBackup.setTvShowText(s);
    }

    @Override
    public void setResaveTime(String s) {
        ssvResave.setTvShowText(s);
    }
}
