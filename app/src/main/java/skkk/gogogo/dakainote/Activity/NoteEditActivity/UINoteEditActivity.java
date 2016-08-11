package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import skkk.gogogo.dakainote.R;

/*
*
* @描述 包含UI初始化 以及UI中包含的基础组件的点击事件
* @作者 admin
* @时间 2016/8/11 21:26
*
*/
public class UINoteEditActivity extends BaseNoteActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }
    //初始化UI
    protected void initUI() {
        setContentView(R.layout.activity_note_detail);
        etNoteDetail= (EditText) findViewById(R.id.et_note_detail);
        //设置toolbar
        tbNoteDetail = (Toolbar) findViewById(R.id.tb_note_detail);
        //添加菜单
        tbNoteDetail.inflateMenu(R.menu.menu_note_detail_edit);
        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        tbNoteDetail.setOnMenuItemClickListener(onMenuItemClick);

        //为toolbar 添加返回按钮
        tbNoteDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*
    * @方法 toolBar的右侧菜单的点击事件
    * @描述 toolBar的右侧菜单的点击事件
    *
    */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_image:
                    //点击调用相机添加图片
                    dispatchTakePictureIntent();
                    break;
                case R.id.action_save:
                    //这里在关闭的时候对应前面的startActivityForResult()
                    // 返回一个note数据
                    if(!TextUtils.isEmpty(etNoteDetail.getText().toString())){
                        //保存到数据库
                        mSaveData();
                        Intent intent=new Intent();
                        intent.putExtra("note_form_edit",note);
                        UINoteEditActivity.this.setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
            return true;
        }
    };
}
