package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/10/12.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/12$ 23:00$.
*/
public class SettingShowView extends LinearLayout {
    private TextView tvTitle,tvShow;
    private final String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private String mTitle,mShow;

    public SettingShowView(Context context) {
        super(context);
        initView();
    }

    public SettingShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTitle=attrs.getAttributeValue(NAMESPACE,"settingTitleText");
        mShow=attrs.getAttributeValue(NAMESPACE,"settingShowText");


        initView();
    }

    public SettingShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_show,this);
        tvTitle= (TextView) findViewById(R.id.tv_setting_title);
        tvShow= (TextView) findViewById(R.id.tv_setting_show);
        tvTitle.setText(mTitle);
        tvShow.setText(mShow);
    }

    private void setTvTitleText(String content){
        tvTitle.setText(content);
    }

    private void setTvShowText(String content){
        tvShow.setText(content);
    }

}
