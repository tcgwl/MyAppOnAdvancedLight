package com.thunderhou.app.advancedlight.chapter03;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.thunderhou.app.advancedlight.R;
import com.thunderhou.app.advancedlight.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {

    private CustomLayoutView mCustomLayoutView;
    private Button mButton;

    /**
     * 需要注意的是,在使用ObjectAnimator的时候,要操作的属性必须要有get和set方法,不然ObjectAnimator就无法生效.
     * 可以通过自定义一个属性类或包装类来间接地给这个属性增加get和set方法.
     *
     * 使用时只需要操作包装类就可以调用get和set方法了
     */
    private static class MyPackageView {
        private View mTarget;

        private MyPackageView(View target) {
            this.mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }

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
//        mCustomLayoutView.smoothScrollTo(-400, 0);

        mButton = findViewById(R.id.id_btn);
        MyPackageView myPackageView = new MyPackageView(mButton);
        ObjectAnimator.ofInt(myPackageView,"width",500).setDuration(2000).start();

//        startAlphaAnim();
//        startAnimatorSet();
//        startPropertyValuesHolder();

        startAnimByXML();
    }

    private void startAnimByXML() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scale);
        animator.setTarget(mCustomLayoutView);
        animator.start();
    }

    private void startPropertyValuesHolder() {
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.3f, 1.0f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2.0f, 1.0f);
        PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("rotationX", 0, 90.0f, 0);
        //使用PropertyValuesHolder只能多个动画同时执行
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mCustomLayoutView, valuesHolder1, valuesHolder2, valuesHolder3);
        animator.setDuration(3000).start();
    }

    private void startAnimatorSet() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mCustomLayoutView,"translationX", 0, 300.0f, 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mCustomLayoutView,"scaleX", 1.0f, 2.0f, 1.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mCustomLayoutView,"rotationX", 0, 90.0f, 0);

        //组合动画
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        //先执行animator3,然后同时执行animator1和animator2
        set.play(animator1).with(animator2).after(animator3);
        set.start();
    }

    private void startAlphaAnim() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mCustomLayoutView, "alpha", 1.0f, 0, 1.0f);
//        alphaAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        alphaAnimator.setDuration(3000).start();
    }
}
