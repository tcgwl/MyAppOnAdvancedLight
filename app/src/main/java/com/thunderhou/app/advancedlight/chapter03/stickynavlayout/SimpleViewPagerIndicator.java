package com.thunderhou.app.advancedlight.chapter03.stickynavlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleViewPagerIndicator extends LinearLayout {

    private static final int COLOR_TEXT_NORMAL = 0xFF000000;
    private static final int COLOR_INDICATOR_COLOR = Color.GREEN;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mIndicatorColor = COLOR_INDICATOR_COLOR;
    private String[] mTitles;
    private int mTabCount;
    private int mTabWidth;
    private float mTranslationX;

    public SimpleViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(9.0f);
    }

    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        mTabCount = titles.length;
        generateTitleView();
    }

    public void scroll(int position, float offset) {
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }

    private void generateTitleView() {
        if (getChildCount() > 0) {
            removeAllViews();
        }

        setWeightSum(mTabCount);
        for (int i = 0; i < mTabCount; i++) {
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setText(mTitles[i]);
            tv.setTextColor(COLOR_TEXT_NORMAL);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            addView(tv);
        }
    }
}
