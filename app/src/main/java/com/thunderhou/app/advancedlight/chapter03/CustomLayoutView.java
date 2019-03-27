package com.thunderhou.app.advancedlight.chapter03;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class CustomLayoutView extends View {
    private int mLastX, mLastY;
    private Scroller mScroller;

    public CustomLayoutView(Context context) {
        super(context);
    }

    public CustomLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public CustomLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;

                //View的滑动
//                //方式1: 使用layout()
//                layout(getLeft()+offsetX,getTop()+offsetY,
//                        getRight()+offsetX,getBottom()+offsetY);
//                //方式2: 使用offsetLeftAndRight和offsetTopAndBottom
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
//                //方式3: LayoutParams(改变布局参数)
////                //根据父控件决定使用哪个的LayoutParams
////                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);
//                //方式4: 动画,在调用处实现
//                //方式5: scrollTo与scrollBy 移动的是View的内容,如果在ViewGroup中使用,则是移动其所有的子View
//                //若要实现CustomView随手指移动的效果,就需要将偏移量设置为负值
//                //放大镜与报纸, 手机屏幕与画布
//                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                //方式6: Scroller
                break;
        }
        return true;
    }

    /**
     * 系统会在绘制View的时候在draw()方法中调用该方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            //调用父类的scrollTo方法并通过mScroller来不断获取当前的滚动值,
            //每滑动一小段距离就调用invalidate()方法不断地进行重绘,重绘就会调用computeScroll()方法,
            //这样我们通过不断地移动一个小的距离并连贯起来就实现了平滑移动的效果
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        //在2000ms内沿X轴平移deltaX像素
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 2000);
        invalidate();
    }
}
