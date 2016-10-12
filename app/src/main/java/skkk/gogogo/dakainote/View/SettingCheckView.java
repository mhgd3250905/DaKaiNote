package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
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
public class SettingCheckView extends LinearLayout {
    private TextView tvTitle;
    private CheckBox cb;
    private final String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private String mTitle;

    public SettingCheckView(Context context) {
        super(context);
        initView();
    }

    public SettingCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTitle=attrs.getAttributeValue(NAMESPACE,"checkTitleText");
        initView();
    }

    public SettingCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_check,this);
        tvTitle= (TextView) findViewById(R.id.tv_setting_title);
        cb= (CheckBox) findViewById(R.id.cb_view_setting_check);
        if (cb.isChecked()){
            tvTitle.setText("夜间模式");
        }else {
            tvTitle.setText("日间模式");
        }
    }

    public boolean getCheck(){
        return cb.isChecked();
    }

    public void setChecked(boolean checked){
        cb.setChecked(checked);
    }

    public void setCheckTitle(String text){
        tvTitle.setText(text);
    }


}
