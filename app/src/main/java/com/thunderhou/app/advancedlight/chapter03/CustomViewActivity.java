package com.thunderhou.app.advancedlight.chapter03;

import android.animation.ObjectAnimator;
import android.view.animation.AnimationUtils;

import com.thunderhou.app.advancedlight.R;
import com.thunderhou.app.advancedlight.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {

    private CustomLayoutView mCustomLayoutView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mCustomLayoutView = findViewById(R.id.id_customLayoutView);
//        //方式4: 动画
////        //View动画 不会改变位置参数
////        mCustomLayoutView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
//        //属性动画
//        ObjectAnimator.ofFloat(mCustomLayoutView,"translationX",0,300)
//                .setDuration(1000).start();
        //方式6: Scroller
        //Scroller本身是不能实现View的滑动的,它需要与View的computeScroll()方法配合才能实现弹性滑动的效果
        //向右平移400像素
        mCustomLayoutView.smoothScrollTo(-400, 0);
    }
}
