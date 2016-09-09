package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.NoteItemActivity.NoteImageActivity;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.AudioButton;
import skkk.gogogo.dakainote.View.MyImageView;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：可以进行动态增加控件的note编辑界面
* 作    者：ksheng
* 时    间：2016/8/26$ 22:18$.
*/
public class UINewNoteActivity extends BaseNewNoteActivity {
    protected static int NUM = 2;
    protected static int REQUEST_NOTE_IMAGE_DELETE = 13;
    protected int childNum;
    protected boolean TextEmptyFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = new NoteNew();
    }

    /*
    * @方法 增加EditTextitem
    *
    */
    protected void addEditTextItem() {
        LayoutInflater li = LayoutInflater.from(this);
        final View view_text = li.inflate(R.layout.item_note_text, null);
        final EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        //添加view
        llNoteDetail.addView(view_text);
        etText.setFocusable(true);
        etText.setFocusableInTouchMode(true);
        etText.requestFocus();

        //添加之后计算目前的child数量
        childNum=llNoteDetail.getChildCount();

         /* @描述 view_image 软键盘按键监听事件 */
        etText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && childNum > 3) {
                    String editStr=etText.getText().toString();

                    switch (event.getAction()){

                        case KeyEvent.ACTION_DOWN:

                            break;
                        case KeyEvent.ACTION_UP:
                            //当按下的是del按键以及此child已经大于第三个了
                            if (TextUtils.isEmpty(editStr)) {
                                //当edit的内容已经是空的
                                getFocuse(llNoteDetail.getChildAt(childNum-2));
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
    }

    /*
    * @方法 增加EditTextitem
    *
    */
    protected void addEditTextItem(String text) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_text = li.inflate(R.layout.item_note_text, null);
        EditText etText = (EditText) view_text.findViewById(R.id.et_note_text);
        etText.setText(text);
        llNoteDetail.addView(view_text);
    }


    /*
    * @方法 在show界面使用的 增加图片item
    *
    */
    protected void addImageItem(String imagePath, int imageId) {
        LayoutInflater li = LayoutInflater.from(this);
        final View view_image = li.inflate(R.layout.item_note_image, null);
        final MyImageView ivInsert = (MyImageView) view_image.findViewById(R.id.iv_note_image);
        ivInsert.setBitmapFromPath(imagePath);
        ivInsert.setImageId(imageId);
        LogUtils.Log("设置image位置为" + llNoteDetail.getChildCount());
        ivInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                testView = view_image;
                intent.putExtra("image_click", ivInsert.getImagePath());
                intent.setClass(UINewNoteActivity.this, NoteImageActivity.class);
                startActivityForResult(intent, REQUEST_NOTE_IMAGE_DELETE);
            }
        });
        llNoteDetail.addView(view_image);
    }


    /*
    * @方法 在编辑界面使用的 增加图片item
    *
    */
    protected void addImageItem(String imagePath) {
        LayoutInflater li = LayoutInflater.from(this);
        final View view_image = li.inflate(R.layout.item_note_image, null);
        final MyImageView ivInsert = (MyImageView) view_image.findViewById(R.id.iv_note_image);
        final ImageView ivNoteImageDelete = (ImageView) view_image.findViewById(R.id.iv_note_image_delete);

        ivInsert.setBitmapFromPath(imagePath);
        LogUtils.Log("设置image位置为" + llNoteDetail.getChildCount());
        ivInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                testView = view_image;
                intent.putExtra("image_click", ivInsert.getImagePath());
                intent.setClass(UINewNoteActivity.this, NoteImageActivity.class);
                startActivityForResult(intent, REQUEST_NOTE_IMAGE_DELETE);
            }
        });
        llNoteDetail.addView(view_image);

        /* @描述 view_image的焦点监听事件 */
        view_image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.Log("image_item是否被选中——————>" + hasFocus);
                if (hasFocus){
                    //如果获取到了焦点 那么就显示删除按钮
                    ivNoteImageDelete.setVisibility(View.VISIBLE);
                }else{
                    //如果没有获取的焦点 那么就不显示焦点
                    ivNoteImageDelete.setVisibility(View.GONE);
                }
            }
        });

        /* @描述 view_image 软键盘按键监听事件 */
        view_image.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_DEL&&event.getAction()==KeyEvent.ACTION_UP){
                    //如果是del按键那么将iamge_view除移
                    llNoteDetail.removeView(llNoteDetail.getChildAt(childNum - 2));
                    //移除之后为下一个控件获取焦点
                    getFocuse(llNoteDetail.getChildAt(llNoteDetail.getChildCount() - 1));
                }
                return true;
            }
        });
    }


    /*
    * @方法 增加录音item
    *
    */
    protected void addVoiceItem(String voicePath) {
        LayoutInflater li = LayoutInflater.from(this);
        View view_voice = li.inflate(R.layout.item_note_voice, null);
        final AudioButton abVoice = (AudioButton) view_voice.findViewById(R.id.ab_note_voice);
        abVoice.setVoicePath(voicePath);
        llNoteDetail.addView(view_voice);

          /* @描述 view_image的焦点监听事件 */
        view_voice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.Log("image_item是否被选中——————>" + hasFocus);
                if (hasFocus) {
                    //如果获取到了焦点 那么就显示删除按钮
                    abVoice.setBackgroundColor(getResources().getColor(R.color.colorRed));
                } else {
                    //如果没有获取的焦点 那么就不显示焦点
                    abVoice.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });

        /* @描述 view_image 软键盘按键监听事件 */
        view_voice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                    //如果是del按键那么将iamge_view除移
                    llNoteDetail.removeView(llNoteDetail.getChildAt(childNum - 2));
                    //移除之后为下一个控件获取焦点
                    getFocuse(llNoteDetail.getChildAt(llNoteDetail.getChildCount()-1));

                }
                return true;
            }
        });
    }

}
