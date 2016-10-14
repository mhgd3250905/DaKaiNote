package skkk.gogogo.dakainote.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skkk.gogogo.dakainote.Activity.HomeActivity.BaseHomeActivity;
import skkk.gogogo.dakainote.Activity.TouchDeblockActivity.TouchDeblockActivity;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.SettingCheckView;
import skkk.gogogo.dakainote.View.SettingShowView;

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
    private SettingShowView ssvMenuImage, ssvNoteStyle,ssvLock;
    private SettingCheckView scvNight;
    private NoteListFragment mNoteListFragment;
    private SharedPreferences sPref;
    private Handler homeHandler;
    protected final static int PHOTO_REQUEST_GALLERY = 914;
    protected String noteStyle,imagePath;

    public SettingFragment(NoteListFragment noteListFragment, SharedPreferences sPref, Handler homeHandler) {
        mNoteListFragment = noteListFragment;
        this.homeHandler = homeHandler;
        this.sPref = sPref;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    /* @描述 初始化控件 */
    private void initView(final View view) {
        ssvMenuImage = (SettingShowView) view.findViewById(R.id.ssv_setting_menu_image);
        ssvNoteStyle = (SettingShowView) view.findViewById(R.id.ssv_setting_note_style);
        scvNight = (SettingCheckView) view.findViewById(R.id.scv_setting_night);
        ssvLock= (SettingShowView) view.findViewById(R.id.ssv_setting_lock);
        noteStyle="瀑布流";
        switch (sPref.getInt("note_style",1)){
            case 0:
                noteStyle="列表";
                break;
            case 1:
                noteStyle="瀑布流";
                break;
            case 2:
                noteStyle="卡片";
                break;
        }

        ssvMenuImage.setTvShowText(sPref.getString("menu_title_image",""));

        ssvNoteStyle.setTvShowText(noteStyle);


        ssvMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* @描述 切换侧滑菜单头图片消息 */
                homeHandler.sendEmptyMessage(1);
            }
        });


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
                BaseHomeActivity uiHomeActivity= (BaseHomeActivity) getActivity();
                if (scvNight.getCheck()) {
                    scvNight.setChecked(false);
                    scvNight.setCheckTitle("日间模式");



                    Snackbar.make(view, "日间模式", Snackbar.LENGTH_SHORT).show();
                } else {
                    scvNight.setChecked(true);
                    scvNight.setCheckTitle("夜间模式");


                    Snackbar.make(view, "夜间模式", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        ssvLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TouchDeblockActivity.class));
            }
        });
    }

    /* @描述 设置侧滑图片路径显示区域 */
    public void setSsvMenuImageSrc(String path){
        ssvMenuImage.setTvShowText(path);
    }

}
