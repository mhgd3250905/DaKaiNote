package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.MyViewUtils;
import skkk.gogogo.dakainote.R;

public class NoteEditAgainActivity extends AppCompatActivity {
    boolean isSchedule;
    LinearLayout llNoteAgain;
    Button btnSchedule;
    ImageView ivfirstSchedule;
    EditText etFirstSchedule;
    private int mItemChildCount;
    private MyApplication myApplication;
    private InputMethodManager mImm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplicationContext();
        initUI();
        initEvent();
    }



    private void initUI() {
        setContentView(R.layout.activity_note_edit_again);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llNoteAgain = (LinearLayout) findViewById(R.id.ll_edit_again);
        btnSchedule= (Button) findViewById(R.id.btn_schedule);
        ivfirstSchedule= (ImageView) findViewById(R.id.iv_first_schedule);
        etFirstSchedule= (EditText) findViewById(R.id.et_first_schedule);
    }


    private void initEvent() {
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSchedule){
                    ivfirstSchedule.setVisibility(View.VISIBLE);
                    isSchedule=true;

                }

            }
        });

        etFirstSchedule.setOnKeyListener(new View.OnKeyListener() {

            int count=llNoteAgain.getChildCount();//标准应该是2

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        if (keyCode == KeyEvent.KEYCODE_ENTER && isSchedule) {
                            insertFirstItem();
                        } else if (keyCode == KeyEvent.KEYCODE_DEL && isSchedule &&
                                TextUtils.isEmpty(etFirstSchedule.getText().toString())){
                            if (count==2){//说明只有一个基础的item，那么删除iv
                                ivfirstSchedule.setVisibility(View.GONE);
                                isSchedule=false;
                            }
                        }
                            break;
                    case KeyEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    private void insertFirstItem() {
        final LinearLayout llItem = new LinearLayout(this);
        llItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        llItem.setOrientation(LinearLayout.HORIZONTAL);

        final ImageView ivItem = new ImageView(this);
        ivItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ivItem.setForegroundGravity(Gravity.CENTER);
        }
        ivItem.setImageResource(R.drawable.ic_launcher);

        final EditText etItem = new EditText(this);
        etItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        etItem.setGravity(Gravity.CENTER_VERTICAL);
        etItem.setTextSize(25);
        etItem.setSingleLine(true);

        etItem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        if (keyCode == KeyEvent.KEYCODE_ENTER && isSchedule) {
                            insertFirstItem();
                        } else if (keyCode == KeyEvent.KEYCODE_DEL && isSchedule) {//说明只有一个基础的item，那么删除iv
                            if (myApplication.getChildCountInScheduleItem()==2
                                    && TextUtils.isEmpty(etItem.getText().toString())) {
                                LogUtils.Log(""+ mItemChildCount);
                                ivItem.setVisibility(View.GONE);
                                myApplication.setChildCountInScheduleItem(1);
                            } else if (myApplication.getChildCountInScheduleItem() == 1
                                    && TextUtils.isEmpty(etItem.getText().toString())){
                                LogUtils.Log(""+ myApplication.getChildCountInScheduleItem());
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


        llItem.addView(ivItem);
        llItem.addView(etItem);

        llNoteAgain.addView(llItem);

    }

    public void getFouce(){
        EditText view= (EditText) ((LinearLayout) llNoteAgain.
                        getChildAt(llNoteAgain.getChildCount() - 1)).getChildAt(1);
        MyViewUtils.getFoucs(view);
    }


}
