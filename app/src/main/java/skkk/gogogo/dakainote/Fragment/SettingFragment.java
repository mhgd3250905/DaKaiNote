package skkk.gogogo.dakainote.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class SettingFragment extends Fragment{

    private View view;
    private SettingShowView ssvMenuImage,ssvLanguage;
    private SettingCheckView scvNight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    /* @描述 初始化控件 */
    private void initView(final View view) {
        ssvMenuImage= (SettingShowView) view.findViewById(R.id.ssv_setting_menu_image);
        ssvLanguage= (SettingShowView) view.findViewById(R.id.ssv_setting_language);
        scvNight= (SettingCheckView) view.findViewById(R.id.scv_setting_night);
        ssvMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view,"侧滑菜单图片设置",Snackbar.LENGTH_SHORT).show();
            }
        });

        ssvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view,"语言设置",Snackbar.LENGTH_SHORT).show();
            }
        });

        scvNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scvNight.getCheck()){
                    scvNight.setChecked(false);
                    scvNight.setCheckTitle("日间模式");
                    Snackbar.make(view,"日间模式",Snackbar.LENGTH_SHORT).show();
                }else {
                    scvNight.setChecked(true);
                    scvNight.setCheckTitle("夜间模式");
                    Snackbar.make(view,"夜间模式",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


}
