package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import skkk.gogogo.dakainote.Activity.ShareActivity.ShareActivity;
import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.TextNextCache;
import skkk.gogogo.dakainote.DbTable.TextPeriousCache;
import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.MyUtils.KeyBoardUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.MyUtils.MyViewUtils;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/26.
 */
/*
* 
* 描    述：关于弹射菜单的noteActivity
* 作    者：ksheng
* 时    间：2016/8/26$ 23:02$.
*/
public class BottomBarNoteActivity extends VoiceNoteActivity {
    protected AlertDialog imageDialog;

    protected final static int PHOTO_REQUEST_GALLERY = 914;
    protected ImageView ivNoteEditSeparate;
    protected ImageView ivNoteEditBack;
    protected ImageView ivNoteEditContact;
    protected ImageView ivNoteEditTime;
    protected ImageView ivNoteEditPin;
    protected ImageView ivNoteEditNext;
    protected ImageView ivNoteEditSchedule;
    protected ImageView ivNoteEditBold;
    boolean change = true;
    private AlertDialog mDialog;
    private Dialog mDialogShare;
    private AlertDialog mShareTypeDialog;
    protected boolean boldFlag = false;
    private Dialog mShareTypeDialogBKS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottomBar();
        initLLEvent();
        initEtEvent();
        ShareSDK.initSDK(this);
    }

    private void initEtEvent() {
        /* @描述 添加空文本 */
        etFirstSchedule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (change) {
//                    if (boldFlag) {
//                        change=false;
//                        etFirstSchedule.append(Html.fromHtml("<big>"+s.toString()+"</big>"));
//                        change=true;
//                    }
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isScheduleExist && !boldFlag) {
                    if (change) {
                    /* @描述 如果不是schedule那么就进行上一步缓存处理 */
                        TextPeriousCache textPeriousCache = new TextPeriousCache();
                        textPeriousCache.setView_name("etFirstSchedule");
                        textPeriousCache.setContent(s.toString());
                        textPeriousCache.save();
                        LogUtils.Log("完成记录  " + s.toString());
                    }
                }

//                if (!checkString.equals(etFirstSchedule.getText().toString())) {
//                    checkString = etFirstSchedule.getText().toString();
//                    SpannableString spannableString = etFirstSchedule.makeSpannableString(s.toString());
//                    int start = etFirstSchedule.getSelectionStart();
//
//                    if (change) {
//                        change = false;
//                    }
//
//                    etFirstSchedule.setText(spannableString);
//
//                    if (!change) {
//                        change = true;
//                    }
//
//                    etFirstSchedule.setMovementMethod(new LinkTouchMovementMethod());
//                    Log.d("skkk", "检查了一遍~");
//                    etFirstSchedule.setSelection(start);
//                    checkFlag = false;
//                }
            }
        });
    }


    /*
    * @方法 初始化bottomBarUI
    *
    */
    private void initBottomBar() {
        ivNoteEditSeparate = (ImageView) findViewById(R.id.bottom_bar_separate);
        ivNoteEditBold = (ImageView) findViewById(R.id.bottom_bar_bold);
        ivNoteEditBack = (ImageView) findViewById(R.id.bottom_bar_back);
        ivNoteEditContact = (ImageView) findViewById(R.id.bottom_bar_contack);
        ivNoteEditTime = (ImageView) findViewById(R.id.bottom_bar_time);
        ivNoteEditPin = (ImageView) findViewById(R.id.bottom_bar_pin);
        ivNoteEditNext = (ImageView) findViewById(R.id.bottom_bar_next);
        ivNoteEditSchedule = (ImageView) findViewById(R.id.bottom_bar_check);


        /* @描述 字体Bold */
        ivNoteEditBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boldFlag) {
                    boldFlag = false;
                    Toast.makeText(myApplication, "Fail", Toast.LENGTH_SHORT).show();
                } else {
                    etFirstSchedule.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                    etFirstSchedule.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页

                    boldFlag = true;
                    Toast.makeText(myApplication, "OK", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /* @描述 pin标记 */
        ivNoteEditPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* @描述 pin */
                if (isPin) {
                    isPin = false;
                    ivPin.setVisibility(View.INVISIBLE);
                } else {
                    isPin = true;
                    ivPin.setVisibility(View.VISIBLE);
                }
            }
        });

        /* @描述 Schedule模式 */
        ivNoteEditSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isScheduleExist) {
                    cbfirstSchedule.setVisibility(View.VISIBLE);
                    isScheduleExist = true;
                    nsvEditAgain.setFillViewport(false);

                    LinearLayout.LayoutParams paramsCb =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                    paramsCb.gravity = Gravity.CENTER;
                    cbfirstSchedule.setLayoutParams(paramsCb);
                    cbfirstSchedule.setPadding(0, 0, 0, 0);
                    cbfirstSchedule.setGravity(Gravity.CENTER);
                    cbfirstSchedule.setButtonDrawable(R.drawable.select_checkbox_for_item_delete);

                    LinearLayout.LayoutParams paramsEt = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    etFirstSchedule.setLayoutParams(paramsEt);

                    etFirstSchedule.setGravity(Gravity.CENTER_VERTICAL);
                    etFirstSchedule.setPadding(0, 0, 0, 0);
                    etFirstSchedule.setBackground(null);
                    etFirstSchedule.setTextSize(17);
                    etFirstSchedule.setSingleLine(true);
                    MyViewUtils.getFoucs(etFirstSchedule);
                } else {
                    if (llNoteAgain.getChildCount() == 1) {//说明只有一个基础的item，那么删除iv
                        cbfirstSchedule.setVisibility(View.GONE);
                        isScheduleExist = false;
                        nsvEditAgain.setFillViewport(true);
                        LinearLayout.LayoutParams paramsEt = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        etFirstSchedule.setLayoutParams(paramsEt);
                        etFirstSchedule.setGravity(Gravity.TOP | Gravity.LEFT);
                        etFirstSchedule.setSingleLine(false);
                    }
                }
            }
        });


        /* @描述 加入分隔符 */
        ivNoteEditSeparate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScheduleExist) {
                    Snackbar.make(llNoteAgain, "行事历状态无法插入分隔符", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                etFirstSchedule.append("\n");
                etFirstSchedule.append("-------------------------");
                etFirstSchedule.append("\n");

            }
        });

        /* @描述 点击选择对齐方式 */
        ivNoteEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScheduleExist) {
                    Snackbar.make(llNoteAgain, "行事历状态无法调节对齐方式", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BottomBarNoteActivity.this);
                boolean[] gravityCheck = new boolean[]{true, false, false};
                int gravityIndex = sPref.getInt("edit_gravity", 0);
                for (int i = 0; i < 3; i++) {
                    if (i == gravityIndex) {
                        gravityCheck[i] = true;
                    } else {
                        gravityCheck[i] = false;
                    }
                }
                builder.setMultiChoiceItems(new String[]{"左对齐", "居中", "右对齐"},
                        gravityCheck,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                switch (which) {
                                    case 0:
                                        etFirstSchedule.setGravity(Gravity.LEFT | Gravity.TOP);
                                        sPref.edit().putInt("edit_gravity", 0).commit();
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        etFirstSchedule.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                                        sPref.edit().putInt("edit_gravity", 1).commit();
                                        dialog.dismiss();
                                        break;
                                    case 2:
                                        etFirstSchedule.setGravity(Gravity.RIGHT | Gravity.TOP);
                                        sPref.edit().putInt("edit_gravity", 2).commit();
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        }
                );
                builder.setTitle("请选择文字对齐方式");
                mDialog = builder.create();
                builder.show();
            }
        });
        /* @描述 点击加入图片或者录音 */
        ivNoteEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScheduleExist) {
                    Snackbar.make(llNoteAgain, "行事历状态无法插入时间", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                View insertView = View.inflate(BottomBarNoteActivity.this, R.layout.dialog_insert, null);
                TextView tvInsertImage = (TextView) insertView.findViewById(R.id.tv_dialog_insert_image);
                TextView tvInsertVoice = (TextView) insertView.findViewById(R.id.tv_dialog_insert_voice);
                /* @描述 点击插入图片 */
                tvInsertImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         /* @描述 只要录音按键出现那么就强制关闭软键盘 */
                        KeyBoardUtils.hidekeyBoard(BottomBarNoteActivity.this, etFirstSchedule);

                        /* @描述 设置dialogView */
                        final View dialogView = View.inflate(BottomBarNoteActivity.this,
                                R.layout.dialog_image, null);
                        TextView tvFromCamera =
                                (TextView) dialogView.findViewById(R.id.tv_dialog_image_from_camera);
                        TextView tvFromAlbum =
                                (TextView) dialogView.findViewById(R.id.tv_dialog_image_from_album);


                         /* @描述 设置item点击事件 */

                        /* @描述 相机拍照 */
                        tvFromCamera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*
                                 * @方法 点击调用相机拍照
                                 *
                                 */
                                takePicture(BottomBarNoteActivity.this);
                                isDelete = false;
                                imageDialog.dismiss();
                            }
                        });

                        /* @描述 来自相册 */
                        tvFromAlbum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 激活系统图库，选择一张图片
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                                imageDialog.dismiss();
                            }
                        });

                        imageDialog = new AlertDialog.Builder(BottomBarNoteActivity.this)
                                .setView(dialogView).create();
                        Window windowImage = imageDialog.getWindow();
                        windowImage.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                        windowImage.setWindowAnimations(R.style.MyDialogBottomStyle);  //添加动画
                        imageDialog.show();

                        mShareTypeDialog.dismiss();
                    }
                });

                /* @描述 点击插入录音 */
                tvInsertVoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (rbVoice.getVisibility() == View.VISIBLE) {
                            rbVoice.setVisibility(View.GONE);
                        } else {
                            rbVoice.setVisibility(View.VISIBLE);
                         /* @描述 只要录音按键出现那么就强制关闭软键盘 */
                            KeyBoardUtils.hidekeyBoard(BottomBarNoteActivity.this, etFirstSchedule);
                        }

                        mShareTypeDialog.dismiss();
                    }
                });
                mShareTypeDialog = new AlertDialog.Builder(BottomBarNoteActivity.this)
                        .setView(insertView).create();
                Window windowShareType = mShareTypeDialog.getWindow();
                windowShareType.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                windowShareType.setWindowAnimations(R.style.MyDialogBottomStyle);  //添加动画
                mShareTypeDialog.show();
            }
        });

        /* @描述
          *
          * 这里需要对文本编辑器的上一步还有有下一步的逻辑进行一个梳理
          * 正常情况下 文本编辑器所有的TextChange都会被记录到db中
          * 按下 上一步 按钮
          * 返回db中的上一个text
          * 这个时候的TextChange都不会写入到db中
          * 但是每一次返回上一步，响应的db table中的对应item就会加入到 下一步的db中
          *
          * 按下 上一步的时候，返回下一步的db中的text
          * 也不会造成TextChange的db写入
          * 每一次点击上一步都会把下一步db中的对应item写入到上一步db中
          *
          * 这样描述感觉有点奇怪，但是大概就是这样子~
          *
          * */

        /* @描述 点击返回文字上一步 */
        ivNoteEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = DataSupport.count(TextPeriousCache.class);
                if (count > 0) {
                    change = false;

                    TextPeriousCache last = DataSupport.findLast(TextPeriousCache.class);
                    TextNextCache textNextCache = new TextNextCache();
                    textNextCache.setView_name(last.getView_name());
                    textNextCache.setContent(last.getContent());
                    textNextCache.save();
                    LogUtils.Log("转存内容 " + last.getContent());

                    last.delete();

                    if (count == 1) {
                        etFirstSchedule.setText("");
                        change = true;
                    } else {
                        String content = DataSupport.findLast(TextPeriousCache.class).getContent();
                        etFirstSchedule.setText(content);
                        etFirstSchedule.setSelection(content.length());
                        change = true;
                    }
                }
            }
        });

        /* @描述 点击返回文字下一步 */
        ivNoteEditNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = DataSupport.count(TextNextCache.class);
                if (count > 0) {
                    change = false;

                    TextNextCache last = DataSupport.findLast(TextNextCache.class);

                    TextPeriousCache textPeriousCache = new TextPeriousCache();
                    textPeriousCache.setView_name(last.getView_name());
                    textPeriousCache.setContent(last.getContent());
                    textPeriousCache.save();

                    String content = DataSupport.findLast(TextNextCache.class).getContent();
                    etFirstSchedule.setText(content);
                    etFirstSchedule.setSelection(content.length());

                    last.delete();
                    change = true;
                }
            }
        });
    }


    private void initLLEvent() {
        llNoteDetail.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    /* @描述 如果布局显示键盘出现 */
                    Message msgActivity = mHandler.obtainMessage();
                    msgActivity.what = MESSAGE_LAYOUT_KEYBOARD_SHOW;
                    mHandler.sendMessage(msgActivity);
                } else if (bottom > oldBottom) {
                    /* @描述 如果布局显示键盘隐藏 */
                    Message msgActivity = mHandler.obtainMessage();
                    msgActivity.what = MESSAGE_LAYOUT_KEYBOARD_HIDE;
                    mHandler.sendMessage(msgActivity);
                }
            }
        });
    }


    /*
   * @方法 添加菜单点击事件
   *
   */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //获取菜单item_id
        int id = item.getItemId();

        //根据菜单判断
        switch (id) {
            case R.id.menu_note_share:
                KeyBoardUtils.hidekeyBoard(this, etFirstSchedule);

                View shareView = View.inflate(this, R.layout.dialog_share_type, null);
                TextView tvShareText =
                        (TextView) shareView.findViewById(R.id.tv_dialog_share_type_text);
                TextView tvShareBKS =
                        (TextView) shareView.findViewById(R.id.tv_dialog_share_type_BKS);
                tvShareText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShare();
                        mShareTypeDialogBKS.dismiss();
                    }
                });
                tvShareBKS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(BottomBarNoteActivity.this, ShareActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("content", etFirstSchedule.getText().toString());
                        bundle.putString("title", etNoteDetailTitle.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        mShareTypeDialogBKS.dismiss();
                    }
                });

                mShareTypeDialogBKS = new AlertDialog.Builder(this).setView(shareView).create();

                Window windowShareType = mShareTypeDialogBKS.getWindow();
                windowShareType.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                windowShareType.setWindowAnimations(R.style.MyDialogBottomStyle);  //添加动画
                mShareTypeDialogBKS.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setText(etFirstSchedule.getText().toString());
        /* @描述 分享第一张图片 */
        ImageCache first = DataSupport.findFirst(ImageCache.class);
        if (first != null) {
            oks.setImagePath(first.getImagePath());
        }
        // 启动分享GUI
        oks.show(this);
    }


    @Override
    protected void onDestroy() {
        /* @描述 删除缓存中的图片内容 */
        DataSupport.deleteAll(ImageCache.class);
        DataSupport.deleteAll(VoiceCache.class);
        DataSupport.deleteAll(TextPeriousCache.class);
        DataSupport.deleteAll(TextNextCache.class);
        super.onDestroy();
    }


    public Bitmap getScreenBitmap() {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();

        return temBitmap;
    }


}
