package com.thunderhou.app.advancedlight.chapter03.vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class CustomVerticalView extends ViewGroup {
    private int mLastX;
    private int mLastY;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private int mCurrentIndex;
    private int mChildCount;
    private int mChildHeight;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public CustomVerticalView(Context context) {
        this(context, null);
    }

    public CustomVerticalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context);
        mTracker = VelocityTracker.obtain();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();

            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(childWidth, childHeight * childCount);
            } else if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(childWidth, heightSize);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSize, childHeight * childCount);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        mChildCount = childCount;
        int top = 0;
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int childHeight = child.getMeasuredHeight();
                mChildHeight = childHeight;
                child.layout(0,top,child.getMeasuredWidth(),top+childHeight);
                top += childHeight;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
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
        mTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                scrollBy(0, -deltaY);
                break;
            case MotionEvent.ACTION_UP:
                //scrollToOtherPage();

//                int distanceY = getScrollY() - mCurrentIndex * mChildHeight;
//                Log.d("thunderHou", "distanceY="+distanceY);
//                if (distanceY < 0) {
//                    smoothScrollTo(0, 0);
//                } else if (distanceY > (mChildCount - 1) * mChildHeight) {
//                    smoothScrollTo(0, (mChildCount - 1) * mChildHeight);
//                }

                mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                mTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    /**
     * 滑动到其它页面
     */
    private void scrollToOtherPage() {
        int distanceY = getScrollY() - mCurrentIndex * mChildHeight;
        Log.d("thunderHou", "distanceY="+distanceY);
        if (Math.abs(distanceY) > mChildHeight * 0.5) {
            if (distanceY > 0) {
                mCurrentIndex++;
            } else {
                mCurrentIndex--;
            }
        } else {
            mTracker.computeCurrentVelocity(1000);
            float yVelocity = mTracker.getYVelocity();
            if (Math.abs(yVelocity) > 50) {
                if (yVelocity > 0) {
                    mCurrentIndex--;
                } else {
                    mCurrentIndex++;
                }
            }
        }
        mCurrentIndex = mCurrentIndex < 0 ? 0 : mCurrentIndex > mChildCount - 1 ? mChildCount - 1 : mCurrentIndex;
        smoothScrollTo(0, mCurrentIndex * mChildHeight);
        mTracker.clear();
    }

    private void smoothScrollTo(int destX, int destY) {
        mScroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void fling(int velocityY) {
        mScroller.fling(0,getScrollY(),0,velocityY,0,0,0,(mChildCount-1)*mChildHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > (mChildCount - 1) * mChildHeight) {
            y = (mChildCount - 1) * mChildHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }
}
