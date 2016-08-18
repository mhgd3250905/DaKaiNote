package skkk.gogogo.dakainote.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
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


    private Position mPosition = Position.LEFT_BOTTOM;
    private int mRadius;
    //菜单的状态
    private Status mCurrentStatus = Status.CLOSE;

    //菜单的主按钮
    private View mCButton;

    private OnMenuItemClickListener mMenuItenClickListener;


    //点击子菜单的就扣回调
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public void setmMenuItenClickListener(OnMenuItemClickListener mMenuItenClickListener) {
        this.mMenuItenClickListener = mMenuItenClickListener;
    }

    //菜单的状态的枚举类
    public enum Status {
        OPEN, CLOSE
    }

    //菜单的位置枚举类
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    public ArcMenuView(Context context) {
        this(context, null);

    }

    public ArcMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        mRadius = (int) a.getDimension(R.styleable.ArcMenuView_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100,
                        getResources().getDisplayMetrics()));

        Log.d("TAG", "position= " + mPosition + " mRedius= " + mRadius);

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

            int count=getChildCount();

            for (int i = 0; i <count-1; i++) {

                View child=getChildAt(i+1);

                child.setVisibility(GONE);

                int cl= (int) (mRadius*Math.sin(Math.PI/2/(count-2)
                        *i));
                int ct= (int) (mRadius*Math.cos(Math.PI/2/(count-2)
                        *i));

                int cWidth=child.getMeasuredWidth();
                int cHeight=child.getMeasuredHeight();

                //如果菜单位置在底部 左下，右下
                if(mPosition==Position.LEFT_BOTTOM||mPosition==Position.RIGHT_BOTTOM){
                    ct=getMeasuredHeight()-cHeight-ct;
                }
                //右上右下
                if(mPosition==Position.RIGHT_TOP||mPosition==Position.RIGHT_BOTTOM){
                    cl=getMeasuredWidth()-cWidth-cl;
                }

                child.layout(cl,ct,cl+cWidth,ct+cHeight);
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

        int l=0;
        int t=0;

        int width=mCButton.getMeasuredWidth();
        int height=mCButton.getMeasuredHeight();

        switch (mPosition){
            case LEFT_TOP:
                l=0;
                t=0;
                break;
            case LEFT_BOTTOM:
                l=0;
                t=getMeasuredHeight()-height;
                break;
            case RIGHT_TOP:
                l=getMeasuredWidth()-width;
                t=0;
                break;
            case RIGHT_BOTTOM:
                l=getMeasuredWidth()-width;
                t=getMeasuredHeight()-height;
                break;
        }
        mCButton.layout(l,t,l+width,t+height);
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
    private void toggleMenu(int duration) {
        //为menuItem添加平移动画和旋转动画
        int count=getChildCount();
        for (int i = 0; i < count - 1; i++) {

            final View childView=getChildAt(i+1);
            childView.setVisibility(VISIBLE);

            //所有的item动画的end就是（0,0）
            int cl= (int) (mRadius*Math.sin(Math.PI/2/(count-2)
                    *i));
            int ct= (int) (mRadius*Math.cos(Math.PI / 2 / (count - 2)
                    * i));
            int xflag=1;
            int yflag=1;

            if(mPosition==Position.LEFT_TOP||mPosition==Position.LEFT_BOTTOM){
                xflag=-1;
            }

            if(mPosition==Position.LEFT_TOP||mPosition==Position.RIGHT_TOP){
                yflag=-1;
            }

            AnimationSet animationSet=new AnimationSet(true);

            Animation tranAnim=null;
            //打开平移动画
            if(mCurrentStatus==Status.CLOSE){
                tranAnim=new TranslateAnimation(xflag*cl,0,yflag*ct,0);
                childView.setClickable(true);
                childView.setFocusable(true);

            }else{
                tranAnim=new TranslateAnimation(0,xflag*cl,0,yflag*ct);
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
                    if(mCurrentStatus==Status.CLOSE){
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //旋转动画
            RotateAnimation rotateAnim=new RotateAnimation(0,720,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

            animationSet.addAnimation(rotateAnim);
            animationSet.addAnimation(tranAnim);
            animationSet.setStartOffset((i*100)/count);
            childView.startAnimation(animationSet);

        }
        //切换菜单状态
        changeStatus();

    }

    /*
    * @方法 切换菜单状态
    *
    */
    private void changeStatus() {
        mCurrentStatus=(mCurrentStatus==Status.CLOSE?Status.OPEN:Status.CLOSE);
    }

    /*
    * @方法 主菜单按钮旋转动画
    *
    */
    private void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation anim=new RotateAnimation(start,end,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }
}
