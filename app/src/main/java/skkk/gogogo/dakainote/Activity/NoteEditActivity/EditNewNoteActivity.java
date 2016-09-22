package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.MyViewUtils;
import skkk.gogogo.dakainote.R;


/*
* 
* 描    述：专门用来解决Edit以及Schedule问题
* 作    者：ksheng
* 时    间：2016/9/22$ 21:57$.
*/
public class EditNewNoteActivity extends VoiceNewNoteActivity {
    protected LinearLayout llNoteAgain;
    protected CheckBox cbfirstSchedule;
    protected MyApplication myApplication;
    protected NestedScrollView nsvEditAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplicationContext();
        initEditUI();//初始化UI
        initEditEvent();//设置监听事件
    }

    /*
    * @方法 初始化UI
    *
    */
    private void initEditUI() {
        llNoteAgain = (LinearLayout) findViewById(R.id.ll_edit_again);//包裹在item的LL
        cbfirstSchedule = (CheckBox) findViewById(R.id.cb_first_schedule);//item中的cb
        nsvEditAgain = (NestedScrollView) findViewById(R.id.nsv_edit_again);//ll外面的NSV

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etFirstSchedule,InputMethodManager.SHOW_FORCED);


    }

    /*
    * @方法 甚至监听事件
    *
    */
    private void initEditEvent() {

        etFirstSchedule.setOnKeyListener(new View.OnKeyListener() {
            int count = llNoteAgain.getChildCount();//标准应该是2
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        if (keyCode == KeyEvent.KEYCODE_ENTER && isScheduleExist) {
                            /* @描述 软键盘回车事件 */
                            insertFirstItem();

                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etFirstSchedule,InputMethodManager.SHOW_FORCED);


                        } else if (keyCode == KeyEvent.KEYCODE_DEL && isScheduleExist &&
                                TextUtils.isEmpty(etFirstSchedule.getText().toString())) {
                            /* @描述 软件盘Del事件 */
                            if (count == 1) {//说明只有一个基础的item，那么删除iv
                                cbfirstSchedule.setVisibility(View.GONE);
                                isScheduleExist = false;
                            }
                        }
                        break;
                    case KeyEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

        /* @描述 cb的勾选监听事件 */
        cbfirstSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etFirstSchedule.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
                    etFirstSchedule.setTextColor(Color.GRAY);
                } else {
                    etFirstSchedule.setPaintFlags(Paint.HINTING_ON);
                    etFirstSchedule.setTextColor(Color.BLACK);
                }
            }
        });


    }

    private void insertFirstItem() {
        final LinearLayout llItem = new LinearLayout(this);
        llItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        llItem.setOrientation(LinearLayout.HORIZONTAL);


        final CheckBox cbItem = new CheckBox(this);
        final EditText etItem = new EditText(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etItem, InputMethodManager.SHOW_FORCED);


        cbItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cbItem.setForegroundGravity(Gravity.CENTER);
        }

        etItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        etItem.setGravity(Gravity.CENTER_VERTICAL);
        etItem.setTextSize(25);
        etItem.setPadding(0,10,0,10);
        etItem.setSingleLine(true);


        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etItem.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
                    etItem.setTextColor(Color.GRAY);
                } else {
                    etItem.setPaintFlags(Paint.HINTING_ON);
                    etItem.setTextColor(Color.BLACK);
                }
            }
        });


        etItem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        if (keyCode == KeyEvent.KEYCODE_ENTER && isScheduleExist) {
                            insertFirstItem();
                        } else if (keyCode == KeyEvent.KEYCODE_DEL && isScheduleExist) {//说明只有一个基础的item，那么删除iv
                            if (myApplication.getChildCountInScheduleItem() == 2
                                    && TextUtils.isEmpty(etItem.getText().toString())) {

                                cbItem.setVisibility(View.GONE);
                                myApplication.setChildCountInScheduleItem(1);
                            } else if (myApplication.getChildCountInScheduleItem() == 1
                                    && TextUtils.isEmpty(etItem.getText().toString())) {
                                LogUtils.Log("" + myApplication.getChildCountInScheduleItem());
                                llNoteAgain.removeView(llItem);
                                myApplication.setChildCountInScheduleItem(2);
                                getFouce();
                            }
                        }
                        break;
                    case KeyEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

        llItem.addView(cbItem);
        llItem.addView(etItem);

        llNoteAgain.addView(llItem);

    }

    public void getFouce() {
        EditText view = (EditText) ((LinearLayout) llNoteAgain.
                getChildAt(llNoteAgain.getChildCount() - 1)).getChildAt(1);
        MyViewUtils.getFoucs(view);
    }
}
