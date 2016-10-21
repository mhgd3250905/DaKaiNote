package skkk.gogogo.dakainote.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.SettingCheckView;
import skkk.gogogo.dakainote.View.SettingShowView;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

/**
 * Created by admin on 2016/10/12.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/12$ 22:58$.
*/
public class SettingFragment extends Fragment {

    private View view;
    private SettingShowView ssvNoteStyle, ssvLock,ssvBackup,ssvResave;
    private SettingCheckView scvNight;
    private NoteListFragment mNoteListFragment;
    private SharedPreferences sPref;
    private Handler homeHandler;
    protected String noteStyle;
    private TextView tvResetPW;
    private Dialog mDialog;


    @SuppressLint("ValidFragment")
    public SettingFragment(NoteListFragment noteListFragment, SharedPreferences sPref, Handler homeHandler) {
        mNoteListFragment = noteListFragment;
        this.homeHandler = homeHandler;
        this.sPref = sPref;
    }

    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        initEvent();
        return view;
    }

    /* @描述 初始化控件 */
    private void initView(final View view) {
        ssvNoteStyle = (SettingShowView) view.findViewById(R.id.ssv_setting_note_style);
        scvNight = (SettingCheckView) view.findViewById(R.id.scv_setting_night);
        ssvLock = (SettingShowView) view.findViewById(R.id.ssv_setting_lock);
        ssvBackup= (SettingShowView) view.findViewById(R.id.ssv_setting_backup);
        ssvResave= (SettingShowView) view.findViewById(R.id.ssv_setting_resave);
        tvResetPW= (TextView) view.findViewById(R.id.tv_setting_reset_pw);

        noteStyle = "瀑布流";
        switch (sPref.getInt("note_style", 1)) {
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


        ssvNoteStyle.setTvShowText(noteStyle);

        if (sPref.getBoolean("lock_first",true)&&TextUtils.isEmpty(sPref.getString("password",""))){
            ssvLock.setTvShowText("点击设置密码");
        }else {
            //设置上锁文字
            if (sPref.getBoolean("lock", false)) {
                ssvLock.setTvShowText("已开启上锁");
            } else {
                ssvLock.setTvShowText("已关闭上锁");
            }
        }

        if (sPref.getBoolean("night",false)){
            scvNight.setCheckTitle("夜间模式");
            scvNight.setChecked(true);
        }else {
            scvNight.setCheckTitle("日间模式");
            scvNight.setChecked(false);
        }

    }

    /* @描述 初始化点击事件 */
    private void initEvent() {
        ssvNoteStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* @描述 设置布局样式点击事件 */
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setSingleChoiceItems(new String[]{"线性布局", "瀑布流", "卡片模式"},
                        sPref.getInt("note_style", 1), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        ssvNoteStyle.setTvShowText("列表");
                                        mNoteListFragment.setLayoutFlag(0);
                                        sPref.edit().putInt("note_style", 0).commit();
                                        dialog.dismiss();
                                        Snackbar.make(view, "您选择了列表", Snackbar.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        ssvNoteStyle.setTvShowText("瀑布流");
                                        mNoteListFragment.setLayoutFlag(1);
                                        sPref.edit().putInt("note_style", 1).commit();
                                        dialog.dismiss();
                                        Snackbar.make(view, "您选择了瀑布流", Snackbar.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        ssvNoteStyle.setTvShowText("卡片");
                                        mNoteListFragment.setLayoutFlag(2);
                                        sPref.edit().putInt("note_style", 2).commit();
                                        dialog.dismiss();
                                        Snackbar.make(view, "您选择了卡片", Snackbar.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                builder.setIcon(R.drawable.item_edit);
                builder.setTitle("布局样式");
                builder.show();
            }
        });


        scvNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sPref.getBoolean("night",false)){
                    sPref.edit().putBoolean("night",false).commit();
                    scvNight.setCheckTitle("夜间模式");
                    scvNight.setChecked(true);
                }else {
                    sPref.edit().putBoolean("night",true).commit();
                    scvNight.setCheckTitle("日间模式");
                    scvNight.setChecked(false);
                }
                startActivity(new Intent(getActivity(), UIHomeActivity.class));
                getActivity().finish();
            }
        });

        ssvLock.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (sPref.getBoolean("lock", false)){
                                               //如果有设置上锁,就关闭上锁
                                               sPref.edit().putBoolean("lock",false).commit();
                                               ssvLock.setTvShowText("已关闭上锁");
                                           }else {
                                               //如果没有设置上锁
                                               if (!sPref.getBoolean("lock_first", true)){
                                                   //如果不是第一次点击
                                                   //打开上锁
                                                   sPref.edit().putBoolean("lock",true).commit();
                                                   ssvLock.setTvShowText("已开启上锁");

                                               }else {
                                                   //如果是第一次点击
                                                   final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                   final TouchDeblockingView tdvLock = new TouchDeblockingView(getContext());

                                                   if (sPref.getBoolean("lock_first", true)) {

                                                       builder.setTitle("请设置密码");
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
                                                                   sPref.edit().putString("password", sb.toString()).commit();
                                                                   sPref.edit().putBoolean("lock_first", false).commit();
                                                                   sPref.edit().putBoolean("lock",true).commit();
                                                                   ssvLock.setTvShowText("已开启上锁");
                                                                   mDialog.dismiss();
                                                                   return true;
                                                               }
                                                           }
                                                       });
                                                       builder.setView(tdvLock);
                                                       mDialog = builder.show();
                                                   }
                                               }
                                           }
                                       }
                                   }

        );

        tvResetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sPref.getBoolean("lock_first",true)){
                    sPref.edit().putBoolean("lock_first",true).commit();
                    sPref.edit().putString("password", "").commit();
                    sPref.edit().putBoolean("lock",false).commit();
                }
                ssvLock.setTvShowText("点击设置密码");
                Snackbar.make(view,"密码已清除，请点击上锁重新设置。",Snackbar.LENGTH_SHORT).show();
            }
        });

        ssvBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.item_save);
                builder.setTitle("备份提醒");
                builder.setMessage("点击确认进行备份操作...");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startBackup();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }

    /* @描述 备份方法 */
    private void startBackup() {

    }


}
