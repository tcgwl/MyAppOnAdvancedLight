package com.thunderhou.app.advancedlight.chapter03;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomHorizontalView extends ViewGroup {
    private int mChildWidth = 0;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;
    //当前子元素
    private int mCurrentIndex;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public CustomHorizontalView(Context context) {
        this(context, null);
    }

    public CustomHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量所有子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //计算本身宽和高
        //处理wrap_content的情况
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //宽和高都为AT_MOST,则宽度设置为所有子元素宽度的和,高度设置为第一个子元素的高度
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth * getChildCount(), childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //宽为AT_MOST,则宽度设置为所有子元素宽度的和
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            setMeasuredDimension(childWidth * getChildCount(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //高为AT_MOST,则高度设置为第一个子元素的高度
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        View child;
        int left = 0;
        //遍历所有子元素,如果子元素不是GONE,则调用子元素的layout()方法将其放置在合适位置
        for (int i = 0; i < childCount; i++){
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childWidth = child.getMeasuredWidth();
                mChildWidth = childWidth;
                child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
                left += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        //在刚进入onInterceptTouchEvent时,获取点击事件的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                //在弹性滑动过程中再次触摸,则进行拦截滑动以操作页面
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //计算每次手指移动的距离
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                //判断用户是水平滑动还是垂直滑动,如水平滑动则拦截事件
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastInterceptX = x;
        mLastInterceptY = y;

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //跟随手指滑动
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            //释放手指以后开始自动滑动到目标位置
            case MotionEvent.ACTION_UP:
                //相对于当前View滑动的距离,正为向左,负为向右
                int distanceX = getScrollX() - mCurrentIndex * mChildWidth;
                if (Math.abs(distanceX) > mChildWidth * 0.5) {
                    //必须滑动的距离要大于1/2个宽度,否则不会切换到其他页面
                    if (distanceX > 0) {
                        mCurrentIndex++;
                    } else {
                        mCurrentIndex--;
                    }
                } else {
                    //获取水平方向上的速度
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = mVelocityTracker.getXVelocity();
                    //若速度的绝对值大于50,则认为是快速滑动,执行切换页面
                    if (Math.abs(xVelocity) > 50) {
                        if (xVelocity > 0) {
                            mCurrentIndex--;
                        } else {
                            mCurrentIndex++;
                        }
                    }
                }

                mCurrentIndex = mCurrentIndex < 0 ? 0 : (mCurrentIndex > getChildCount() - 1 ? getChildCount() - 1 : mCurrentIndex);
                smoothScrollTo(mCurrentIndex * mChildWidth, 0);
                //重置速度计算器
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    /**
     * 弹性滑动到指定位置
     */
    private void smoothScrollTo(int destX, int destY) {
        mScroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrX());
            postInvalidate();
        }
    }
}
