package skkk.gogogo.dakainote.Activity.SettingActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.Interface.SettingInterface;
import skkk.gogogo.dakainote.Presenter.SettingPresenter;
import skkk.gogogo.dakainote.R;
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

    private MyApplication mApplication;
    private Toolbar tbSetting;

    private SettingPresenter mSettingPresenter;
    private String noteStyle;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getApplicationContext();
        mSettingPresenter=new SettingPresenter(this);

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

        ssvNoteStyle.setOnClickListener(this);
        scvNight.setOnClickListener(this);
        ssvLock.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
        ssvBackup.setOnClickListener(this);
        ssvResave.setOnClickListener(this);

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
            setNight(true,"夜间模式");
        } else {
            setNight(false,"日间模式");
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
                if (mSettingPresenter.isNight()){
                    mSettingPresenter.setSomeThingInsPerf("night",false);
                    scvNight.setCheckTitle("日间模式");
                    scvNight.setChecked(false);
                }else {
                    mSettingPresenter.setSomeThingInsPerf("night",true);
                    scvNight.setCheckTitle("夜间模式");
                    scvNight.setChecked(true);
                }

                recreate();


                break;

            case R.id.ssv_setting_lock:
                //上锁
                if (mSettingPresenter.isLocked()){
                    //如果有设置上锁,就关闭上锁
                    mSettingPresenter.setSomeThingInsPerf("lock",false);
                    ssvLock.setTvShowText("已关闭上锁");
                }else {
                    //如果没有设置上锁
                    if (!mSettingPresenter.isFirstLock()){
                        //如果不是第一次点击
                        //打开上锁
                        mSettingPresenter.setSomeThingInsPerf("lock",true);
                        ssvLock.setTvShowText("已开启上锁");

                    }else {
                        //如果是第一次点击
                        final AlertDialog.Builder lockBuilder = new AlertDialog.Builder(this);
                        final TouchDeblockingView tdvLock = new TouchDeblockingView(this);

                        if (mSettingPresenter.isFirstLock()) {

                            lockBuilder.setTitle("请设置密码");
                            tdvLock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {
                                @Override
                                public boolean OnDrawFinished(List<Integer> passList) {
                                    if (!mSettingPresenter.deblocking(passList)){
                                        Snackbar.make(tdvLock, "密码过短，请重新输入", Snackbar.LENGTH_SHORT).show();
                                        tdvLock.resetPoints();
                                        return false;
                                    }else {
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
                Snackbar.make(mClSetting,"密码已清除，请点击上锁重新设置。",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.ssv_setting_backup:
                //备份
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.item_save);
                builder.setTitle("备份提醒");
                builder.setMessage("点击确认进行备份操作...");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    @Override
    public void setNight(boolean isNight, String title) {
        scvNight.setChecked(isNight);
        scvNight.setCheckTitle(title);
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
