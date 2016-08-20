package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import skkk.gogogo.dakainote.R;

/**
 * Created by admin on 2016/8/18.
 */
/*
* 
* 描    述：弹射菜单
* 作    者：ksheng
* 时    间：2016/8/18$ 21:45$.
*/
public class ArcMenuView extends ViewGroup implements View.OnClickListener {


    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;
    private static final int ARC_MODE_CIRCLE = 100;
    private static final int ARC_MODE_LINE = 101;

    private Position mPosition = Position.LEFT_BOTTOM;
    private ArcMode mArcMode = ArcMode.CIRCLE;

    private int mRadius;
    //菜单的状态
    private Status mCurrentStatus = Status.CLOSE;

    //菜单的主按钮
    private View mCButton;

    private OnMenuItemClickListener mMenuItemClickListener;


    //点击子菜单的就扣回调
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public void setmMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    //菜单的状态的枚举类
    public enum Status {
        OPEN, CLOSE
    }

    //菜单的位置枚举类
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    //菜单弹出方式枚举类
    public enum ArcMode {
        CIRCLE, LINE
    }

    public ArcMenuView(Context context) {
        this(context, null);

    }

    public ArcMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置默认值
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100, getResources().getDisplayMetrics());

        //获取自定义属性的值

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(
                        attrs,
                        R.styleable.ArcMenuView,
                        defStyleAttr,
                        0);

        int pos = a.getInt(R.styleable.ArcMenuView_position, 1);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }

        int mode = a.getInt(R.styleable.ArcMenuView_arcMode, 100);
        switch (mode) {
            case ARC_MODE_CIRCLE:
                mArcMode = ArcMode.CIRCLE;
                break;
            case ARC_MODE_LINE:
                mArcMode = ArcMode.LINE;
                break;
        }

        mRadius = (int) a.getDimension(R.styleable.ArcMenuView_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100,
                        getResources().getDisplayMetrics()));

        Log.d("SKKK_____", "position= " + mPosition + " mRedius= " + mRadius + " mArcMode= " + mArcMode);

        a.recycle();
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
        if (changed) {
            layoutCButton();
            int count = getChildCount();
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(GONE);
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();
                if(mArcMode==ArcMode.CIRCLE){
                    //判断当前弹射模式是否为圆形
                    int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                            * i));
                    int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                            * i));
                    //如果菜单位置在底部 左下，右下
                    if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                        ct = getMeasuredHeight() - cHeight - ct;
                    }
                    //右上右下
                    if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                        cl = getMeasuredWidth() - cWidth - cl;
                    }
                    child.layout(cl, ct, cl + cWidth, ct + cHeight);

                }else if(mArcMode==ArcMode.LINE) {
                    //判断当前弹射模式是否为线性
                    //左上
                    int cl=getMeasuredWidth()/(count)+getMeasuredWidth()/count*i;
                    int ct=0;
                    //如果菜单位置在底部 左下，右下
                    if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                        ct = getMeasuredHeight() - cHeight - ct;
                    }
                    //右上右下
                    if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                        cl = getMeasuredWidth() - cWidth - cl;
                    }
                    child.layout(cl, ct, cl + cWidth, ct + cHeight);
                }
            }
        }
    }

    /*
    * @方法 定位主菜单
    *
    */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int l = 0;
        int t = 0;

        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + height);
    }

    /*
    * @方法 主菜单按钮点击事件
    *
    */
    @Override
    public void onClick(View v) {
        rotateCButton(v, 0f, 720f, 200);
        toggleMenu(200);

    }
    /*
    * @方法 切换菜单动画
    *
    */
    public void toggleMenu(int duration) {
        //为menuItem添加平移动画和旋转动画
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(VISIBLE);

            if(mArcMode==ArcMode.CIRCLE){
                //动画判断 如果弹射模式为圆形
                //所有的item动画的end就是（0,0）
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                        * i));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                        * i));
                int xflag = 1;
                int yflag = 1;

                if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                    xflag = -1;
                }

                if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                    yflag = -1;
                }

                AnimationSet animationSet = new AnimationSet(true);

                Animation tranAnim = null;
                //打开平移动画
                if (mCurrentStatus == Status.CLOSE) {
                    tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                    childView.setClickable(true);
                    childView.setFocusable(true);

                } else {
                    tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                    childView.setClickable(false);
                    childView.setFocusable(false);

                }
                tranAnim.setDuration(duration);
                tranAnim.setFillAfter(true);

                tranAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mCurrentStatus == Status.CLOSE) {
                            childView.setVisibility(GONE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                //旋转动画
                RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(duration);
                rotateAnim.setFillAfter(true);

                animationSet.addAnimation(rotateAnim);
                animationSet.addAnimation(tranAnim);
                animationSet.setStartOffset((i * 50) / count);
                childView.startAnimation(animationSet);
            }else if(mArcMode==ArcMode.LINE){
                //动画判断 如果弹射模式为线性


                //动画判断 如果弹射模式为圆形
                //所有的item动画的end就是（0,0）
                //左上
                int cl=getMeasuredWidth()/(count)+getMeasuredWidth()/count*i;
                int ct=0;

                int xflag = 1;
                int yflag = 1;

                if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                    xflag = -1;
                }

                if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                    yflag = -1;
                }

                AnimationSet animationSet = new AnimationSet(true);

                Animation tranAnim = null;
                //打开平移动画
                if (mCurrentStatus == Status.CLOSE) {
                    tranAnim = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                    childView.setClickable(true);
                    childView.setFocusable(true);
                } else {
                    tranAnim = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                    childView.setClickable(false);
                    childView.setFocusable(false);

                }
                tranAnim.setDuration(duration);
                tranAnim.setFillAfter(true);


                tranAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mCurrentStatus == Status.CLOSE) {
                            childView.setVisibility(GONE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                //旋转动画
                RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(duration);
                rotateAnim.setFillAfter(true);

                animationSet.addAnimation(rotateAnim);
                animationSet.addAnimation(tranAnim);
                animationSet.setStartOffset((i * 50) / count);
                childView.startAnimation(animationSet);


            }

            //子菜单点击事件
            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener != null) {
                        mMenuItemClickListener.onClick(childView, pos);
                        menuItemAnim(pos - 1);
                        changeStatus();
                        Log.d("SKKK_____", "点击了" + pos);
                    }
                }
            });
        }
        //切换菜单状态
        changeStatus();

    }

    /*
    * @方法 添加
menuItem点击动画
    *
    */
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    /*
    * @方法为当前点击的Item设置变大和透明度降低地动画
    *
    */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /*
   * @方法为当前未点击的Item设置变小和透明度降低地动画
   *
   */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /*
    * @方法 返回当前按钮是否为开启状态
    *
    */
    public boolean isOpen() {
        return mCurrentStatus == Status.OPEN;
    }

    /*
    * @方法 切换菜单状态
    *
    */
    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    /*
    * @方法 主菜单按钮旋转动画
    *
    */
    private void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }
}
