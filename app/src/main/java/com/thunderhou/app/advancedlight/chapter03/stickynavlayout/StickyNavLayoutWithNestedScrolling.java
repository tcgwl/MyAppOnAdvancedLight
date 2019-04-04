package com.thunderhou.app.advancedlight.chapter03.stickynavlayout;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.thunderhou.app.advancedlight.R;

/**
 * 事件分发是这样的：子View首先得到事件处理权，处理过程中，父View可以对其拦截，但是拦截了以后就无法再还给子View（本次手势内）。
 * NestedScrolling机制是这样的：内部View在滚动的时候，首先将dx,dy交给NestedScrollingParent，NestedScrollingParent可对其进行部分消耗，剩余的部分还给内部View。
 * ---------------------
 * 原文：https://blog.csdn.net/lmj623565791/article/details/52204039
 */
public class StickyNavLayoutWithNestedScrolling extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "StickyNavLayout";

    private View mTop;
    private View mNav;
    private ViewPager mViewPager;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mTopViewHeight;

    public StickyNavLayoutWithNestedScrolling(Context context) {
        this(context, null);
    }

    public StickyNavLayoutWithNestedScrolling(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);

        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (view instanceof ViewPager) {
            mViewPager = (ViewPager) view;
        } else {
            throw new RuntimeException("id_stickynavlayout_viewpager show used by ViewPager!");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.height = getMeasuredHeight() - mNav.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
        Log.d(TAG, "onSizeChanged: mTopViewHeight="+mTopViewHeight);
    }

    private void fling(int velocityY) {
        mScroller.fling(0,getScrollY(),0,velocityY,0,0,0,mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 该方法决定了当前控件是否能接收到其内部View(不一定非要是直接子View)滑动时的参数
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //如果是纵向返回true
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //如果是上滑且顶部控件未完全隐藏，则消耗掉dy，即consumed[1]=dy
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        //如果是下滑且内部View已经无法继续下拉，则消耗掉dy，即consumed[1]=dy
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
        Log.d(TAG, "onNestedPreScroll: dy="+dy+", scrollY="+getScrollY());
        Log.d(TAG, "onNestedPreScroll: hiddenTop="+hiddenTop+", showTop="+showTop);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    /**
     * 当顶部控件显示时，fling可以让顶部控件隐藏或者显示
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling: velocityY="+velocityY+", scrollY="+getScrollY());
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }
}
