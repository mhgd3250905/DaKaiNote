package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/20.
 */
/*
* 
* 描    述：note为了实现标题动画自定义view
* 作    者：ksheng
* 时    间：2016/8/20$ 14:58$.
*/
public class NoteBoxView extends ViewGroup implements View.OnClickListener {

    private View mNoteContent;
    private View mNoteTitle;
    private boolean pinIsExist = false;

    private static final int STATUS_TITLE_SHOW = 200;
    private static final int STATUS_TITLE_HIDE = 201;
    //标题默认状态为显示
    private NoteStatus mNoteStatus = NoteStatus.TITLE_HIDE;
    private ImageView ivTest;
    private TextView timeView;


    //设置标题的状态：显示、隐藏
    public enum NoteStatus {
        TITLE_SHOW, TITLE_HIDE
    }

    public NoteBoxView(Context context) {
        this(context, null);
    }

    public NoteBoxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(
                        attrs,
                        R.styleable.NoteBoxView,
                        defStyleAttr,
                        0);

        int status = a.getInt(R.styleable.NoteBoxView_noteStatus, 200);
        switch (status) {
            case STATUS_TITLE_SHOW:
                mNoteStatus = NoteStatus.TITLE_SHOW;
                break;
            case STATUS_TITLE_HIDE:
                mNoteStatus = NoteStatus.TITLE_HIDE;
                break;
        }
        a.recycle();

        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ivTest = new ImageView(getContext());
        ivTest.setLayoutParams(lp);
        ivTest.setImageResource(R.drawable.item_pin);
        ivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*
    * @方法 设置Pin值
    *
    */
    public void setPinIsExist(boolean pinIsExist) {
        this.pinIsExist = pinIsExist;
    }

    public boolean isPinIsExist() {
        return pinIsExist;
    }

    public void setPinImage() {
        setPinIsExist(true);
        Log.d("SKKK______", "修改pin为false");
        addView(ivTest);

    }

    public void removePinImage() {
        setPinIsExist(false);
        Log.d("SKKK______", "修改pin为true");
        removeView(ivTest);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutContent();
        layoutTitle();
        layoutTime();
        if (mNoteStatus == NoteStatus.TITLE_SHOW) {
            getChildAt(1).setVisibility(VISIBLE);
            getChildAt(2).setVisibility(GONE);
        }
        if (pinIsExist) {
            for (int i = 3; i < getChildCount(); i++) {
                layoutInsertedView(i);
            }
        }
    }

    /*
    * @方法 对标题下方事件进行布局
    *
    */
    private void layoutTime() {
        mNoteTitle = getChildAt(2);
        int l = 0;
        int t = 0;
        int width = mNoteTitle.getMeasuredWidth();
        int height = mNoteTitle.getMeasuredHeight();
        int nl = getMeasuredWidth() / 2 - width / 2;
        int nt = 0;
        mNoteTitle.layout(nl, nt, nl + width - 100, nt + height);
    }

    /*
    * @方法 设置第三个child
    *
    */
    private void layoutInsertedView(int i) {
        View insertedView = getChildAt(i);

        int l = 0;
        int t = getMeasuredHeight() - insertedView.getMeasuredHeight();
        int width = insertedView.getMeasuredWidth();
        int height = insertedView.getMeasuredHeight();

        insertedView.layout(l, t, l + width, t + height);
    }


    /*
        * @方法 对Note标题进行布局
        *
        */
    private void layoutTitle() {
        mNoteTitle = getChildAt(1);
        int l = 0;
        int t = 0;
        int width = mNoteTitle.getMeasuredWidth();
        int height = mNoteTitle.getMeasuredHeight();
        int nl = getMeasuredWidth() / 2 - width / 2;
        int nt = 0;
        mNoteTitle.layout(nl, nt, nl + width - 100, nt + height);
    }

    /*
    * @方法 对note内容进行布局
    *
    */
    private void layoutContent() {
        mNoteContent = getChildAt(0);
        mNoteContent.setFocusable(true);
        mNoteContent.setOnClickListener(this);

        int l = 0;
        int t = getChildAt(1).getMeasuredHeight();
        int width = mNoteContent.getMeasuredWidth();
        int height = mNoteContent.getMeasuredHeight();
        if (mNoteStatus == NoteStatus.TITLE_SHOW) {
            t = getChildAt(1).getMeasuredHeight();
        }
        mNoteContent.layout(l, t, l + width, t + height);
    }


    /*
    * @方法 测试点击事件
    *
    */
    @Override
    public void onClick(View v) {

        timeView = (TextView) getChildAt(2);
        if(TextUtils.isEmpty(timeView.getText())) {
            timeView.setText(DateUtils.getTime());
        }
        int width = mNoteTitle.getMeasuredWidth();
        int height = mNoteTitle.getMeasuredHeight();

        int nl = getMeasuredWidth() / 2 - width / 2;
        int nt = 0;


        TranslateAnimation transAnim = null;
        if (mNoteStatus == NoteStatus.TITLE_HIDE) {
            transAnim = new TranslateAnimation((-1) * width, nl, 0, 0);
            //getChildAt(1).setFocusable(true);
            //getChildAt(1).setClickable(true);
        } else if (mNoteStatus == NoteStatus.TITLE_SHOW) {
            transAnim = new TranslateAnimation(nl, (-1) * width, 0, 0);
            TranslateAnimation a=new TranslateAnimation(nl, (-1) * width, 0, 0);
            a.setDuration(200);
            timeView.startAnimation(a);
            //getChildAt(1).setFocusable(false);
            //getChildAt(1).setClickable(false);
        }

        transAnim.setDuration(200);
        transAnim.setFillAfter(true);

        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mNoteStatus == NoteStatus.TITLE_HIDE) {
                    getChildAt(1).setVisibility(GONE);

                    AlphaAnimation alphAnim = new AlphaAnimation(0f, 1f);
                    alphAnim.setDuration(400);
                    alphAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            timeView.setVisibility(VISIBLE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    timeView.setAnimation(alphAnim);
                } else {
                    getChildAt(1).setVisibility(VISIBLE);
                    timeView.setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        getChildAt(1).startAnimation(transAnim);

        //切换菜单状态
        changeStatus();

        //重新定位layout布局
        layoutContent();
    }

    /*
    * @方法 切换菜单状态
    *
    */
    private void changeStatus() {
        mNoteStatus = (mNoteStatus == NoteStatus.TITLE_HIDE ? NoteStatus.TITLE_SHOW : NoteStatus.TITLE_HIDE);
    }


}
